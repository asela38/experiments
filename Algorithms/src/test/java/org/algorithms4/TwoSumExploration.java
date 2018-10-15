package org.algorithms4;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.math.BigInteger;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

public class TwoSumExploration {

	// Prime number above 1M is 1_000_003
	private static class HTable<K, V> {
		private static final int magic_number = 1_000_003;
        @SuppressWarnings("unchecked")
        Node<K, V>[]             table        = new Node[magic_number];

		public void put(K k, V v) {
			int key = Math.abs(k.hashCode()) % magic_number;
			if (table[key] == null) {
				table[key] = Node.of(k, v);
			} else {
				Node<K, V> root = table[key];
				while (root.hasNext())
					root = root.next;
				root.next = Node.of(k, v);
			}
		}

        @SuppressWarnings("unlikely-arg-type")
        public boolean contains(K value) {
			int key = Math.abs(value.hashCode()) % magic_number;
			if (table[key] == null) {
				return false;
			}

			Node<K, V> root = table[key];
			do {
				if (root.value.equals(value)) {
					return true;
				}
				root = root.next;
			} while (root != null);

			return false;

		}

        @SuppressWarnings("unlikely-arg-type")
        public V get(K value) {
			int key = Math.abs(value.hashCode()) % magic_number;
			if (table[key] == null) {
				return null;
			}

			Node<K, V> root = table[key];
			do {
				if (root.value.equals(value)) {
					return root.value;
				}
				root = root.next;
			} while (root != null);

			return null;

		}



		public void printUsage() {
			System.out.printf("[%s->%s]%n", table.length, Arrays.stream(table).filter(Objects::nonNull).count());
		}

		private static class Node<K, V> {
			V value;
            @SuppressWarnings("unused")
            K          key;
			Node<K, V> next;

			public Node(K k, V v) {
				this.value = v;
				this.key = k;
			}

			public static <K, V> Node<K, V> of(K k, V v) {
				return new Node<K, V>(k, v);
			}

			public boolean hasNext() {
				return Objects.nonNull(next);
			}
		}
	}

	@Test
	public void primeNumberOver1Million() throws Exception {
		System.out.println(BigInteger.valueOf(100_000_000L).nextProbablePrime());
	}

	@Test
	public void testHTableTrival() {
		HTable<Integer, Integer> hTable = new HTable<>();
		hTable.put(1, 1);
		hTable.put(2, 1);
		assertTrue(hTable.contains(1));
		assertTrue(hTable.contains(2));
		assertFalse(hTable.contains(3));
		assertFalse(hTable.contains(1_000_004));
		hTable.put(1_000_004, 1_000_004);
		assertTrue(hTable.contains(1_000_004));

	}

	private static final String FILE_PATH_1 = "/Users/asela.illayapparachc/Documents/algorithms/2sum.txt";

	@Test
	public void testNumbersSimple() throws Exception {
		HTable<Long, Long> hTable = new HTable<>();
		List<Long> list = new ArrayList<>(),
				list2 = Stream.of(1L).collect(Collectors.toList());
		for (AtomicInteger x = new AtomicInteger(10_000), y = new AtomicInteger(1); y.get() < 20_000; x
				.set(x.get() / 10), y.set(y.get() * 10)) {

			AtomicInteger counter = new AtomicInteger(0);
			list2.forEach(i -> {
				if (hTable.contains(i / x.get())) {
					list.add(i);
					list.add(hTable.get(i / x.get()));
					counter.incrementAndGet();
				}
				IntStream.range(-1 - y.get(), y.get() + 1)
						.forEach(a -> hTable.put(a - i / x.get(), i));
				hTable.printUsage();
				assertThat(hTable.get(-2L), is(1));
				assertThat(hTable.get(-1L), is(1));
				assertThat(hTable.get(0L), is(1));
				assertThat(hTable.get(1L), is(1));
				assertThat(hTable.get(0L), is(1));
			});
			hTable.printUsage();

			list2.clear();
			list2.addAll(list);
			list.clear();
		}

	}

