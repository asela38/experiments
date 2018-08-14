package org.algorithms2.week2;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.Test;

public class SSCExploration {

	private static class Node {
		int label;

		public static Node of(int label) {
			Node node = new Node();
			node.label = label;
			return node;
		}

		@Override
		public int hashCode() {
			return Objects.hash(label);
		}

		public int getLabel() {
			return label;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null)
				return false;
			if (this == obj)
				return true;
			if (obj instanceof Node) {
				Node node = (Node) obj;
				return Objects.equals(this.label, node.label);
			}

			return false;
		}

		@Override
		public String toString() {
			return String.format("(%s)", label);
		}
	}

	private class FinishingTime extends HashMap<Node, Integer> {

		private static final long serialVersionUID = 6253362454609719194L;

	}

	private class Leader extends HashMap<Node, Node> {

		private static final long serialVersionUID = -2984488665942375796L;

	}

	private class Explorer extends HashMap<Node, Boolean> {
		private static final long serialVersionUID = 2874632950820856335L;

		public boolean isNotExplored(Node node) {
			return !this.getOrDefault(node, Boolean.FALSE);
		}

		public void markExplored(Node node) {
			this.put(node, Boolean.TRUE);
		}

		public void markUnexplored(Node node) {
			this.put(node, Boolean.TRUE);
		}

		public void reset() {
			keySet().forEach(this::markUnexplored);
		}
	}

	private class Graph extends HashMap<Node, List<Node>> {

		private static final long serialVersionUID = 8971622010121342865L;
		Explorer explorer = new Explorer();
		FinishingTime f = new FinishingTime();
		AtomicInteger t = new AtomicInteger(0);

		Leader l = new Leader();

		private Graph put(int v, int... ws) {
			this.put(Node.of(v), Arrays.stream(ws).mapToObj(Node::of).collect(Collectors.toList()));
			return this;
		}

		private Graph put(Node v, Node w) {
			this.putIfAbsent(v, new ArrayList<>());
			this.get(v).add(w);
			return this;
		}

		private void printEdgeStatistics() {
			System.out.println(this.values().stream().mapToInt(List::size).summaryStatistics());
		}

		public void dfsLoop(Map<Integer, Node> order) {
			// System.out
			// .println(order.keySet().stream().sorted((a, b) -> b.compareTo(a)).map(s ->
			// order.get(s))
			// .map(Objects::toString)
			// .collect(Collectors.joining(",")));
			order.keySet().stream().sorted((a, b) -> b.compareTo(a)).forEach(node -> {
				if (explorer.isNotExplored(order.get(node))) {
					this.dfs(order.get(node), order.get(node));
				}
			});
		}

		public void dfs(Node v, Node lead) {

			l.put(v, lead);
			explorer.markExplored(v);
			getOrDefault(v, Collections.emptyList()).forEach(w -> {
				if (explorer.isNotExplored(w))
					dfs(w, lead);
			});

			f.put(v, t.incrementAndGet());
		}

		private Graph reverse() {
			Graph graph = new Graph();
			this.entrySet().forEach(e -> {
				e.getValue().forEach(w -> {
					graph.put(w, e.getKey());
				});
			});
			return graph;
		}
	}

	/*
	 * (1)-----*(7) ------*(9) ------*(6)-------*(8)* ----- (5) 
	 *   *      /            *       /             \       *    
	 *    \    /              \     /               \     /     
	 *     \  *                \   *                 *   /      
	 *     (4)                  (3)                   (2)        
	 */
	private Graph createGraph() {
		System.out.println("(1)-----*(7) ------*(9) ------*(6)-------*(8)* ----- (5) ");
		System.out.println("  *      /            *       /             \\       *    ");
		System.out.println("   \\    /              \\     /               \\     /     ");
		System.out.println("    \\  *                \\   *                 *   /      ");
		System.out.println("    (4)                  (3)                   (2)       ");
		return new Graph()
				.put(1, 7).put(2, 5).put(3, 9)
				.put(4, 1).put(5, 8).put(6, 8, 3)
				.put(7, 4, 9).put(8, 2).put(9, 6);
	}

	@Test
	public void testSSC() throws Exception {

		Graph graph = createGraph();
		graph.printEdgeStatistics();
		System.out.println(graph);
		System.out.println(graph.reverse());

		Explorer explorer = new Explorer();
		FinishingTime f = new FinishingTime();

		AtomicInteger t = new AtomicInteger(0);

		dfs(graph, Node.of(9), explorer, f, t);
		System.out.println(explorer);

		explorer.reset();

		findScc(graph.reverse());

	}

	private void findScc(Graph graph) {
		graph.dfsLoop(graph.keySet().stream().collect(Collectors.toMap(Node::getLabel, Function.identity())));
		// System.out.println(graph.l);
		// System.out.println(graph.f);
		Graph graph2 = graph.reverse();
		graph2.dfsLoop(graph.f.entrySet().stream().collect(Collectors.toMap(e -> e.getValue(), e -> e.getKey())));
		// System.out.println(graph2.l);
		// System.out.println(graph2.f);

		Map<Node, Integer> map = new HashMap<>();
		for (Map.Entry<Node, Node> entry : graph2.l.entrySet()) {
			map.put(entry.getValue(), map.getOrDefault(entry.getValue(), 0) + 1);
		}

		// System.out.println(map);
		System.out.println(
				map.values().stream().map(Objects::toString).sorted().limit(5).collect(Collectors.joining(",")));

		System.out.println(
				map.values().stream().sorted((a, b) -> b.compareTo(a)).map(Objects::toString).limit(5)
						.collect(Collectors.joining(",")));

	}

	private static final String FILE_PATH_1 = "/Users/asela.illayapparachc/Documents/algorithms/SCC.txt";

	@Test
	public void test() throws Exception {
		Graph graph = new Graph();
		Files.readAllLines(new File(FILE_PATH_1).toPath()).stream().forEach(l -> {
			String[] items = l.split(" ");
			graph.put(Node.of(Integer.parseInt(items[0])), Node.of(Integer.parseInt(items[1])));

		});

		// System.out.println(graph.entrySet().stream().map(e -> e.getKey().label + " "
		// + e.getValue().size())
		// .collect(Collectors.joining(",")));
		findScc(graph);

	}

	@Test
	public void test2() throws Exception {
		findScc(createGraph());
	}

	public void dfsLoop(Graph graph, Explorer explorer, FinishingTime f, AtomicInteger t) {
		graph.keySet().forEach(n -> {
			if (explorer.isNotExplored(n)) {
				dfs(graph, n, explorer, f, t);
			}
		});
	}

	public void dfs(Graph graph, Node v, Explorer explorer, FinishingTime f, AtomicInteger t) {

		if (explorer.isNotExplored(v)) {
			explorer.markExplored(v);
			graph.get(v).forEach(w -> dfs(graph, w, explorer, f, t));
		}

		f.put(v, t.incrementAndGet());
	}

}
