package org.algorithms3.week1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;

public class MSTExploration {

    private static final Logger LOGGER             = Logger.getAnonymousLogger();

    private static final String TEST_FILE_LOCATION = "C:\\Users\\asela.illayapparachc\\git\\stanford-algs\\testCases\\course3\\assignment1SchedulingAndMST\\question3\\";
    private static final String INPUT_FILE         = "input_random_";
    private static final String _1_10_FILE         = TEST_FILE_LOCATION + INPUT_FILE + "1_10.txt";
    private static final String _2_10_FILE         = TEST_FILE_LOCATION + INPUT_FILE + "2_10.txt" /*-12829*/;
    private static final String _9_40_FILE         = TEST_FILE_LOCATION + INPUT_FILE + "9_40.txt" /*-49942*/ ;
    private static final String _5_20_FILE         = TEST_FILE_LOCATION + INPUT_FILE + "5_20.txt" /*-10519*/ ;
    private static final String _68_100000_FILE    = TEST_FILE_LOCATION + INPUT_FILE + "68_100000.txt" /* -229263767*/;
    private static final String JOB_FILE           = "C:\\Asela\\mydocs\\Coursera\\edges.txt";
    @Test
    public void testFile() throws Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader(_1_10_FILE))) {
            reader.lines().forEach(System.out::println);
        }
    }

    @Test
    public void trivial() throws Exception {
        LOGGER.setLevel(Level.ALL);
        System.out.println(process(_1_10_FILE)); // -7430
        System.out.println(process(_5_20_FILE)); // -10519
        System.out.println(process(_2_10_FILE)); // -12829
        System.out.println(process(_9_40_FILE)); // -49942
        System.out.println(process(_68_100000_FILE)); // -229263767
        System.out.println(process(JOB_FILE)); // -10519
    }

    @Test
    public void testForAll() throws Exception {
        LOGGER.setLevel(Level.FINE);
        // new StopW

        Files.walk(Paths.get(TEST_FILE_LOCATION)).map(p -> p.toFile()).filter(f -> f.getName().contains("input_random"))
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
            Map<Integer, Map<Integer, Integer>> adj = new HashMap<>();
            int n = scanner.nextInt();
            int m = scanner.nextInt();

            for (int i = 0; i < m; i++) {
                int v = scanner.nextInt();
                int w = scanner.nextInt();
                int d = scanner.nextInt();
                adj.putIfAbsent(v, new HashMap<>());
                adj.putIfAbsent(w, new HashMap<>());
                d = Integer.min(d, adj.get(v).getOrDefault(w, d));
                adj.get(v).put(w, d);
                adj.get(w).put(v, d);
            }

            // System.out.println("ADJ : " + adj);

            boolean[] mst = new boolean[n];
            int[] shortest = new int[n];
            Arrays.fill(shortest, Integer.MAX_VALUE);

            PriorityQueue<Integer> heap = new PriorityQueue<>(
                    (a, b) -> Integer.compare(shortest[a - 1], shortest[b - 1]));
            shortest[0] = 0;
            heap.offer(1);
            while (!heap.isEmpty()) {
                Integer v = heap.poll();
                // System.out.printf("%4s - %10s%n", v, shortest[v - 1]);
                mst[v - 1] = true;
                for (Integer w : adj.getOrDefault(v, new HashMap<>()).keySet()) {
                    if (!mst[w - 1]) {
                        shortest[w - 1] = Math.min(shortest[w - 1], adj.get(v).get(w));
                        heap.remove(w);
                        heap.offer(w);
                    }

                }
                // System.out.println(Arrays.stream(shortest).filter(i -> i != Integer.MAX_VALUE).boxed()
                // .map(Objects::toString).collect(Collectors.joining(", ")));
                // System.out.println(Arrays.toString(shortest));
            }

            return Arrays.stream(shortest).asLongStream().sum();

        }
    }
}
