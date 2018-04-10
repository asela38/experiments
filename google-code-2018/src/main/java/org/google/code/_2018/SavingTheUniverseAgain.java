package org.google.code._2018;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class SavingTheUniverseAgain {
	static {
		try {
			System.setIn(new FileInputStream("save-the-universe-again-input.txt"));
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
		for (int i = 1; i <= t; i++)
			System.out.printf("Case #%d: %s%n", i, sanitize(minimumNumberOfChanges(scanner.nextInt(), scanner.next())));

	}

	private static Object sanitize(Integer value) {
		return value < 0 ? "IMPOSSIBLE" : value;
	}

	private static Integer minimumNumberOfChanges(Integer maxD, String originalP) {
		String p = originalP;
		boolean damageIsCritical = true;
		int modificationCout = 0;
		while (damageIsCritical = maxD < calculateDamage(p))
			if (p.contains("CS")) {
				int i = p.lastIndexOf("CS");
				p = p.substring(0, i) + p.substring(i).replaceFirst("CS", "SC");
				modificationCout++;
			} else
				break;

		return damageIsCritical ? -1 : modificationCout;
	}

	private static Integer calculateDamage(String p) {
		int d = 1, t = 0;
		for (char command : p.toCharArray()) {
			switch (command) {
			case 'S':
				t += d;
				break;
			case 'C':
				d *= 2;
				break;
			}
		}
		return t;
	}
}
