package org.google.code._2018;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Solution {
	static {
		try {
			System.setIn(new FileInputStream("trouble-sort-input-2.txt"));
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
			System.out.printf("Case #%d: %s %n", i, sanitize(troubleSort2(v)));
		}
	}

	private static String sanitize(int value) {
		return value == -1 ? "OK" : String.valueOf(value);
	}

	private static int troubleSort2(int[] l) {
		int errorIndex = -1;
		int[] l1 = new int[(l.length + 1) / 2], l2 = new int[l.length / 2];
		{
			int i = 0, j = 0;
			for (; i < l.length - 2; i += 2, j++) {
				l1[j] = l[i];
				l2[j] = l[i + 1];
			}
			if (i < l.length)
				l1[j] = l[i];
		}

		Arrays.sort(l1);
		Arrays.sort(l2);

		{
			int i = 0, j = 0;
			for (; i < l.length - 1; i += 2, j++)
				if (l1[j] > l2[j]) {
					errorIndex = i;
					break;
				} else if (l2[j] > l1[j + 1]) {
					errorIndex = i + 1;
					break;
				}
		}
		return errorIndex;
	}

}
