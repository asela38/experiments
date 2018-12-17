package org.nand.to.tettris.vm.translator;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.junit.Test;

public class HackVMTranslatorTest {

    @Test
    public void verify_execution_loop() throws Exception {
        System.out.printf("File loaded form : %s%n", new File(".").getAbsolutePath());
        executeMainMethod(
                "C:\\Users\\asela.illayapparachc\\git\\experiments\\nand-to-tettris\\src\\test\\resources\\BasicLoop.vm");

    }

    @Test
    public void verify_execution_fib() throws Exception {
        System.out.printf("File loaded form : %s%n", new File(".").getAbsolutePath());
        executeMainMethod(
                "C:\\Users\\asela.illayapparachc\\git\\experiments\\nand-to-tettris\\src\\test\\resources\\FibonacciSeries.vm");

    }

    @Test
    public void verify_execution() throws Exception {
        System.out.printf("File loaded form : %s%n", new File(".").getAbsolutePath());
        executeAssembler("BasicTest.vm");
        executeAssembler("StaticTest.vm");
        executeAssembler("PointerTest.vm");
        executeAssembler("SimpleAdd.vm");
        executeAssembler("StackTest.vm");

    }

    private void executeMainMethod(String... files) {
        Main.main(files);
    }

    private void executeAssembler(String file) throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec(String
                .format("java -cp target/classes org.nand.to.tettris.vm.translator.Main target/test-classes/%s", file));
        System.out.printf("Wait for the process to complete. Result: %s%n", process.waitFor());

        System.out.println("--Inputstream:");
        try (Scanner scanner = new Scanner(process.getInputStream());) {
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
