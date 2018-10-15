package org.algorithms3.week2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

import org.junit.Test;

public class ClusteringBig {


    private static final String TEST_FILE_LOCATION = "C:\\Users\\asela.illayapparachc\\git\\stanford-algs\\testCases\\course3\\assignment2Clustering\\question2\\";
    private static final String INPUT_FILE         = "input_random_";
    private static final String _1_4_14_FILE       = TEST_FILE_LOCATION + INPUT_FILE + "1_4_14.txt";
    @SuppressWarnings("unused")
    private static final String _5_4_4_FILE       = TEST_FILE_LOCATION + INPUT_FILE + "5_4_4.txt";
    @SuppressWarnings("unused")
    private static final String _3_4_8_FILE       = TEST_FILE_LOCATION + INPUT_FILE + "3_4_8.txt";
    private static final String JOB_FILE           = "C:\\Asela\\mydocs\\Coursera\\clustering_big.txt";

    @Test
    public void testFile() throws Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader(_1_4_14_FILE))) {
            reader.lines().forEach(System.out::println);
        }
    }

    @Test
    public void trivial() throws Exception {
     //   System.out.println(process(_1_4_14_FILE)); // 3
    //    System.out.println(process(_5_4_4_FILE)); // 2
  //      System.out.println(process(_3_4_8_FILE)); // 2
        System.out.println(process(JOB_FILE)); // 2
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
            int n = scanner.nextInt();
            @SuppressWarnings("unused")
            int b = scanner.nextInt();
            scanner.nextLine();
            int[] parents = new int[n];
            
            for(int i = 0; i < n ; i++) parents[i] = i;
            
            int[] node = new int[n];
            
            for (int i = 0; i < n; i++) {
                String line = scanner.nextLine();
                node[i] = Integer.parseInt(line.replaceAll("\\s+", ""), 2);
                for(int j = 0; j < i; j++) {
                    int d = Integer.bitCount(node[j] ^ node[i]);
               //     System.out.printf("%s - %s = %d %n", node[j], node[i], d);
                    if(d < 3) {
                        int p1 = find(j, parents);
                        int p2 = find(i, parents);
                        if(p1 == p2) continue;
                        
                        parents[p1] = p2;
                    }
                }
               
            }
            
            //Arrays.stream(node).mapToObj(Integer::toBinaryString).forEach(System.out::println);

            return Arrays.stream(parents).parallel().map(i -> find(i, parents)).distinct().count();
        }
    }

    private int find(int i, int[] parent) {
        int p = i ;
        while (p != parent[p])
            p = parent[p];

        parent[i] = p;
        return p;
    }
}
