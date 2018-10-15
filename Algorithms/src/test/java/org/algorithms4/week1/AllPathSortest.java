package org.algorithms4.week1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

import org.junit.Test;

public class AllPathSortest {

    private static final String TEST_FILE_LOCATION = "C:\\Users\\asela.illayapparachc\\git\\stanford-algs\\testCases\\course4\\assignment1AllPairsShortestPath\\";
    private static final String INPUT_FILE         = "input_random_";
    private static final String _1_2_FILE          = TEST_FILE_LOCATION + INPUT_FILE + "1_2.txt";
    @SuppressWarnings("unused")
    private static final String JOB_FILE           = "C:\\Asela\\mydocs\\Coursera\\knapsack1.txt";
    @SuppressWarnings("unused")
    private static final String JOB_FILE2          = "C:\\Asela\\mydocs\\Coursera\\knapsack_big.txt";

    @Test
    public void testFile() throws Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader(_1_2_FILE))) {
            reader.lines().forEach(System.out::println);
        }
    }

    @Test
    public void trivial() throws Exception {
        System.out.println(process(_1_2_FILE)); // NULL
    }

    private String process(String file) throws FileNotFoundException {
        return "NULL";
    }

    @Test
    public void testForAll() throws Exception {

        Files.walk(Paths.get(TEST_FILE_LOCATION)).map(p -> p.toFile()).filter(f -> f.getName().contains(INPUT_FILE))
                .sorted((a, b) -> Long.compare(a.length(), b.length())).forEach(f -> {
                    try {
                        String outputFile = f.getAbsolutePath().replace("input", "output");
                        String output = "";
                        try (BufferedReader reader = new BufferedReader(new FileReader(outputFile))) {
                            output = reader.readLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        String result = null;
                        System.out.printf("Expected : %10s Calculated: %10s %6s File: %s %n", output,
                                result = process(f.getAbsolutePath()), Objects.equals(result, output), f.getName());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                });
    }

}
