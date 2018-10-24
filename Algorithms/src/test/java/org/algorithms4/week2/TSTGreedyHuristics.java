package org.algorithms4.week2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Scanner;

import org.junit.Test;

public class TSTGreedyHuristics {

    private static final String TEST_FILE_LOCATION = "/Users/asela.illayapparachc/git/stanford-algs/testCases/course4/assignment3TSPHeuristic/";
    private static final String INPUT_FILE         = "input_simple_";
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
            double[][] vertices = new double[N][3];
            PriorityQueue<double[]> minHeap = new PriorityQueue<>(
                    (a, b) -> {
                        int v = Double.compare(a[3], b[3]);
                        return v == 0 ? Double.compare(a[0], b[0]) : v;
                    });
            for (int i = 0; i < N; i++) {
                vertices[i] = new double[] { scanner.nextDouble(), scanner.nextDouble(), scanner.nextDouble(), 0 };
                vertices[i][3] = distance(vertices[i], vertices[0]);
                minHeap.add(vertices[i]);
            }

            double totalDistance = 0D;
            double[] from = minHeap.poll();
            while (!minHeap.isEmpty()) {
                double[] to = minHeap.poll();
                totalDistance += distance(from, to);
                from = to;
            }

            totalDistance += distance(from, vertices[0]);

            return Long.toString((long) totalDistance);
        }
    }

    private double distance(double[] a, double[] b) {
        // System.out.println(Math.sqrt(Math.pow(a[0] - b[0], 2) + Math.pow(a[1] - b[1],
        // 2)));
        return Math.sqrt(Math.pow(a[1] - b[1], 2) + Math.pow(a[2] - b[2], 2));

    }

    @Test
    public void testForAll() throws Exception {

        Files.walk(Paths.get(TEST_FILE_LOCATION)).sequential().map(p -> p.toFile())
                .filter(f -> f.getName().contains(INPUT_FILE))
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
