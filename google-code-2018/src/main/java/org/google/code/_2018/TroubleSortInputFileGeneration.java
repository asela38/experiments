package org.google.code._2018;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TroubleSortInputFileGeneration {
	private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

	static {
		try {
			System.setOut(new PrintStream("trouble-sort-input-2.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		int t = 100;// RANDOM.nextInt(1, 101);
		System.out.println(t);
		for (int i = 0; i < t; i++) {
			int n = RANDOM.nextInt(3, 101);
			System.out.println(n);
			System.out.println(IntStream.generate(() -> RANDOM.nextInt(0, 1_000_000_000)).limit(n).boxed()
					.map(String::valueOf).collect(Collectors.joining(" ")));
		}
	}

}
