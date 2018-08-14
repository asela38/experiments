package org.asela.bfs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.google.common.base.Stopwatch;

public class SolutionDijkstra {

	// Complete the shortestReach function below.
	static int[] shortestReach(int n, Map<Integer, Map<Integer, Integer>> adj, int s) {
		System.out.printf("%s - %s%n", "Shortest Reach - Begins", stopwatch.elapsed(TimeUnit.MILLISECONDS));
		boolean[] visited = new boolean[n];
		int[] distance = new int[n];
		Arrays.fill(distance, Integer.MAX_VALUE);

		PriorityQueue<Integer> heap = new PriorityQueue<>((a, b) -> Integer.compare(
				distance[a - 1],
				distance[b - 1]));
		heap.offer(s);
		distance[s - 1] = 0;

		while (!heap.isEmpty()) {
			int v = heap.poll();
			if (visited[v - 1])
				continue;
			visited[v - 1] = true;
			for (int w : adj.getOrDefault(v, Collections.emptyMap()).keySet()) {
				distance[w - 1] = Math.min(distance[w - 1],
						distance[v - 1] + adj.get(v).get(w));
				heap.offer(w);
			}

		}

		int[] distance2 = new int[n - 1];
		int j = 0;
		for (int i : distance) {
			if (i == 0)
				continue;
			distance2[j++] = i != Integer.MAX_VALUE ? i : -1;

		}

		System.out.printf("%s - %s%n", "Shortest Reach - Begins", stopwatch.elapsed(TimeUnit.MILLISECONDS));
		return distance2;
	}

	private static String file = "/Users/asela.illayapparachc/Documents/algorithms/hacker-rank-dijkstra3.txt";

	private static Stopwatch stopwatch = Stopwatch.createStarted();

	public static void main(String[] args) throws IOException {
		System.out.printf("%s - %s%n", "Main Method - Begins", stopwatch.elapsed(TimeUnit.MILLISECONDS));
		BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

		int index = 0;
		List<String> list = new BufferedReader(new FileReader(file)).lines().collect(Collectors.toList());
		// List<String> list = new BufferedReader(new
		// InputStreamReader(System.in)).lines().collect(Collectors.toList());
		int t = Integer.parseInt(list.get(index++));

		for (int tItr = 0; tItr < t; tItr++) {
			String[] nm = list.get(index++).split(" ");
			int n = Integer.parseInt(nm[0]);

			int m = Integer.parseInt(nm[1]);

			System.out.printf("%s - %s%n", "Start Create Adjecency List", stopwatch.elapsed(TimeUnit.MILLISECONDS));
			Map<Integer, Map<Integer, Integer>> adj = new HashMap<>(n, 2f);
			for (int i = 1; i <= n; i++)
				adj.put(i, new HashMap<>(m / n, 2f));

			for (int i = 0; i < m; i++) {
				String[] split = list.get(index++).split(" ");

				int v = Integer.parseInt(split[0]);
				int w = Integer.parseInt(split[1]);
				int d = Integer.parseInt(split[2]);

				if (adj.get(v).containsKey(w)) {
					d = Math.min(d, adj.get(v).get(w));
				}
				adj.get(v).put(w, d);
				adj.get(w).put(v, d);

			}

			System.out.printf("%s - %s%n", "Finish Creating Adjecency List", stopwatch.elapsed(TimeUnit.MILLISECONDS));

			int s = Integer.parseInt(list.get(index++));

			int[] result = shortestReach(n, adj, s);

			for (int i = 0; i < result.length; i++) {
				bufferedWriter.write(String.valueOf(result[i]));

				if (i != result.length - 1) {
					bufferedWriter.write(" ");
				}
			}

			bufferedWriter.newLine();
		}

		bufferedWriter.close();

		System.out.printf("%n%s - %s%n", "Main Method - Ends", stopwatch.elapsed(TimeUnit.MILLISECONDS));
	}
}
