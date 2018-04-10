package org.google.code._2018;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

public class TroubleSort {
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
			System.out.printf("Case #%d: %s %n", i, sanitize(troubleSort(v)));
			// troubleSort(v);
		}
	}

	private static String sanitize(int value) {
		return value == -1 ? "OK" : String.valueOf(value);
	}

	private static int troubleSort2(int[] l) {
		int errorIndex = -1;
		int length = l.length / 2;
		int[] l1 = new int[l.length - length], l2 = new int[length];
		{
			int i = 0, j = 0;
			for (; j < length; i += 2, j++) {
				l1[j] = l[i];
				l2[j] = l[i + 1];
			}
			if (l.length - length > length)
				l1[j] = l[i];
		}

		Arrays.sort(l1);
		Arrays.sort(l2);
		System.out.println(Arrays.toString(l1));
		System.out.println(Arrays.toString(l2));
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

	private static int troubleSort(int[] l) {
		// System.out.printf("Sorting : %s%n", Arrays.toString(l));
		boolean done = false;
		while (!done) {
			done = true;
			for (int i = 0; i < l.length - 2; i++)
				if (l[i] > l[i + 2]) {
					done = false;
					int temp = l[i];
					l[i] = l[i + 2];
					l[i + 2] = temp;
					// System.out.printf("Rotate %s%n", Arrays.toString(l));
				}
		}
		int errorIndex = -1;
		for (int i = 0; i < l.length - 1; i++)
			if (l[i] > l[i + 1]) {
				errorIndex = i;
				break;
			}

		// getout(errorIndex == -1).printf("Sorted : %s error-index:%d%n%n",
		// Arrays.toString(l), errorIndex);
		return errorIndex;
	}

	private static PrintStream getout(boolean c) {
		return c ? System.out : System.err;
	}
}
