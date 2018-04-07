package org.google.code._2018;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class TroubleSort {
	static {
		try {
			System.setIn(new FileInputStream("trouble-sort-input.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try (Scanner scanner = new Scanner(System.in)) {
			process(scanner);
		}
	}

	public static void process(Scanner scanner) {
		Integer t = scanner.nextInt();
		for (int i = 1; i <= t; i++) {
			int n = scanner.nextInt();
			int[] v = new int[n];
			for (int j = 0; j < n; j++)
				v[j] = scanner.nextInt();
			System.out.printf("Case #%d: %s %n", i, Arrays.toString(v));
		}
	}
}
