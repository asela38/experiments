package org.tcp.over.ip;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class EchoServer {
    public static void main(String[] args) throws Exception {

        try (ServerSocket server = new ServerSocket(56789)) {
            while (true)
                try (Socket accept = server.accept()) {

                    Logger.getGlobal()
                            .info(String.format("Client Accepted (%s:%d)", accept.getInetAddress(), accept.getPort()));

                    try (InputStream in = accept.getInputStream()) {

                        try (OutputStream out = accept.getOutputStream()) {
                            OutputStreamWriter writer = new OutputStreamWriter(out);
                            writer.write("HTTP/1.1 200 OK\n");
                            writer.write("Content-Type: application/json\n\n");
                            writer.write(String.format("{\"message\":\"%s\"}\n\n", "ACK"));
                            writer.flush();
                        }
                    }

                    Logger.getGlobal().info("Completed.");

                }
        }
    }
}
