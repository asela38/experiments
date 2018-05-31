package org.java.monads;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class InputNumberFileGenerator {

    public static void main(String[] args) throws Exception {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("input-numbers.txt"))) {
            ThreadLocalRandom.current().ints(1, 1_000_000).limit(1_000_000).forEach(i -> {
                try {
                    writer.write(i + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
