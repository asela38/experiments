package org.tcp.over.ip;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class DNSRequest {

    public static void main(String[] args) throws Exception {

        try (Socket socket = new Socket("10.13.60.31", 53)) {

            try (OutputStream out = socket.getOutputStream()) {
                int[] text = new int[] { 0xb5, 0x1e, 0x01, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x07,
                        0x69, 0x73, 0x6a, 0x35, 0x63, 0x6d, 0x78, 0x0c, 0x77, 0x65, 0x62, 0x65, 0x78, 0x63, 0x6f, 0x6e,
                        0x6e, 0x65, 0x63, 0x74, 0x03, 0x63, 0x6f, 0x6d, 0x00, 0x00, 0x01, 0x00, 0x01 };
                for (int i : text)
                    out.write((byte) i);

                try (InputStream in = socket.getInputStream()) {
                    int i = 0;
                    while ((i = in.read()) != -1)
                        System.out.println(i);
                }
            }

        }
    }
}
