package org.asela.dfs;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class DfsExploration {

	// Complete the roadsAndLibraries function below.
	static long roadsAndLibraries(int n, int c_lib, int c_road, int[][] cities) {
		Map<Integer, Set<Integer>> adj = new HashMap<>();
		for (int[] edge : cities) {
			adj.putIfAbsent(edge[0], new HashSet<>());
			adj.putIfAbsent(edge[1], new HashSet<>());
			adj.get(edge[1]).add(edge[0]);
			adj.get(edge[0]).add(edge[1]);
		}

		boolean[] visited = new boolean[n];
		int[] root = new int[n];
		for (int i = 0; i < n; root[i] = i++)
			;

		for (int i = 1; i <= n; i++) {
			if (!visited[i - 1]) {
				visited[i - 1] = true;
				dfs(adj, i, i, visited, root);
			}
		}

		// System.out.println(Arrays.toString(root));
		long cc = Arrays.stream(root).distinct().count();
		// System.out.println("LIB ONLY " + 1L * n * c_lib);
		// System.out.println("OTHER " + Math.max(0L, cc * c_lib + (n - cc) * c_road));
		return Math.min(1L * n * c_lib, Math.max(0L, cc * c_lib + (n - cc) * c_road));
	}

	static void dfs(Map<Integer, Set<Integer>> adj, int v, int s, boolean[] visited, int[] root) {
		for (int w : adj.getOrDefault(v, Collections.emptySet())) {
			if (!visited[w - 1]) {
				root[w - 1] = s - 1;
				visited[w - 1] = true;
				dfs(adj, w, s, visited, root);
			}
		}
	}

	private static String file = "/Users/asela.illayapparachc/Documents/algorithms/hacker_rank_lib_1.txt";

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
			String[] nmC_libC_road = scanner.nextLine().split(" ");

			int n = Integer.parseInt(nmC_libC_road[0]);

			int m = Integer.parseInt(nmC_libC_road[1]);

			int c_lib = Integer.parseInt(nmC_libC_road[2]);

			int c_road = Integer.parseInt(nmC_libC_road[3]);

			int[][] cities = new int[m][2];

			for (int i = 0; i < m; i++) {
				String[] citiesRowItems = scanner.nextLine().split(" ");
				scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

				for (int j = 0; j < 2; j++) {
					int citiesItem = Integer.parseInt(citiesRowItems[j]);
					cities[i][j] = citiesItem;
				}
			}

			long result = roadsAndLibraries(n, c_lib, c_road, cities);

			bufferedWriter.write(String.valueOf(result));
			bufferedWriter.newLine();
		}

		bufferedWriter.close();

		scanner.close();
	}
}
