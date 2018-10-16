package org.algorithms4.week1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Scanner;
import java.util.Set;

import org.junit.Test;

public class AllPathSortest {

    private static final String TEST_FILE_LOCATION = "C:\\Users\\asela.illayapparachc\\git\\stanford-algs\\testCases\\course4\\assignment1AllPairsShortestPath\\";
    private static final String INPUT_FILE         = "input_random_";
    private static final String _1_2_FILE          = TEST_FILE_LOCATION + INPUT_FILE + "1_2.txt";
    private static final String _2_2_FILE          = TEST_FILE_LOCATION + INPUT_FILE + "2_2.txt";
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
        // System.out.println(process(_1_2_FILE)); // NULL
        System.out.println(process(_2_2_FILE)); // -66
    }

    private String process(String file) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new File(file))) {
            int V = scanner.nextInt();
            int E = scanner.nextInt();

            int[][] G = new int[E][3];
            int[][] GP = new int[E + V][3];
            for (int i = 0; i < E; i++)
                for (int j = 0; j < 3; j++)
                    GP[i][j] = G[i][j] = scanner.nextInt();

            for (int i = E, j = 0; j < V; j++) {
                GP[i++] = new int[] { V + 1, j + 1, 0 };
            }

            int[] distanceBF = bellmanFord(GP, V + 1, V);

            boolean hasNegativeCycles = false;
            for (int[] e : GP) {
                if (distanceBF[e[0] - 1] + e[2] < distanceBF[e[1] - 1]) {
                    hasNegativeCycles = true;
                }
            }

            return hasNegativeCycles ? "NULL" : "";
        }
    }

    private int[] bellmanFord(int[][] G, int V, int src) {

        int[][] distance = new int[V][V];
        for (int i = 0; i < V; i++)
            distance[0][i] = Integer.MAX_VALUE;
        distance[0][src] = 0;
        Map<Integer, Set<int[]>> map = new HashMap<>();
        for (int[] is : G) {
            for (int i = 0; i < 2; i++) {
                if (!map.containsKey(is[i])) {
                    map.put(is[i], new HashSet<>());
                }
                map.get(is[i]).add(is);
            }

        }

        for (int i = 1; i < V; i++) {
            for (int v = 1; v <= V; v++) {
                Set<int[]> set = map.get(v);
                int[] pre = distance[i - 1];
                int cv = v;
                OptionalInt min = set.stream()
                        // .peek(w -> System.out.println(Arrays.toString(w)))
                        .mapToInt(w -> Optional.ofNullable(w[0] == cv ? w[1] : w[0]).filter(j -> j != Integer.MAX_VALUE)
                                .map(j -> j - 1).map(j -> j + w[2]).orElse(Integer.MAX_VALUE))
                        .min();
                distance[i][v - 1] = Integer.min(pre[v - 1], min.orElse(Integer.MAX_VALUE));
            }
        }

        return distance[V - 1];
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
