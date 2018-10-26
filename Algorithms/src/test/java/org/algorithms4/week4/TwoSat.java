package org.algorithms4.week4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Scanner;

import org.junit.Test;

import com.google.common.base.Stopwatch;

public class TwoSat {

    private static final String TEST_FILE_LOCATION = "/Users/asela.illayapparachc/git/stanford-algs/testCases/course4/assignment4TwoSat/";
    private static final String INPUT_FILE         = "input_beaunus_";
    private static final String _1_2_FILE          = TEST_FILE_LOCATION + INPUT_FILE + "1_2.txt";
    private static final String _2_2_FILE          = TEST_FILE_LOCATION + INPUT_FILE + "2_2.txt";
    private static final String _12_8_FILE         = TEST_FILE_LOCATION + INPUT_FILE + "12_8.txt";
    private static final String _19_6_FILE         = TEST_FILE_LOCATION + INPUT_FILE + "19_6.txt";
    private static final String _26_8_FILE         = TEST_FILE_LOCATION + INPUT_FILE + "26_8.txt";
    private static final String _32_256_FILE       = TEST_FILE_LOCATION + INPUT_FILE + "32_256.txt";
    private static final String _38_1024_FILE      = TEST_FILE_LOCATION + INPUT_FILE + "38_1024.txt";
    @SuppressWarnings("unused")
    private static final String JOB_FILE1          = "/Users/asela.illayapparachc/Desktop/code/nn.txt";

    @Test
    public void testFile() throws Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader(_1_2_FILE))) {
            reader.lines().forEach(System.out::println);
        }
    }

    @Test
    public void job() throws Exception {

        System.out.println(process(JOB_FILE1)); // -1947

    }

    @Test
    public void trivial() throws Exception {
        // System.out.println(process(_1_2_FILE)); // 0
        // System.out.println(process(_2_2_FILE)); // 2
        System.out.println(process(_12_8_FILE)); // 19
    }

    private String process(String file) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new File(file))) {
            int N = scanner.nextInt();

            return Long.toString((long) N);
        }
    }

    @Test
    public void testForAll() throws Exception {

        Files.walk(Paths.get(TEST_FILE_LOCATION)).sequential().map(p -> p.toFile())
                .filter(f -> f.getName().contains(INPUT_FILE))
                .sorted((a, b) -> Long.compare(a.length(), b.length())).forEach(f -> {
                    try {
                        Stopwatch sw = Stopwatch.createStarted();
                        String outputFile = f.getAbsolutePath().replace("input", "output");
                        String output = "";
                        try (BufferedReader reader = new BufferedReader(new FileReader(outputFile))) {
                            output = reader.readLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        String result = null;
                        System.out.printf("Expected : %10s Calculated: %10s %6s File: %40s %s %3s %n", output,
                                result = process(f.getAbsolutePath()), Objects.equals(result, output), f.getName(),
                                sw.elapsed().getSeconds(), "s");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                });
    }

}
