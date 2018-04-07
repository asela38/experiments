package org.google.code._2018;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CubeUFo {

	static {
		try {
			System.setIn(new FileInputStream("club-ufo-input.txt"));
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
		for (int i = 1; i <= t; i++)
			solve(scanner.nextDouble(), i);

	}

	private static void solve0(double value, int j) {
		double ans = 0D;
		int i = 0;

		double minDiff = Double.MAX_VALUE;
		double range = 1_000_000D;
		double limit = range / 4;

		while (i <= limit) {
			double r = (i++ / range) * Math.PI;
			double n = Math.cos(r) + Math.sin(r);
			double diff = Math.abs(n - value);
			if (diff < minDiff) {
				minDiff = diff;
				ans = r;
			}
		}

		double cos = 0.5 * Math.cos(ans);
		double sin = 0.5 * Math.sin(ans);
		System.out.println("Case #" + j + ":");
		System.out.println(cos + " " + sin + " " + 0);
		System.out.println(-sin + " " + cos + " " + 0);
		System.out.println(0 + " " + 0 + " " + 0.5);

	}

	private static void solve(double value, int j) {
		solve0(value, j);

		double angleX = Math.PI / 4, angleY = 0;
		if (value < 1.414213D) {
			angleX = Math.asin(Math.pow(value, 2) - 1) / 2;
		}
		double cosX = 0.5 * Math.cos(angleX);
		double sinX = 0.5 * Math.sin(angleX);
		System.out.println("Case #" + j + ":");
		System.out.println(cosX + " " + sinX + " " + 0);
		System.out.println(-sinX + " " + cosX + " " + 0);
		System.out.println(0 + " " + 0 + " " + 0.5);

	}
}
