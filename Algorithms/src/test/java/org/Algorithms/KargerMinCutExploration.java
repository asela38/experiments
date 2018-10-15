package org.Algorithms;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;

public class KargerMinCutExploration {

	private static final String FILE_PATH_1 = "/Users/asela.illayapparachc/Documents/algorithms/kargerMinCut.txt";

	@Test
	public void testRepresentation() throws Exception {
		List<String> lines = Files.readAllLines(new File(FILE_PATH_1).toPath());
		Map<Integer, List<Integer>> map = lines.stream().map(s -> s.split("\\s"))
				.collect(Collectors.toMap(a -> Integer.valueOf(a[0]),
						a -> Arrays.stream(a).skip(1).map(Integer::valueOf).collect(Collectors.toList())));
		System.out.println(map);

		int n = map.keySet().size();
		System.out.println("Number of Nodes :  " + n);

		Set<String> vertextSet2 = map.entrySet().stream()
				.flatMap(e -> e.getValue().stream().map(a -> new int[] { e.getKey(), a.intValue() }))
				.map(a -> Integer.max(a[0], a[1]) + "-" + Integer.min(a[0], a[1])).distinct()
				.collect(Collectors.toSet());

		System.out.println(IntStream.range(0, 400).map(ii -> {
			List<int[]> vertextSet = vertextSet2.stream().map(a -> a.split("-"))
					.map(a -> new int[] { Integer.parseInt(a[0]), Integer.parseInt(a[1]) })
					.collect(Collectors.toList());

			// System.out.println(vertextSet.size());
			vertextSet.stream().filter(a -> a[0] == a[1]).map(Arrays::toString).forEach(System.out::println);
			vertextSet = vertextSet.stream().filter(a -> a[0] != a[1]).collect(Collectors.toList());
			// System.out.println(vertextSet.size());

			for (int i = 0; i < n - 2; i++) {
				int[] first = vertextSet.remove(ThreadLocalRandom.current().nextInt(0, vertextSet.size()));
				vertextSet = vertextSet.stream().map(a -> {
					if (a[0] == first[0]) {
						a[0] = first[1];
					}

					if (a[1] == first[0]) {
						a[1] = first[1];
					}
					return a;
				}).collect(Collectors.toList());

				// System.out.println(vertextSet.size());
				// vertextSet.stream().filter(a -> a[0] ==
				// a[1]).map(Arrays::toString).forEach(System.out::println);
				vertextSet = vertextSet.stream().filter(a -> a[0] != a[1]).collect(Collectors.toList());
			}

			// System.out.println(vertextSet.size());
			//

			int size = vertextSet.size();
			if (size < 18) {
				vertextSet.stream().map(Arrays::toString).map(s -> s + " ").forEach(System.out::print);
			}
			System.out.println(ii + " -> " + size);
			return size;
		}).summaryStatistics());
	}

    @SuppressWarnings("unused")
    private void firstAttempt_FailedSinceExponentialGrowth(Map<Integer, List<Integer>> map, int n) {
		Set<Integer> rSet = new HashSet<>();

		for (int i = 1; i <= n - 2; i++) {
			System.out.println(i);
			List<Integer> list = map.remove(i);
			rSet.add(i);
			list.removeAll(rSet);
			System.out.println(i + " -> " + list.size());
			for (Integer j : list) {
				map.get(j).addAll(list);
				map.get(j).remove(j);
				map.get(j).remove(Integer.valueOf(i));
			}

		}

		System.out.println(map);
		System.out.println(map.size());
	}

}
