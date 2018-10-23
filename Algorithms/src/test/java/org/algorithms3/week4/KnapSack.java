package org.algorithms3.week4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Logger;

import org.junit.Test;

public class KnapSack {

    @SuppressWarnings("unused")
    private static final Logger LOGGER             = Logger.getAnonymousLogger();

    private static final String TEST_FILE_LOCATION = "C:\\Users\\asela.illayapparachc\\git\\stanford-algs\\testCases\\course3\\assignment4Knapsack\\";
    private static final String INPUT_FILE         = "input_random_";
    private static final String _1_4_4_FILE        = TEST_FILE_LOCATION + INPUT_FILE + "1_4_4.txt";
    private static final String _2_4_4_FILE        = TEST_FILE_LOCATION + INPUT_FILE + "2_4_4.txt";
    private static final String JOB_FILE           = "C:\\Asela\\mydocs\\Coursera\\knapsack1.txt";
    private static final String JOB_FILE2          = "C:\\Asela\\mydocs\\Coursera\\knapsack_big.txt";

    @Test
    public void testFile() throws Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader(_1_4_4_FILE))) {
            reader.lines().forEach(System.out::println);
        }
    }

    @Test
    public void trivial() throws Exception {
        System.out.println(process(_1_4_4_FILE)); // 4
        System.out.println(process(_2_4_4_FILE)); // 6
        System.out.println(process(JOB_FILE)); // 106
        System.out.println(process(JOB_FILE2)); // 106
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
                        long result = 0L;
                        System.out.printf("Expected : %10s Calculated: %10s %6s File: %s %n", output,
                                result = process(f.getAbsolutePath()), Long.parseLong(output) == result, f.getName());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                });
    }

    private long process(String file) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new File(file))) {
            int W = scanner.nextInt();
            int n = scanner.nextInt();

            int[] v = new int[n + 1], s = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                v[i] = scanner.nextInt();
                s[i] = scanner.nextInt();
            }

            long[][] w = new long[2][W + 1];
            for (int i = 0; i <= W; i++)
                w[0][i] = 0;
            for (int i = 1; i <= n; i++) {
                for (int j = 0; j <= W; j++) {
                    // System.out.printf("%s-%s-%s%n", i, j , s[i]);
                    long[] previous = w[i % 2];
					w[(i + 1) % 2][j] = Long.max(previous[j], (j - s[i]) > -1 ? (v[i] + previous[j - s[i]]) : 0);
                }
            }

            // printData(W, v, s, w);

            return w[(n + 1) % 2][W];
        }
    }

    @SuppressWarnings("unused")
    private void printData(int W, int[] v, int[] s, long[][] w) {
        System.out.println("--");
        System.out.println(W);
        System.out.println("v:" + Arrays.toString(v));
        System.out.println("s:" + Arrays.toString(s));
        Arrays.stream(w).map(Arrays::toString).forEach(System.out::println);
    }

}
