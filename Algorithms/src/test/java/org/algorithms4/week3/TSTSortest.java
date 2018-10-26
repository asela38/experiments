package org.algorithms4.week3;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;

public class TSTSortest {

	private static final String TEST_FILE_LOCATION = "/Users/asela.illayapparachc/git/stanford-algs/testCases/course4/assignment2TSP/";
	private static final String INPUT_FILE = "input_float_";
	private static final String _1_2_FILE = TEST_FILE_LOCATION + INPUT_FILE + "1_2.txt";
	private static final String _5_3_FILE = TEST_FILE_LOCATION + INPUT_FILE + "5_3.txt";
	private static final String _19_6_FILE = TEST_FILE_LOCATION + INPUT_FILE + "19_6.txt";
	private static final String _26_8_FILE = TEST_FILE_LOCATION + INPUT_FILE + "26_8.txt";
	private static final String _32_256_FILE = TEST_FILE_LOCATION + INPUT_FILE + "32_256.txt";
	private static final String _38_1024_FILE = TEST_FILE_LOCATION + INPUT_FILE + "38_1024.txt";
	@SuppressWarnings("unused")
	private static final String JOB_FILE1 = "/Users/asela.illayapparachc/Desktop/code/tsp.txt";

	@Test
	public void testFile() throws Exception {
		try (BufferedReader reader = new BufferedReader(new FileReader(_1_2_FILE))) {
			reader.lines().forEach(System.out::println);
		}
	}

	@Test
	public void job() throws Exception {

		System.out.println(process(JOB_FILE1)); // -1947

	}

	@Test
	public void trivial() throws Exception {
		System.out.println(process(_26_8_FILE)); // 2
	}

