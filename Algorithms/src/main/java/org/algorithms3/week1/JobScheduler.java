package org.algorithms3.week1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import org.junit.Test;

public class JobScheduler {

    private static final String TEST_FILE_DIR      = "C:\\Users\\asela.illayapparachc\\git\\stanford-algs\\testCases\\course3\\assignment1SchedulingAndMST\\questions1And2\\";
    private static final String TEST_FILE_LOCATION = TEST_FILE_DIR + "input_random_";
    private static final String JOB_FILE           = "C:\\Asela\\mydocs\\Coursera\\jobs.txt";
    private static final String TEST_FILE          = TEST_FILE_LOCATION + "44_10000.txt";

    @Test
    public void checkFileReadability() throws Exception {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(JOB_FILE))) {
            bufferedReader.lines().forEach(System.out::println);
        }
    }

    @Test
    public void trivalAsInt() throws Exception {
        try (Scanner scanner = new Scanner(new File(TEST_FILE))) {
            int n = scanner.nextInt();
            // 0 - weight | 1 - length | 2 - priority
            PriorityQueue<int[]> heap = new PriorityQueue<>((b, a) -> {
                int result = Integer.compare(a[2], b[2]);
                if (result == 0) {
                    result = Integer.compare(a[0], b[0]);
                }
                return result;
            });

            for (int i = 0; i < n; i++) {
                int[] job = new int[3];
                job[0] = scanner.nextInt();
                job[1] = scanner.nextInt();
                job[2] = job[0] - job[1];
                heap.offer(job);

            }

            long aggregatedLegnth = 0, weightedCompletionTime = 0;
            for (int i = 0; i < n; i++) {
                int[] min = heap.poll();
                System.out.println(Arrays.toString(min));
                aggregatedLegnth += min[1];
                weightedCompletionTime += aggregatedLegnth * min[0];

            }
            System.out.println(weightedCompletionTime);
        }
    }

    @Test
    public void trivalAsDouble() throws Exception {
        try (Scanner scanner = new Scanner(new File(TEST_FILE))) {
            double n = scanner.nextDouble();
            // 0 - weight | 1 - length | 2 - priority
            PriorityQueue<double[]> maxHeap = new PriorityQueue<>((b, a) -> {
                int result = Double.compare(a[2], b[2]);
                if (result == 0) {
                    result = Double.compare(a[0], b[0]);
                }
                return result;
            });

            for (int i = 0; i < n; i++) {
                double[] job = new double[3];
                job[0] = scanner.nextDouble();
                job[1] = scanner.nextDouble();
                job[2] = job[0] / job[1];
                maxHeap.offer(job);

            }

            long aggregatedLegnth = 0, weightedCompletionTime = 0;
            for (int i = 0; i < n; i++) {
                double[] min = maxHeap.poll();
                System.out.println(Arrays.toString(min));
                aggregatedLegnth += min[1];
                weightedCompletionTime += aggregatedLegnth * min[0];

            }
            System.out.println(weightedCompletionTime);
        }
    }

    @Test
    public void testForAll() throws Exception {

        Files.walk(Paths.get(TEST_FILE_DIR)).map(p -> p.toFile()).filter(f -> f.getName().contains("input_random"))
                .sorted((a, b) -> Long.compare(a.length(), b.length())).forEach(f -> {
                    try {
                        String outputFile = f.getAbsolutePath().replace("input", "output");
                        List<String> output = Collections.emptyList();
                        try (BufferedReader reader = new BufferedReader(new FileReader(outputFile))) {
                            output = reader.lines().collect(Collectors.toList());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        List<Long> result = Collections.emptyList();
                        System.out.printf("Expected : %10s Calculated: %10s %6s File: %s %n", output,
                                result = process(f.getAbsolutePath()), output.toString().equals(result.toString()),
                                f.getName());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    public void trivalAsDoubleGeneralized() throws Exception {
        String jobFile = JOB_FILE;
        System.out.println(process(jobFile).stream().map(Objects::toString).collect(Collectors.joining("\n")));
    }

    private List<Long> process(String jobFile) throws FileNotFoundException {
        List<Long> results = new ArrayList<>();
        BiFunction<Double, Double, Double> difference = (a, b) -> a - b, ratio = (a, b) -> a / b;
        try (Scanner scanner = new Scanner(new File(jobFile))) {
            int n = scanner.nextInt();
            // 0 - weight | 1 - length | 2 - priority

            double[][] jobs = new double[n][3];

            for (int i = 0; i < n; i++) {
                jobs[i][0] = scanner.nextDouble();
                jobs[i][1] = scanner.nextDouble();
            }

            PriorityQueue<double[]> maxHeap = new PriorityQueue<>((b, a) -> {
                int result = Double.compare(a[2], b[2]);
                if (result == 0) {
                    result = Double.compare(a[0], b[0]);
                }
                return result;
            });
            for (BiFunction<Double, Double, Double> function : Arrays.asList(difference, ratio)) {

                for (int i = 0; i < n; i++) {
                    double[] job = jobs[i];
                    job[2] = function.apply(job[0], job[1]);
                    maxHeap.offer(job);
                }

                long aggregatedLegnth = 0, weightedCompletionTime = 0;
                for (int i = 0; i < n; i++) {
                    double[] min = maxHeap.poll();
                    aggregatedLegnth += min[1];
                    weightedCompletionTime += aggregatedLegnth * min[0];

                }
                results.add(weightedCompletionTime);
            }

        }
        return results;
    }
}