	@Test
	public void testNumbers() throws Exception {
		HTable<Long, Long> hTable = new HTable<>();
		List<Long> list = new ArrayList<>(),
				// list2 = Files.readAllLines(new
				// File(FILE_PATH_1).toPath()).stream().map(Long::valueOf)
				// .collect(Collectors.toList());
				list2 = Stream.of(1L, 2L).collect(Collectors.toList());
		for (AtomicInteger x = new AtomicInteger(10_000), y = new AtomicInteger(1); y.get() < 20_000; x
				.set(x.get() / 10), y.set(y.get() * 10)) {

			AtomicInteger counter = new AtomicInteger(0);
			list2.forEach(i -> {
				if (hTable.contains(i / x.get())) {
					list.add(i);
					list.add(hTable.get(i / x.get()));
					counter.incrementAndGet();
				}
				IntStream.range(-1 - y.get(), y.get() + 1)
						.forEach(a -> hTable.put(a - i / x.get(), i));
				hTable.printUsage();
				System.out.println(hTable.contains(-2L));
				System.out.println(hTable.contains(-1L));
				System.out.println(hTable.contains(0L));
				System.out.println(hTable.contains(1L));
			});
			hTable.printUsage();
			System.out.println(y);
			System.out.println(list);
			System.out.println(list.size());
			System.out.println(counter);

			list2.clear();
			list2.addAll(list);
			list.clear();
		}
	}

	@Test
	public void testWithHashMap_withList() throws Exception {
		String filePostFix = "50_40000";
		List<Long> list = Files.readAllLines(new File(String.format(FILE_PATH, filePostFix)).toPath()).stream()
				.map(Long::valueOf)
				.collect(Collectors.toList()),
				probables = new ArrayList<>();// , list = Stream.of(1L, 20000L).collect(Collectors.toList());

		Map<Long, List<Long>> map = new HashMap<>();
		for (int m = 10_000; m > 0; m -= 100) {

			int rangeFrom = -10_000 / m - 1;
			int rangeTo = 10_000 / m + 1;
			System.out.printf("(%s) Range (%s,%s)-%s%n", m, rangeFrom, rangeTo, rangeTo - rangeFrom);

			for (Long l : list) {
				if (map.containsKey(l / m)) {
					probables.addAll(map.get(l / m));
					probables.add(l);
				}

				for (int i = rangeFrom; i <= rangeTo; i++) {
					map.putIfAbsent(i - l / m, new ArrayList<>());
					map.get(i - l / m).add(l);
				}
			}

			System.out.println(probables.size());
			map.clear();
			list.clear();
			list.addAll(probables);
			probables.clear();
			if (list.size() < 10)
				System.out.println(list);

			if (m == 500)
				break;
		}

		Set<Long> set = new HashSet<>();
		list.forEach(a -> set.add(a));

		int count = 0;
		for (Long l : set) {
			for (int i = -10_000; i <= 10_000; i++) {
				if (set.contains(i - l)) {
					count++;
				}
			}
		}
		System.out.println(count);
		Files.readAllLines(new File(String.format(FILE_PATH_OUT, filePostFix)).toPath()).forEach(System.out::println);
		// 100390 / 2 = 50195
	}

	private static final String FILE_PATH = "/Users/asela.illayapparachc/git/stanford-algs/testCases/course2/assignment4TwoSum/input_random_%s.txt";
	private static final String FILE_PATH_OUT = "/Users/asela.illayapparachc/git/stanford-algs/testCases/course2/assignment4TwoSum/output_random_%s.txt";

