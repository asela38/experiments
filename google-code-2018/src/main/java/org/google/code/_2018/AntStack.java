package org.google.code._2018;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AntStack {

	static {
		try {
			System.setIn(new FileInputStream("ant-stack-input.txt"));
			// System.setOut(new PrintStream("club-ufo-out.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try (Scanner scanner = new Scanner(System.in)) {
			process(scanner);
		}
	}

	private static void process(Scanner scanner) {
		int t = scanner.nextInt();
		for (int i = 1; i <= t; i++) {
			int n = scanner.nextInt();
			int[] w = new int[n];
			for (int j = 0; j < n; j++)
				w[j] = scanner.nextInt();

			System.out.printf("Case #%d: %s%n", i, solve(w));

		}
	}

	private static int solve(int[] w) {
		return 0;
	}
}
