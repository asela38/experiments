package org.algorithms2.week2;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

public class DijkstraExploration {

	private class WeightedGraph extends HashMap<Integer, HashMap<Integer, Integer>> {
		private static final long serialVersionUID = -6781915081739380866L;

		private Map<Integer, Boolean> processed = new HashMap<>();
		private Map<Integer, Integer> shortestPath = new HashMap<>();

		public void put(int v, int w, int l) {
			putIfAbsent(v, new HashMap<>());
			get(v).put(w, l);
		}

		public boolean isNotProcessed(int v) {
			return !processed.getOrDefault(v, Boolean.FALSE);
		}

		public void markProcessed(int v) {
			processed.put(v, Boolean.TRUE);
		}

        @SuppressWarnings("unused")
        public void setShortestPath(int v, int distance) {
			shortestPath.put(v, distance);
		}
	}

	/*
	 *           <1>           <6>
	 *   (1)------------*(2)---------*(4)
	 *     \             /            *
	 *       \ <4>      /<2>         /
	 *         \       /            /
	 *           \    /            /
	 *            *  *       <3>  /
	 *             (3)-----------+
	 *   
	 */
	private WeightedGraph createWeightedGraph() {

		System.out.println("         <1>           <6>           ");
		System.out.println(" (1)------------*(2)---------*(4)    ");
		System.out.println("   \\             /            *      ");
		System.out.println("     \\ <4>      /<2>         /       ");
		System.out.println("       \\       /            /        ");
		System.out.println("         \\    /            /         ");
		System.out.println("          *  *       <3>  /          ");
		System.out.println("           (3)-----------+           ");

		WeightedGraph graph = new WeightedGraph();
		graph.put(1, 2, 1);
		graph.put(2, 4, 6);
		graph.put(1, 3, 4);
		graph.put(2, 3, 2);
		graph.put(3, 4, 5);
		return graph;
	}

	private static class DistanceEdge {
		Integer v, w, d;

		public static DistanceEdge of(Integer v, Integer w, Integer d) {
			DistanceEdge edge = new DistanceEdge();
			edge.v = v;
			edge.w = w;
			edge.d = d;
			return edge;
		}

		@Override
		public String toString() {
			return String.format("(d=%s,v=%s,w=%s)", d, v, w);
		}
	}

	@Test
	public void testDijkstra() throws Exception {

		WeightedGraph graph = createWeightedGraph();

		findShortestPaths(graph, 1);

		System.out.println(graph.shortestPath);
	}

	private void findShortestPaths(WeightedGraph graph, int v) {
		PriorityQueue<DistanceEdge> priorityQueue = new PriorityQueue<>(
				(a, b) -> Integer.compare(
						a.d + graph.shortestPath.getOrDefault(a.v, 2 << 20),
						b.d + graph.shortestPath.getOrDefault(b.v, 2 << 20)));

		graph.markProcessed(v);
		graph.get(v).entrySet().forEach(e -> priorityQueue.offer(DistanceEdge.of(v, e.getKey(), e.getValue())));
		graph.shortestPath.put(v, 0);

		while (!priorityQueue.isEmpty()) {

			DistanceEdge edge = priorityQueue.poll();

			graph.shortestPath.put(edge.w, Math.min(graph.shortestPath.getOrDefault(edge.w, 1000000),
					graph.shortestPath.get(edge.v) + edge.d));

			if (graph.isNotProcessed(edge.w)) {
				graph.markProcessed(edge.w);
				graph.getOrDefault(edge.w, new HashMap<>()).entrySet()
						.forEach(e -> priorityQueue.offer(DistanceEdge.of(edge.w, e.getKey(), e.getValue())));
			}
		}
	}

	private static final String FILE_PATH_1 = "/Users/asela.illayapparachc/Documents/algorithms/dijkstraData2.txt";

	@Test
	public void test() throws Exception {
		WeightedGraph graph = new WeightedGraph();
		Files.readAllLines(new File(FILE_PATH_1).toPath()).stream().forEach(l -> {
			String[] items = l.split("\\s+");
			Arrays.stream(items).skip(1).forEach(item -> {
				String[] parts = item.split(",");
				graph.put(Integer.parseInt(items[0]), Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
			});
		});

		System.out.println(graph.size());
		findShortestPaths(graph, 1);

		System.out.println(graph.processed.values().stream().filter(a -> a == true).count());
		System.out.println(graph.shortestPath);
		System.out.println(Stream.of(7, 37, 59, 82, 99, 115, 133, 165, 188, 197).map(graph.shortestPath::get)
				.map(Objects::toString).collect(Collectors.joining(",")));

		System.out.println(Stream.of(7, 37, 59, 82, 99, 115, 133, 165, 188, 197).map(graph.shortestPath::get)
				.map(Objects::toString).collect(Collectors.joining(",")));

		// 4685,2610,3754,2052,2367,2399,2029,4399,2633,4483
		// 7 , 37 , 59 , 82, 99,
		System.out.println(graph);
	}

	private static final String FILE_PATH_2 = "/Users/asela.illayapparachc/Documents/algorithms/dijkstraData-Simple.txt";

	@Test
	public void test_simple() throws Exception {
		WeightedGraph graph = new WeightedGraph();
		Files.readAllLines(new File(FILE_PATH_2).toPath()).stream().forEach(l -> {
			String[] items = l.split("\\s+");
			Arrays.stream(items).skip(1).forEach(item -> {
				String[] parts = item.split(",");
				graph.put(Integer.parseInt(items[0]), Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
			});
		});

		System.out.println(graph.size());
		findShortestPaths(graph, 1);

		System.out.println(graph.processed.values().stream().filter(a -> a == true).count());
		System.out.println(graph.shortestPath);

		// 4685,2610,3754,2052,2367,2399,2029,4399,2633,4483
		// 7 , 37 , 59 , 82, 99,
		System.out.println(graph);
	}
}
