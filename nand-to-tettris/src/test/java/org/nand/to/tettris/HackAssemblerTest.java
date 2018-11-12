package org.nand.to.tettris;

import java.io.File;
import java.util.Scanner;

import org.junit.Test;

public class HackAssemblerTest {

    @Test
    public void verify_execution() throws Exception {
        System.out.printf("File loaded form : %s%n", new File(".").getAbsolutePath());
        Process process = Runtime.getRuntime()
                .exec(String.format(
                        "java -cp target/classes org.nand.to.tettris.HackAssembler target/test-classes/%s", "Add.asm"));
        System.out.printf("Wait for the process to complete. Result: %s%n", process.waitFor());

        System.out.println("--Inputstream:");
        try (Scanner scanner = new Scanner(process.getInputStream())) {
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }

        }

        System.out.println("--Errorstream:");
        try (Scanner scanner = new Scanner(process.getErrorStream())) {
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }

        }

    }
}
