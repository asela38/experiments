package org.algorithms4.week4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;

import com.google.common.base.Stopwatch;

public class TwoSat {

    private static final String TEST_FILE_LOCATION = "/Users/asela.illayapparachc/git/stanford-algs/testCases/course4/assignment4TwoSat/";
    private static final String INPUT_FILE         = "input_beaunus_";
    private static final String _1_2_FILE          = TEST_FILE_LOCATION + INPUT_FILE + "1_2.txt";
    private static final String _2_2_FILE          = TEST_FILE_LOCATION + INPUT_FILE + "2_2.txt";
    private static final String _4_4_FILE          = TEST_FILE_LOCATION + INPUT_FILE + "4_4.txt";
    private static final String _9_20_FILE         = TEST_FILE_LOCATION + INPUT_FILE + "9_20.txt";
    private static final String _19_6_FILE         = TEST_FILE_LOCATION + INPUT_FILE + "19_6.txt";
    private static final String _26_8_FILE         = TEST_FILE_LOCATION + INPUT_FILE + "26_8.txt";
    private static final String _32_256_FILE       = TEST_FILE_LOCATION + INPUT_FILE + "32_256.txt";
    private static final String _38_1024_FILE      = TEST_FILE_LOCATION + INPUT_FILE + "38_1024.txt";
    @SuppressWarnings("unused")
    private static final String JOB_FILE1          = "/Users/asela.illayapparachc/Desktop/code/nn.txt";

    @Test
    public void testFile() throws Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader(_9_20_FILE))) {
            reader.lines().forEach(System.out::println);
        }
    }

    @Test
    public void job() throws Exception {

        System.out.println(process(JOB_FILE1)); // -1947

    }

    @Test
    public void trivial() throws Exception {
        System.out.println(process(_9_20_FILE)); // 0
    }

    private String process(String file) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new File(file))) {
            int N = scanner.nextInt();
            Map<Integer, Set<Integer>> map = new HashMap<>();
            Set<Integer> set = new HashSet<>();
            while (scanner.hasNextInt()) {
                int u = scanner.nextInt(), v = scanner.nextInt();
                if (u == -v)
                    continue;
                if (u == v) {
                    if (!set.add(Math.abs(u))) {
                        return "0";
                    }
                    continue;
                }

                if (!map.containsKey(-u))
                    map.put(-u, new HashSet<>());
                if (!map.containsKey(-v))
                    map.put(-v, new HashSet<>());
                map.get(-u).add(v);
                map.get(-v).add(u);
            }

            if (map.size() == 0)
                return "0";

            Map<Integer, Integer> leader = new SCC2().getleader(map);

            // System.out.println(leader);

            Map<Integer, Set<Entry<Integer, Integer>>> collect = leader.entrySet().stream()
                    .collect(Collectors.groupingBy(e -> e.getValue(), Collectors.toSet()));

            for (Set<Entry<Integer, Integer>> entrySet : collect.values()) {
                int[] arrayRelated = entrySet.stream().map(e -> e.getKey()).mapToInt(Integer::intValue).toArray();
                int[] array = Arrays.stream(arrayRelated).map(Math::abs)
                        .toArray();
                if (array.length != Arrays.stream(array).boxed().collect(Collectors.toSet()).size()) {
                    return "0";
                }
            }

            return Long.toString((long) 1);
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
