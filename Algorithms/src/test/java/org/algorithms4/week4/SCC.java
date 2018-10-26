package org.algorithms4.week4;

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
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import com.google.common.base.Stopwatch;

public class SCC {

    private static final String TEST_FILE_LOCATION = "/Users/asela.illayapparachc/git/stanford-algs/testCases/course2/assignment1SCC/";
    private static final String INPUT_FILE         = "input_mostlyCycles_";
    private static final String _1_8_FILE          = TEST_FILE_LOCATION + INPUT_FILE + "1_8.txt";
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
        try (BufferedReader reader = new BufferedReader(new FileReader(_1_8_FILE))) {
            reader.lines().forEach(System.out::println);
        }
    }

    @Test
    public void job() throws Exception {

        System.out.println(process(JOB_FILE1)); // -1947

    }

    @Test
    public void trivial() throws Exception {
        System.out.println(process(_1_8_FILE)); // 19
    }

    private String process(String file) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new File(file))) {
            Map<Integer, Set<Integer>> map = new HashMap<>();
            while (scanner.hasNextInt()) {
                int u = scanner.nextInt(), v = scanner.nextInt();
                if (!map.containsKey(u))
                    map.put(u, new HashSet<>());
                if (!map.containsKey(v))
                    map.put(v, new HashSet<>());
                map.get(u).add(v);
                map.get(v).add(u);
            }

            AtomicInteger t = new AtomicInteger(0);
            Integer s = null;

            Set<Integer> explored = new HashSet<>();
            Map<Integer, Integer> finish = new HashMap<>();
            Map<Integer, Integer> leader = new HashMap<>();
            for (Integer i : map.keySet()) {
                if (!explored.contains(i)) {
                    s = i;
                    dfs(map, i, s, t, finish, explored, leader);
                }
            }

            Map<Integer, Long> sum = leader.values().stream()
                    .collect(Collectors.groupingBy(i -> i, Collectors.counting()));
            return Stream.concat(sum.values().stream(), Stream.of(0, 0, 0, 0, 0)).mapToInt(Number::intValue)
                    .map(i -> -i)
                    .sorted().limit(5)
                    .map(i -> -i)
                    .mapToObj(Objects::toString).collect(Collectors.joining(","));

            // return Long.toString((long) map.size());
        }
    }

    private void dfs(Map<Integer, Set<Integer>> map, Integer i, Integer s, AtomicInteger t,
            Map<Integer, Integer> finish, Set<Integer> explored, Map<Integer, Integer> leader) {
        explored.add(i);
        leader.put(i, s);
        for (Integer w : map.get(i)) {
            if (!explored.contains(w)) {
                dfs(map, w, s, t, finish, explored, leader);
            }
        }
        finish.put(i, t.incrementAndGet());

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
                        System.out.printf("Expected : %30s Calculated: %10s %6s File: %40s %s %3s %n", output,
                                result = process(f.getAbsolutePath()), Objects.equals(result, output), f.getName(),
                                sw.elapsed().getSeconds(), "s");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                });
    }



}
