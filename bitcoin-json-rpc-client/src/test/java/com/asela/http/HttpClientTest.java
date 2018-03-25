/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asela.http;

import com.asela.Main;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;

/**
 *
 * @author a531a
 */
public class HttpClientTest {
    
    @Test
    public void testHttpClient() {
        try {
            URL url = new URL("http://localhost:8332");
            
            String body = "{\"jsonrpc\":\"1.0\",\"id\":\"curltext\",\"method\":\"help\",\"params\":[]}";
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
             connection.setRequestMethod("POST");
            connection.setRequestProperty("content-type", "application/json");
            
            Authenticator.setDefault( new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                   return new PasswordAuthentication("bitcoin", "bitcoint".toCharArray());
                }
            });
            connection.setDoOutput(true);
            Writer writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(body);
            writer.flush();
            
           
            
            
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            
            reader.lines().forEach(System.out::println);
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(HttpClientTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HttpClientTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
            
            
            
}
