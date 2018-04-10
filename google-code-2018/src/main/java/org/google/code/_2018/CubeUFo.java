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

	private static void solve(double value, int j) {

		double angleX = Math.PI / 4, angleY = 0;
		if (value < 1.414213D) {
			angleX = Math.asin(Math.pow(value, 2) - 1) / 2;
		} else {
			angleY = Math.acos(Math.sqrt(2) * 2 * (value - 1.414213D));
		}
		double cosX = 0.5 * Math.cos(angleX);
		double sinX = 0.5 * Math.sin(angleX);
		double cosY = Math.cos(angleY);
		double sinY = Math.sin(angleY);
		System.out.println("Case #" + j + ":");
		System.out.println((cosX * sinY) + " " + (sinX * sinY) + " " + (0.5 * cosY));
		System.out.println(-sinX + " " + cosX + " " + 0);
		System.out.println(0 + " " + 0 + " " + 0.5);

	}
}
