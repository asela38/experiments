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
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;

public class MaximumWeightIndipendentSet {

	private static final String TEST_FILE_LOCATION = "/Users/asela.illayapparachc/git/stanford-algs/testCases/course3/assignment3HuffmanAndMWIS/question3/";
	private static final String INPUT_FILE = "input_random_";
	private static final String _1_10_FILE = TEST_FILE_LOCATION + INPUT_FILE + "1_10.txt";
	private static final String _29_1000_FILE = TEST_FILE_LOCATION + INPUT_FILE + "29_1000.txt";
	private static final String JOB_FILE = "/Users/asela.illayapparachc/Documents/algorithms/mwis.txt";

	@Test
	public void testFile() throws Exception {
		try (BufferedReader reader = new BufferedReader(new FileReader(_1_10_FILE))) {
			reader.lines().forEach(System.out::println);
		}
	}

	@Test
	public void trivial() throws Exception {
		// System.out.println(process(_1_10_FILE)); // 10100000
		System.out.println(process(_29_1000_FILE)); // 10010100
		// System.out.println(process(_37_4000_FILE)); // [23,11]
		System.out.println(process(JOB_FILE)); // 10100110
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
						List<String> result = Arrays.asList(process(f.getAbsolutePath()));
						System.out.printf("Expected : %10s Calculated: %10s %6s File: %s %n", output,
								result, output.toString().equals(result.toString()), f.getName());
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				});
	}

	private String process(String file) throws FileNotFoundException {
		try (Scanner scanner = new Scanner(new File(file))) {
			int n = scanner.nextInt();

			int[] weight = new int[n + 1];
			for (int i = 1; i < weight.length; i++)
				weight[i] = scanner.nextInt();

			long[] mwis = new long[n + 1];
			mwis[0] = 0;
			mwis[1] = weight[1];
			for (int i = 2; i <= n; i++) {
				mwis[i] = Long.max(mwis[i - 2] + weight[i], mwis[i - 1]);
				// System.out.println(mwis[i]);
			}

			Set<Integer> set = new HashSet<>();
			for (int i = n; i >= 1; i--) {
				if (i - 2 == -1) {
					set.add(i);
					continue;
				}
				if (mwis[i - 1] < mwis[i - 2] + weight[i]) {
					set.add(i);
					i--;
				}
			}

			List<Integer> list = Arrays.asList(1, 2, 3, 4, 17, 117, 517, 997);

			return IntStream.range(1, 1000).filter(i -> list.contains(i))
					.map(i -> set.contains(i) ? 1 : 0)
					.mapToObj(Objects::toString)
					.collect(Collectors.joining());

		}
	}
}
