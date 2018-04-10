package org.google.code._2018;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class GoGopher {

	public static void main(String[] args) {
		start();
	}

	private static void start() {
		try (Scanner scanner = new Scanner(System.in)) {
			int t = scanner.nextInt();
			for (int i = 0; i < t; i++) {
				boolean done = false;
				int a = scanner.nextInt();

				int inc = 0;
				while (!done) {

					Set<String> set = new HashSet<>();
					for (int xx = 1; xx < 4; xx++)
						for (int yy = 1 + inc; yy < 4 + inc; yy++)
							set.add(xx + "," + yy);

					while (!set.isEmpty()) {
						System.out.printf("%d %d\n", 2, 2 + inc);

						int x = scanner.nextInt();
						int y = scanner.nextInt();

						set.remove(x + "," + y);

						done = x == 0 && y == 0;
						if (y == -1 || x == -1)
							System.exit(0);
						if (done)
							break;

					}

					inc += 3;
				}
			}
		}

	}

	private static void start_0() {
		try (PrintWriter LOG = new PrintWriter(new PrintStream("go-gopher.log"))) {
			try (Scanner scanner = new Scanner(System.in)) {
				int t = scanner.nextInt();
				LOG.printf("%s Test Cases:  %d%n", LocalDateTime.now(), t);
				for (int i = 0; i < t; i++) {
					boolean done = false;
					int a = scanner.nextInt();
					LOG.printf("A : %d%n", a);
					LOG.flush();

					int inc = 0;
					while (!done) {

						Set<String> set = new HashSet<>();
						for (int xx = 1; xx < 4; xx++)
							for (int yy = 1 + inc; yy < 4 + inc; yy++)
								set.add(xx + "," + yy);

						while (!set.isEmpty()) {
							System.out.printf("%d %d\n", 2, 2 + inc);

							int x = scanner.nextInt();
							int y = scanner.nextInt();

							set.remove(x + "," + y);
							LOG.printf("X: %d, Y: %d %n", x, y);
							LOG.flush();

							done = x == 0 && y == 0;
							if (y == -1 || x == -1)
								System.exit(0);
							if (done)
								break;

						}

						inc += 3;
					}
				}
			}

			LOG.flush();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
