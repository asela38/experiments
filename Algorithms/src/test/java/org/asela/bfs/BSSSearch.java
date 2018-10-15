package org.asela.bfs;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class BSSSearch {

	// Complete the bfs function below.
	static int[] bfs(int n, int m, int[][] edges, int s) {
		Map<Integer, Set<Integer>> adj = new HashMap<>();
		for (int[] edge : edges) {
			adj.putIfAbsent(edge[0], new HashSet<>());
			adj.putIfAbsent(edge[1], new HashSet<>());
			adj.get(edge[1]).add(edge[0]);
			adj.get(edge[0]).add(edge[1]);
		}

		boolean[] explored = new boolean[n];
		int[] distance = new int[n];
		Arrays.fill(distance, -1);

		Queue<Integer> queue = new LinkedList<>();
		queue.offer(s);
		distance[s - 1] = 0;
		explored[s - 1] = true;
		while (!queue.isEmpty()) {
			int v = queue.poll();
			for (int w : adj.getOrDefault(v, Collections.emptySet())) {
				if (!explored[w - 1]) {
					queue.offer(w);
					explored[w - 1] = true;
					distance[w - 1] = distance[v - 1] + 6;
				}
			}
		}

		int[] distance2 = new int[n - 1];
		int j = 0;
		for (int i : distance)
			if (i != 0)
				distance2[j++] = i;
		return distance2;
	}

	private static String file = "/Users/asela.illayapparachc/Documents/algorithms/hacker-rank-bfs1.txt";

	private static Scanner scanner;
	static {
		try {
			scanner = new Scanner(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

		int q = scanner.nextInt();
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		for (int qItr = 0; qItr < q; qItr++) {
			String[] nm = scanner.nextLine().split(" ");

			int n = Integer.parseInt(nm[0]);

			int m = Integer.parseInt(nm[1]);

			int[][] edges = new int[m][2];

			for (int i = 0; i < m; i++) {
				String[] edgesRowItems = scanner.nextLine().split(" ");
				scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

				for (int j = 0; j < 2; j++) {
					int edgesItem = Integer.parseInt(edgesRowItems[j]);
					edges[i][j] = edgesItem;
				}
			}

			int s = scanner.nextInt();
			scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

			int[] result = bfs(n, m, edges, s);

			for (int i = 0; i < result.length; i++) {
				bufferedWriter.write(String.valueOf(result[i]));

				if (i != result.length - 1) {
					bufferedWriter.write(" ");
				}
			}

			bufferedWriter.newLine();
		}

		bufferedWriter.close();

		scanner.close();
	}
}