	@Test
	public void testWithHashMap_withSets() throws Exception {
		String filePostFix = "50_40000";
		Set<Long> list = Files.readAllLines(new File(String.format(FILE_PATH, filePostFix)).toPath()).stream()
				.map(Long::valueOf)
				.collect(Collectors.toSet()),
				probables = new HashSet<>();// , list = Stream.of(1L, 20000L).collect(Collectors.toList());

		Map<Long, List<Long>> map = new HashMap<>();
		for (int m = 10_000; m > 0; m -= 100) {

			int rangeFrom = -10_000 / m - 1;
			int rangeTo = 10_000 / m + 1;
			System.out.printf("(%s) Range (%s,%s)-%s%n", m, rangeFrom, rangeTo, rangeTo - rangeFrom);

			for (Long l : list) {
				if (map.containsKey(l / m)) {
					probables.addAll(map.get(l / m));
					probables.add(l);
				}

				for (int i = rangeFrom; i <= rangeTo; i++) {
					map.putIfAbsent(i - l / m, new ArrayList<>());
					map.get(i - l / m).add(l);
				}
			}

			System.out.println(probables.size());
			map.clear();
			list.clear();
			list.addAll(probables);
			probables.clear();
			if (list.size() < 10)
				System.out.println(list);

			if (m == 500)
				break;
		}

		Set<Long> set = new HashSet<>();
		list.forEach(a -> set.add(a));

		int count = 0;
		for (Long l : set) {
			for (int i = -10_000; i <= 10_000; i++) {
				if (set.contains(i - l)) {
					count++;
				}
			}
		}
		System.out.println(count);
		Files.readAllLines(new File(String.format(FILE_PATH_OUT, filePostFix)).toPath()).forEach(System.out::println);
		// 100390 / 2 = 50195
	}

	@Test
	public void testWithHashMap_withSets2_old() throws Exception {
		String filePostFix = "60_160000";
		Set<Long> list = Files.readAllLines(new File(String.format(FILE_PATH, filePostFix)).toPath()).stream()
				.map(Long::valueOf)
				.collect(Collectors.toSet());

		Map<Long, Long> map = list.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		System.out.println(map.size());
		int count = 0;
		Set<Integer> set = IntStream.range(-10_000, 10_001).boxed().collect(Collectors.toSet());
		Set<Integer> set2 = new HashSet<>();
		System.out.println(set.size());
		for (Long l : map.keySet()) {
			long a = map.put(l, 0L);
			for (int i : set) {
				if (map.containsKey(i - l)) {
					// System.out.printf("%s,%s,%s%n", l, i - l, i);
					count += map.get(i - l) * a;
					set2.add(i);
				}
			}
			set.removeAll(set2);
			set2.clear();
		}
		System.out.println("Count " + count);
		System.out.println("i Size " + set.size());
		System.out.println(20_001 - set.size());
		Files.readAllLines(new File(String.format(FILE_PATH_OUT, filePostFix)).toPath()).forEach(System.out::println);
		// 100390 / 2 = 50195
	}

	@Test
	public void testWithHashMap_withSets2() throws Exception {
		String filePostFix = "60_160000";
		// String file_name = String.format(FILE_PATH, filePostFix);
		String file_name = FILE_PATH_1; // String.format(FILE_PATH, filePostFix);
		Set<Long> list = Files.readAllLines(new File(file_name).toPath()).stream()
				.map(Long::valueOf)
				.collect(Collectors.toSet());

		Map<Long, Long> map = list.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		System.out.println(map.size());
		int count = 0;
		HashSet<Object> set = new HashSet<>();
		for (Long l : map.keySet()) {
			long a = map.put(l, 0L);
			for (int i = -10_000; i <= 10_000; i++) {
				if (map.containsKey(i - l)) {
					// System.out.printf("%s,%s,%s%n", l, i - l, i);
					count += map.get(i - l) * a;
					set.add(i);
				}
			}
		}
		System.out.println("Count " + count);
		System.out.println("i Size " + set.size());
		Files.readAllLines(new File(String.format(FILE_PATH_OUT, filePostFix)).toPath()).forEach(System.out::println);
		// 100390 / 2 = 50195
	}

}
