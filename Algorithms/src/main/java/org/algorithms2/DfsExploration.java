package org.algorithms2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

public class DfsExploration {

    /*
     *        (0)---(1)---(3)
     *          \    |    / \
     *           \   |   /   \
     *            \  |  /     \    
     *             \ | /       \ 
     *              (2)--------(4)
     */
    private Graph createSimpleGraph1() {

        System.out.println("(0)---(1)---(3)     ");
        System.out.println("  \\    |    / \\     ");
        System.out.println("   \\   |   /   \\    ");
        System.out.println("    \\  |  /     \\   ");
        System.out.println("     \\ | /       \\  ");
        System.out.println("      (2)--------(4)");

        Graph edges = new Graph();
        edges.put(0, Arrays.asList(1, 2));
        edges.put(1, Arrays.asList(2, 3, 0));
        edges.put(2, Arrays.asList(0, 1, 3, 4));
        edges.put(3, Arrays.asList(1, 2, 4));
        edges.put(4, Arrays.asList(2, 3));
        return edges;
    }

    /*
     *        (0)---(1)---(3)
     *          \         / \
     *           \       /   \
     *            \     /     \    
     *             \   /       \ 
     *              (2)--------(4)
     */
    private Graph createSimpleGraph2() {

        System.out.println("(0)---(1)---(3)     ");
        System.out.println("  \\         / \\     ");
        System.out.println("   \\       /   \\    ");
        System.out.println("    \\     /     \\   ");
        System.out.println("     \\   /       \\  ");
        System.out.println("      (2)--------(4)");

        Graph edges = new Graph();
        edges.put(0, Arrays.asList(1, 2));
        edges.put(1, Arrays.asList(3, 0));
        edges.put(2, Arrays.asList(0, 3, 4));
        edges.put(3, Arrays.asList(1, 2, 4));
        edges.put(4, Arrays.asList(2, 3));
        return edges;
    }
    
    /*
     *        (0)---(1)---(3)
     *          \         / \
     *           \       /   \
     *            \     /     \    
     *             \   /       \ 
     *              (2)--------(4)
     */
    private Graph createSimpleGraph2D() {

        System.out.println("(0)---(1)---(3)     ");
        System.out.println("  \\         / \\     ");
        System.out.println("   \\       /   \\    ");
        System.out.println("    \\     /     \\   ");
        System.out.println("     \\   /       \\  ");
        System.out.println("      (2)--------(4)");

        Graph edges = new Graph();
        edges.put(0, Arrays.asList(1,2));
        edges.put(1, Arrays.asList(3));
        edges.put(2, Arrays.asList(3, 4));
        edges.put(3, Arrays.asList(4));
        edges.put(4, Arrays.asList());
        return edges;
    }


    /*
     *        (0)---(1)---(3)
     *          \           \
     *           \           \
     *            \           \    
     *             \           \ 
     *              (2)--------(4)
     */
    private Graph createSimpleGraph3() {

        System.out.println("(0)---(1)---(3)     ");
        System.out.println("  \\           \\     ");
        System.out.println("   \\           \\    ");
        System.out.println("    \\           \\   ");
        System.out.println("     \\           \\  ");
        System.out.println("      (2)--------(4)");

        Graph edges = new Graph();
        edges.put(0, Arrays.asList(1, 2));
        edges.put(1, Arrays.asList(3, 0));
        edges.put(2, Arrays.asList(0, 4));
        edges.put(3, Arrays.asList(1, 4));
        edges.put(4, Arrays.asList(2, 3));
        return edges;
    }

    @Test
    public void testDfs() throws Exception {
        HashMap<Integer, List<Integer>> graph = createSimpleGraph1();

        boolean[] visited = new boolean[graph.size()];

        Stack<Integer> stack = new Stack<>();
        stack.push(0);

        while (!stack.isEmpty()) {
            Integer v = stack.pop();
            if (!visited[v]) {
                visited[v] = true;
                List<Integer> list = graph.get(v);

                for (Integer w : list) {
                    stack.push(w);
                }
            }

            System.out.println("\n Queue : " + stack);
            System.out.println(" Visited : " + Arrays.toString(visited));
        }

    }

    public class Graph extends HashMap<Integer, List<Integer>> {
    }

    @Test
    public void dfsRecursive() throws Exception {
        Graph graph = createSimpleGraph1();

        boolean[] visited = new boolean[graph.size()];

        dfs(graph, 0, visited);
        System.out.println(String.format("%20s", " ").replace(" ", "--"));
        dfs(graph = createSimpleGraph2(), 0, visited = new boolean[graph.size()]);
        System.out.println("END: " + Arrays.toString(visited));
        System.out.println(String.format("%20s", " ").replace(" ", "--"));
        dfs(graph = createSimpleGraph3(), 0, new boolean[graph.size()]);
        System.out.println("END: " + Arrays.toString(visited));

    }

    public void dfs(Graph graph, Integer v, boolean[] visited) {
        System.out.println(" Visited : " + Arrays.toString(visited));
        visited[v] = true;
        List<Integer> list = graph.get(v);
        for (Integer w : list) {
            if (!visited[w]) {
                dfs(graph, w, visited);
            }
        }
    }

    @Test
    public void testTopologicalOrdering() throws Exception {
        Graph graph = createSimpleGraph2D();

        boolean[] visited = new boolean[graph.size()];
        int[] order = new int[graph.size()];
        AtomicInteger c = new AtomicInteger(graph.size());
        for (int i = 0; i < graph.size(); i++) {
            if(!visited[i]) {
                dfs(graph, i, visited, c, order);
            }
        }
        
        System.out.println(" F Visited : " + Arrays.toString(visited));
        System.out.println(" F Order   : " + Arrays.toString(order));

    }

    public void dfs(Graph graph, Integer v, boolean[] visited, AtomicInteger c, int[] order) {
        System.out.println(" Visited : " + Arrays.toString(visited));
        System.out.println(" Order   : " + Arrays.toString(order));
        visited[v] = true;
        List<Integer> list = graph.get(v);
        for (Integer w : list) {
            if (!visited[w]) {
                dfs(graph, w, visited, c, order);
            }
        }
        order[v] = c.getAndDecrement();
    }

}
