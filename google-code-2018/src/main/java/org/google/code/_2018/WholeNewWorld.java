package org.google.code._2018;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Hello world!
 *
 */
public class WholeNewWorld {
	static {
		try {
			System.setIn(new FileInputStream("whole-new-world-input.txt"));
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
			int l = scanner.nextInt();
			HashSet<String>[] letters = new HashSet[l];
			HashSet<String> words = new HashSet<>();
			for (int j = 0; j < n; j++) {
				String word = scanner.next();
				words.add(word);
				char[] chars = word.toCharArray();
				for (int k = 0; k < l; k++) {
					if (letters[k] == null)
						letters[k] = new HashSet<String>();
					letters[k].add(String.valueOf(chars[k]));
				}
			}
			System.out.printf("Case #%d: %s%n", i, sanitize(solve(letters, n, l, words)));
		}
	}

	private static Object sanitize(String value) {
		return value == null ? "-" : value;
	}

	private static String solve(HashSet<String>[] words, int n, int l, HashSet<String> dictionary) {
		if (n == 1 || l == 1)
			return null;

		long possiblePermutations = Arrays.stream(words).map(word -> word.size()).reduce(1, (x, y) -> x * y)
				.longValue();

		if (possiblePermutations <= n)
			return null;

		ArrayList<String>[] letters = new ArrayList[l];
		for (int i = 0; i < l; i++) {
			letters[i] = new ArrayList<>(words[i]);
		}

		String newWord = null;
		while (newWord == null) {
			newWord = "";
			for (int i = 0; i < l; i++) {
				List<String> ll = letters[i];
				newWord += ll.get(ThreadLocalRandom.current().nextInt(0, ll.size()));
			}

			if (dictionary.contains(newWord))
				newWord = null;
		}

		return newWord;
	}

}
