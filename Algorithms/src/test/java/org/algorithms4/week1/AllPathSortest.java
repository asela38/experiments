package org.algorithms4.week1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.LongStream;

import org.junit.Test;

public class AllPathSortest {

    private static final String TEST_FILE_LOCATION = "C:\\Users\\asela.illayapparachc\\git\\stanford-algs\\testCases\\course4\\assignment1AllPairsShortestPath\\";
    private static final String INPUT_FILE         = "input_random_";
    private static final String _1_2_FILE          = TEST_FILE_LOCATION + INPUT_FILE + "1_2.txt";
    private static final String _2_2_FILE          = TEST_FILE_LOCATION + INPUT_FILE + "2_2.txt";
    private static final String _17_32_FILE        = TEST_FILE_LOCATION + INPUT_FILE + "17_32.txt";
    private static final String _6_4_FILE          = TEST_FILE_LOCATION + INPUT_FILE + "6_4.txt";
    private static final String _32_256_FILE       = TEST_FILE_LOCATION + INPUT_FILE + "32_256.txt";
    private static final String _38_1024_FILE      = TEST_FILE_LOCATION + INPUT_FILE + "38_1024.txt";
    @SuppressWarnings("unused")
    private static final String JOB_FILE1          = "C:\\Asela\\mydocs\\Coursera\\apsp_g1.txt";
    private static final String JOB_FILE2          = "C:\\Asela\\mydocs\\Coursera\\apsp_g2.txt";
    private static final String JOB_FILE3          = "C:\\Asela\\mydocs\\Coursera\\apsp_g3.txt";

    @Test
    public void testFile() throws Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader(_1_2_FILE))) {
            reader.lines().forEach(System.out::println);
        }
    }

    @Test
    public void job() throws Exception {

        System.out.println(process(JOB_FILE1)); // -1947
        System.out.println(process(JOB_FILE2)); // -1947
        System.out.println(process(JOB_FILE3)); // -1947

    }

    @Test
    public void trivial() throws Exception {
        // System.out.println(process(_1_2_FILE)); // NULL
        // System.out.println(process(_2_2_FILE)); // -66
        // System.out.println(process(_6_4_FILE)); // -12
        // System.out.println(process(_32_256_FILE)); // -778
        System.out.println(process(_38_1024_FILE)); // -1947
        // System.out.println(process(_17_32_FILE)); // NULL
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

            long[] distanceBF = bellmanFord(GP, V + 1, V);

            boolean hasNegativeCycles = false;
            for (int[] e : GP) {
                if (distanceBF[e[0] - 1] + e[2] < distanceBF[e[1] - 1]) {
                    hasNegativeCycles = true;
                }
            }

            long min = Long.MAX_VALUE;
            if (!hasNegativeCycles) {
                Map<Integer, Set<int[]>> map = new HashMap<>();

                Map<String, Integer> mapd = new HashMap<>();
                for (int[] is : G) {
                    String key = is[0] + "-" + is[1];
                    Integer v = mapd.get(key);
                    if (v == null) {
                        mapd.put(key, is[2]);
                    } else {
                        mapd.put(key, Integer.min(is[2], v));
                        // System.out.println(Arrays.toString(is) + " / " + key + " - " + v + " / " + mapd.get(key));
                    }
                }

                // System.out.println(mapd.size());

                int[][] G2 = new int[mapd.size()][3];
                int ii = 0;
                for (Map.Entry<String, Integer> entry : mapd.entrySet()) {
                    String[] x = entry.getKey().split("-");
                    G2[ii++] = new int[] { Integer.parseInt(x[0]), Integer.parseInt(x[1]), entry.getValue() };
                }

                for (int[] is : G2) {
                    for (int i = 0; i < 1; i++) {
                        if (!map.containsKey(is[i])) {
                            map.put(is[i], new HashSet<>());
                        }
                        map.get(is[i]).add(is);
                    }

                }

                for (int i = 1; i <= V; i++) {
                    long[] dijkstra = dijkstra(V, map, i, distanceBF);
                    min = Long.min(min, LongStream.of(dijkstra).min().getAsLong());
                }

            }
            return hasNegativeCycles ? "NULL" : "" + min;
        }
    }

    private long[] bellmanFord(int[][] G, int V, int src) {

        long[][] distance = new long[V + 1][V];
        for (int i = 0; i < V; i++)
            distance[0][i] = Long.MAX_VALUE;
        distance[0][src] = 0;
        Map<Integer, Set<int[]>> map = new HashMap<>();
        for (int[] is : G) {
            for (int i = 1; i < 2; i++) {
                if (!map.containsKey(is[i])) {
                    map.put(is[i], new HashSet<>());
                }
                map.get(is[i]).add(is);
            }

        }

        for (int i = 1; i <= V; i++) {
            for (int v = 1; v <= V; v++) {
                Set<int[]> set = map.getOrDefault(v, new HashSet<>());
                long[] pre = distance[i - 1];
                int cv = v;
                OptionalLong min = set.stream()
                        .mapToLong(w -> Optional.ofNullable(w[0] == cv ? w[1] : w[0])
                                .map(j -> j - 1).filter(j -> pre[j] != Long.MAX_VALUE).map(j -> pre[j] + w[2])
                                .orElse(Long.MAX_VALUE))
                        .min();
                distance[i][v - 1] = min.isPresent() ? Long.min(pre[v - 1], min.orElse(Long.MAX_VALUE))
                        : pre[v - 1];
            }
        }

        return distance[V - 1];
    }

    static long[] dijkstra(int n, Map<Integer, Set<int[]>> adj, int s, long[] dist) {
        boolean[] visited = new boolean[n];
        long[] distance = new long[n];
        Arrays.fill(distance, Long.MAX_VALUE);

        PriorityQueue<Integer> heap = new PriorityQueue<>((a, b) -> Long.compare(
                distance[a - 1],
                distance[b - 1]));
        heap.offer(s);
        distance[s - 1] = 0;

        while (!heap.isEmpty()) {
            int v = heap.poll();
            if (visited[v - 1])
                continue;
            visited[v - 1] = true;
            for (int[] w : adj.getOrDefault(v, Collections.emptySet())) {
                distance[w[1] - 1] = Math.min(distance[w[1] - 1],
                        distance[w[0] - 1] + (w[2] + dist[w[0] - 1] - dist[w[1] - 1]));
                heap.offer(w[1]);

            }

        }

        long[] distance2 = new long[n];
        int j = 0;
        for (int i = 0; i < n; i++) {

            distance2[j++] = distance[i] != Long.MAX_VALUE ? (distance[i] - dist[s - 1] + dist[i])
                    : Long.MAX_VALUE;

        }

        return distance2;
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
