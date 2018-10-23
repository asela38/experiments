package org.algorithms3.week3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.junit.Test;

public class HuffmanChallenge {

	private static final String TEST_FILE_LOCATION = "/Users/asela.illayapparachc/git/stanford-algs/testCases/course3/assignment3HuffmanAndMWIS/question1And2/";
	private static final String INPUT_FILE = "input_random_";
	private static final String _1_10_FILE = TEST_FILE_LOCATION + INPUT_FILE + "1_10.txt";
	private static final String _37_4000_FILE = TEST_FILE_LOCATION + INPUT_FILE + "37_4000.txt";
	private static final String JOB_FILE = "/Users/asela.illayapparachc/Documents/algorithms/huffman.txt";

	@Test
	public void testFile() throws Exception {
		try (BufferedReader reader = new BufferedReader(new FileReader(_1_10_FILE))) {
			reader.lines().forEach(System.out::println);
		}
	}

	@Test
	public void trivial() throws Exception {
		// System.out.println(process(_1_10_FILE)); // [5,2]
		// System.out.println(process(_37_4000_FILE)); // [23,11]
		System.out.println(process(JOB_FILE)); // 106
	}

	@Test
	public void testForAll() throws Exception {

		Files.walk(Paths.get(TEST_FILE_LOCATION)).map(p -> p.toFile())
				.filter(f -> f.getName().contains(INPUT_FILE))
				.sorted((a, b) -> Long.compare(a.length(), b.length())).forEach(f -> {
					try {
						String outputFile = f.getAbsolutePath().replace("input", "output");
						List<String> output = new ArrayList<>();
						try (BufferedReader reader = new BufferedReader(new FileReader(outputFile))) {
							output = reader.lines().collect(Collectors.toList());
						} catch (IOException e) {
							e.printStackTrace();
						}
						List<Long> result = process(f.getAbsolutePath());
						System.out.printf("Expected : %10s Calculated: %10s %6s File: %s %n", output,
								result, output.toString().equals(result.toString()), f.getName());
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				});
	}

	private List<Long> process(String file) throws FileNotFoundException {
		try (Scanner scanner = new Scanner(new File(file))) {
			int n = scanner.nextInt();

			long[][] alphabet = new long[n][3];
			PriorityQueue<long[]> heap = new PriorityQueue<>((a, b) -> Long.compare(a[1], b[1]));
			for (int i = 0; i < n; i++) {
				alphabet[i][1] = scanner.nextLong();
				heap.offer(alphabet[i]);
			}

			while (heap.size() > 1) {
				long[] a = heap.poll(), b = heap.poll();
				long[] metaSymbol = new long[3];
				metaSymbol[1] = a[1] + b[1];
				metaSymbol[2] = Long.max(a[2], b[2]) + 1;
				metaSymbol[0] = Long.min(a[0], b[0]) + 1;
				heap.offer(metaSymbol);

			}

			long[] root = heap.poll();
			return Arrays.asList(root[2], root[0]);
		}
	}
}
