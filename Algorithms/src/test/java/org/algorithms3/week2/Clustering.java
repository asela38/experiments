package org.algorithms3.week2;

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
import java.util.Scanner;
import java.util.logging.Logger;

import org.junit.Test;

public class Clustering {

    private static final Logger LOGGER             = Logger.getAnonymousLogger();

    private static final String TEST_FILE_LOCATION = "C:\\Users\\asela.illayapparachc\\git\\stanford-algs\\testCases\\course3\\assignment2Clustering\\question1\\";
    private static final String INPUT_FILE         = "input_completeRandom_";
    private static final String _1_8_FILE          = TEST_FILE_LOCATION + INPUT_FILE + "1_8.txt";
    private static final String JOB_FILE           = "C:\\Asela\\mydocs\\Coursera\\clustering1.txt";

    @Test
    public void testFile() throws Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader(_1_8_FILE))) {
            reader.lines().forEach(System.out::println);
        }
    }

    @Test
    public void trivial() throws Exception {
        System.out.println(process(_1_8_FILE)); // 21
        System.out.println(process(JOB_FILE)); // 106
    }

    @Test
    public void testForAll() throws Exception {

        Files.walk(Paths.get(TEST_FILE_LOCATION)).map(p -> p.toFile()).filter(f -> f.getName().contains("input_completeRando"))
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
            int n = scanner.nextInt();
            List<int[]> adj = new ArrayList<>();

            while (scanner.hasNext()) {
                adj.add(new int[] { scanner.nextInt(), scanner.nextInt(), scanner.nextInt() });
            }

            Collections.sort(adj, (a, b) -> Integer.compare(a[2], b[2]));

            int[] parent = new int[n];
            // In the beginning, each node is it's root
            for (int i = 0; i < n; i++)
                parent[i] = i;

           
            int noOfClusters = n, lastWieght = 0;
            for(int[] a : adj) {
           //     System.out.println(Arrays.toString(a));
                int p1 = find(a[0], parent);
                int p2 = find(a[1], parent);
                
                if(noOfClusters <= 3) continue;
                // if parents are same they are in same cluster => nothing to merge
               
                if(p1 == p2)
                    continue;
                
                // change the parent
                parent[p1] = p2;
                lastWieght = a[2];
                noOfClusters--;
            }
//            
//            for(int i = 1 ; i < n + 1; i++)
//                System.out.println(find(i, parent));

            System.out.println(Arrays.stream(parent).distinct().count());
            return lastWieght;
        }
    }

    private int find(int i, int[] parent) {
        int p = i - 1;
        while (p != parent[p])
            p = parent[p];

        parent[i - 1] = p;
        return p;
    }
}