    private String process(String file) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new File(file))) {
            int N = scanner.nextInt();
            double[][] vertices = new double[N][2];
            for (int i = 0; i < N; i++) {
                vertices[i] = new double[] { scanner.nextDouble(), scanner.nextDouble(), 0 };
            }

            Map<String, double[]> map = new HashMap<>(1 << 22);
            double[] arr = new double[N];
            Arrays.fill(arr, Double.MAX_VALUE);
            arr[0] = 0;
            map.put(Arrays.toString(new int[] {}), arr);

            // if (N < 24)
            // return "0";
            Map<Integer, Set<String>> subSetMap = getSubSetMap(N - 1);
            Queue<Integer> queue = new LinkedList<>();
            Map<Integer, Set<String>> mapSet = new HashMap<>();
            for (int m = 2; m <= N; m++) {
                mapSet.put(m, new HashSet<>());
                if (mapSet.containsKey(m - 2)) {
                    mapSet.get(m - 2).forEach(key -> map.remove(key));
                }
                // System.out.println(
                // m + "=" + choose(N, m - 1) + " / " + map.size() + " / " + choose(N - 1, m -
                // 1));


                for (String pattern : subSetMap.get(m - 1)) {

                    queue.clear();
                    IntStream.range(1, 26).forEach(queue::add);
                    int[] S = pattern.chars().map(i -> i - '0').map(i -> i * queue.poll()).filter(i -> i != 0)
                            .toArray();

                    // System.out.println(Arrays.toString(T));

                    if (!mapSet.get(m).add(Arrays.toString(S))) {
                        // zz--;
                        continue;
                    }
                    // System.out.printf("%5s=%s%n", zz + 1, Arrays.toString(T));
                    // System.out.println(Arrays.toString(S));
                    for (int j : S) {
                        String key = Arrays.toString(Arrays.stream(S).filter(s -> s != j).toArray());
                        double[] x = map.get(key);
                        if (x == null) {
                            x = new double[N];
                            Arrays.fill(x, Double.MAX_VALUE);
                            map.put(Arrays.toString(S), x);
                        }

                        double min = x[0] + distance(vertices[j], vertices[0]);
                        for (int k : S) {
                            if (k != j) {
                                min = Double.min(min, x[k] + distance(vertices[j], vertices[k]));
                            }
                        }

                        double[] is = map.get(Arrays.toString(S));
                        if (is == null) {
                            is = new double[N];
                            Arrays.fill(is, Double.MAX_VALUE);
                            map.put(Arrays.toString(S), is);
                        }

                        is[j] = min;
                    }
                }
            }

            int[] S = IntStream.iterate(1, i -> i + 1).limit(N - 1).sorted().toArray();
            double[] x = map.get(Arrays.toString(Arrays.stream(S).toArray()));
            double min = Double.MAX_VALUE;
            for (int k : S) {
                min = Double.min(min, x[k] + distance(vertices[0], vertices[k]));
            }

            return Long.toString((long) min);
        }
    }

    @Test
    public void testString() throws Exception {

        String s = "1110011001100111";
        Queue<Integer> queue = new LinkedList<>();
        IntStream.range(1, 25).forEach(queue::add);
        Set<Integer> collect = s.chars().map(i -> i - '0').map(i -> i * queue.poll()).filter(i -> i != 0).boxed()
                .collect(Collectors.toSet());
        System.out.println(collect);
    }

    private Map<Integer, Set<String>> getSubSetMap(int pow) {
        Map<Integer, Set<String>> map = new HashMap<>();
        for (int i = 0; i < Math.pow(2, pow); i++) {
            String bs = Integer.toBinaryString(i);
            Integer bitCount = Integer.bitCount(i);
            if (!map.containsKey(bitCount)) {
                map.put(bitCount, new HashSet<>());
            }

            map.get(bitCount).add(String.format("%" + pow + "s", bs).replace(" ", "0"));
        }
        return map;
    }

    @SuppressWarnings("unused")
    private String process0(String file) throws FileNotFoundException {
		try (Scanner scanner = new Scanner(new File(file))) {
			int N = scanner.nextInt();
			double[][] vertices = new double[N][2];
			for (int i = 0; i < N; i++) {
				vertices[i] = new double[] { scanner.nextDouble(), scanner.nextDouble(), 0 };
			}

			Map<String, double[]> map = new HashMap<>(1 << 22);
			double[] arr = new double[N];
			Arrays.fill(arr, Double.MAX_VALUE);
			arr[0] = 0;
			map.put(Arrays.toString(new int[] {}), arr);
			Random random = new Random(1L);
			// if (N < 24)
			// return "0";
			Map<Integer, Set<String>> mapSet = new HashMap<>();
			for (int m = 2; m <= N; m++) {
				mapSet.put(m, new HashSet<>());
				if (mapSet.containsKey(m - 2)) {
					// mapSet.get(m - 2).forEach(key -> map.remove(key));
				}
				System.out.println(
						m + "=" + choose(N, m - 1) + " / " + map.size() + " / " + choose(N - 1, m -
								1));

				int[] T = new int[N - 1];
				boolean xxx = false;
				for (int i = 0; i < T.length; i++) {
					if (i < m - 1)
						T[i] = 1;
				}
				AtomicInteger shift = new AtomicInteger(0);
				for (int zz = 0; zz < choose(N - 1, m - 1); zz++) {

					// int[] S = getS(m, T, xxx, shift);
					xxx = true;
					int[] S = random.ints(1, N).distinct().limit(m - 1).sorted().toArray();

					// System.out.println(Arrays.toString(T));

					if (!mapSet.get(m).add(Arrays.toString(S))) {
						// zz--;
						continue;
					}
					// System.out.printf("%5s=%s%n", zz + 1, Arrays.toString(T));
					// System.out.println(Arrays.toString(S));
					for (int j : S) {
						String key = Arrays.toString(Arrays.stream(S).filter(s -> s != j).toArray());
						double[] x = map.get(key);
						if (x == null) {
							x = new double[N];
							Arrays.fill(x, Double.MAX_VALUE);
							map.put(Arrays.toString(S), x);
						}

						double min = x[0] + distance(vertices[j], vertices[0]);
						for (int k : S) {
							if (k != j) {
								min = Double.min(min, x[k] + distance(vertices[j], vertices[k]));
							}
						}

						double[] is = map.get(Arrays.toString(S));
						if (is == null) {
							is = new double[N];
							Arrays.fill(is, Double.MAX_VALUE);
							map.put(Arrays.toString(S), is);
						}

						is[j] = min;
					}
				}
			}

			int[] S = IntStream.iterate(1, i -> i + 1).limit(N - 1).sorted().toArray();
			double[] x = map.get(Arrays.toString(Arrays.stream(S).toArray()));
			double min = Double.MAX_VALUE;
			for (int k : S) {
				min = Double.min(min, x[k] + distance(vertices[0], vertices[k]));
			}

			return Long.toString((long) min);
		}
	}

	private int[] getS(int m, int[] T, boolean xxx, AtomicInteger shift) {
		if (xxx) {
			boolean connered = true;
			for (int i = T.length - 1; i > 0; i--) {
				if (T[i] == 0 && T[i - 1] == 1) {
					T[i] = 1;
					T[i - 1] = 0;
					connered = false;
					break;
				}
			}

			if (connered) {
				int sum = Arrays.stream(T).sum();
				Arrays.fill(T, 0);
				int ii = shift.incrementAndGet();
				if (ii + sum < T.length) {
					for (int i = ii; i < ii + sum; i++) {
						T[i] = 1;
					}
				}
			}

		}

		// System.out.println("*" + Arrays.toString(T));

		int[] S = new int[m - 1];

		for (int i = 0, j = 0; i < T.length; i++) {
			if (T[i] == 1) {
				S[j++] = i + 1;
			}
		}
		return S;
	}

	@Test
	public void testS() throws Exception {
		int[] T = new int[] { 1, 1, 0, 0, 0, 0, 0, 0 };
		boolean x = false;
		System.out.println("==" + choose(T.length, Arrays.stream(T).sum()));
		Set<String> set = new TreeSet<>();
		AtomicInteger shift = new AtomicInteger(0);
		for (int i = 0; i < Math.pow(2, T.length); i++) {
			int[] S = getS(T.length, T, x, shift);
			System.out.printf("%5s = %s%n", i + 1, Arrays.toString(T));
			set.add(Arrays.stream(T).boxed().map(Objects::toString).collect(Collectors.joining()));
			x = true;
		}

		set.forEach(System.out::println);
	}

	@SuppressWarnings("unused")
	private String process_0(String file) throws FileNotFoundException {
		try (Scanner scanner = new Scanner(new File(file))) {
			int N = scanner.nextInt();
			double[][] vertices = new double[N][2];
			for (int i = 0; i < N; i++) {
				vertices[i] = new double[] { scanner.nextDouble(), scanner.nextDouble(), 0 };
			}

			double distance = 0, distance2 = Double.MAX_VALUE;

			Random random = new Random(1L);
			for (int k = 0; k < Math.pow(1000, Math.log(N)); k++) {

				List<double[]> set = Arrays.stream(vertices).collect(Collectors.toList());

				Collections.shuffle(set, random);

				double[] u = set.stream().findFirst().get(), u0 = u, v;
				while (set.size() > 1) {
					set.remove(u);
					double[] uu = u;
					v = set.stream().filter(a -> a != uu).findFirst().get();
					// for (double[] w : set) {
					// if (distance(u, w) < distance(u, v)) {
					// v = w;
					// }
					// }
					distance += distance(u, v);
					u = v;
				}

				distance += distance(u, u0);

				distance2 = Double.min(distance, distance2);
				// System.out.println(distance2);
				distance = 0;
			}
			return Long.toString((long) distance2);
		}
	}

	private double distance(double[] a, double[] b) {
		// System.out.println(Math.sqrt(Math.pow(a[0] - b[0], 2) + Math.pow(a[1] - b[1],
		// 2)));
		return Math.sqrt(Math.pow(a[0] - b[0], 2) + Math.pow(a[1] - b[1], 2));

	}

	private int distanceTruncate(double[] a, double[] b) {
		// System.out.println(Math.sqrt(Math.pow(a[0] - b[0], 2) + Math.pow(a[1] - b[1],
		// 2)));
		return (int) Math.round(Math.sqrt(Math.pow(a[0] - b[0], 2) + Math.pow(a[1] - b[1], 2)));

	}

	@Test
	public void testForAll() throws Exception {

		Files.walk(Paths.get(TEST_FILE_LOCATION)).sequential().map(p -> p.toFile())
				.filter(f -> f.getName().contains(INPUT_FILE))
				.sorted((a, b) -> Long.compare(a.length(), b.length())).forEach(f -> {
					try {
						String outputFile = f.getAbsolutePath().replace("input", "output");
						String output = "";
						try (BufferedReader reader = new BufferedReader(new FileReader(outputFile))) {
							output = reader.readLine();
						} catch (IOException e) {
							e.printStackTrace();
						}
						String result = null;
						System.out.printf("Expected : %10s Calculated: %10s %6s File: %s %n", output,
								result = process(f.getAbsolutePath()), Objects.equals(result, output), f.getName());
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				});
	}

	@Test
	public void testLog() throws Exception {
		for (int i = 0; i < 100; i++)
			System.out.println(i + "=" + Math.pow(10, Math.log10(i)));
	}

	@Test
	public void testRandom() throws Exception {
		Random random = new Random(1L);
		for (int i = 0; i < 10; i++)
			System.out.println(random.nextInt(10));
	}

	@Test
	public void arrayToString() throws Exception {
		System.out.println(Arrays.toString(new int[] {}));
		System.out.println(Arrays.toString(new int[] { 1 }));
		System.out.println(Arrays.toString(new int[] { 1, 2 }));

	}

	@Test
	public void factorial() throws Exception {

		long l = 1;
		for (int i = 1; i < 100; i++) {
			System.out.println(i + " = " + (l *= i));
		}
	}

	@Test
	public void chooseTest() throws Exception {
		for (int i = 1; i < 25; i++)
			assertThat(choose(i, 1), is(i * 1L));

		assertThat(choose(25, 12), is(5200300L));
		assertThat(choose(25, 10), is(choose(25, 15)));
	}

	@Test
	public void choose25() throws Exception {

		for (int i = 1; i <= 22; i++) {
			System.out.println(choose(25, i));

		}
	}

	@Test
	public void testX() throws Exception {

		Map<Integer, Set<String>> map = new HashMap<>();
		for (int i = 0; i < Math.pow(2, 24); i++) {
			String bs = Integer.toBinaryString(i);
			Integer bitCount = Integer.bitCount(i);
			if (!map.containsKey(bitCount)) {
				map.put(bitCount, new HashSet<>());
			}

			map.get(bitCount).add(bs);
		}

		for (Map.Entry<Integer, Set<String>> entry : map.entrySet()) {
			System.out.printf("%3s = %s%n", entry.getKey(), entry.getValue().size());
		}
	}

	private long choose(int n, int k) {
		int max = Integer.max(n - k, k);
		int min = Integer.min(n - k, k);
		long ans = 1L;
		for (int i = n; i > max; i--)
			ans *= i;
		for (int i = 1; i <= min; i++)
			ans /= i;
		return ans;
	}
}
