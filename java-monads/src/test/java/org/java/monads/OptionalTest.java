package org.java.monads;

import static org.junit.Assert.*;

import java.security.MessageDigest;
import java.util.Base64;
import java.util.Optional;

import org.junit.Test;

public class OptionalTest {

    @Test
    public void testName() throws Exception {
        String password = "Monad123!@#";
        System.out.printf("Password : [%s]  enc1: [%s], enc2: [%s] %n ", password, encodePassword1(password), encodePassword2(password));
        
        assertEquals(encodePassword1(password), encodePassword2(password));
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void encodeTestException() throws Exception{
        encodePassword3(null);
    }

    public String encodePassword1(String password) throws Exception {
        return Optional.ofNullable(password)
                        .map(String::getBytes)
                        .map(MessageDigest.getInstance("SHA1")::digest)
                        .map(Base64.getEncoder()::encodeToString)
                        .orElse(null);
    }

    public String encodePassword2(String password) throws Exception{
        if (password == null)
            return password;
        return Base64.getEncoder().encodeToString(MessageDigest.getInstance("SHA1").digest(password.getBytes()));
    }
    
    public String encodePassword3(String password) throws Exception {
        return Optional.ofNullable(password)
                        .map(String::getBytes)
                        .map(MessageDigest.getInstance("SHA1")::digest)
                        .map(Base64.getEncoder()::encodeToString)
                        .orElseThrow(IllegalArgumentException::new);
    }
    
    public int size(String string) throws Exception {
        return Optional.ofNullable(string)
                        .map(String::length)
                        .orElseThrow(IllegalArgumentException::new);
    }
    
    @Test(expected=IllegalArgumentException.class)
	public void sizeIfNullExcpetException() throws Exception {
		size(null);
	}


}
