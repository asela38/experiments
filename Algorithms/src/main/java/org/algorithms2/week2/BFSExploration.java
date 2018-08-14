package org.algorithms2.week2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.junit.Test;

public class BFSExploration {

    /*
     *        (0)---(1)---(3)
     *          \    |    / \
     *           \   |   /   \
     *            \  |  /     \    
     *             \ | /       \ 
     *              (2)--------(4)
     */
    private HashMap<Integer, List<Integer>> createSimpleGraph1() {
        
        System.out.println("(0)---(1)---(3)     ");
        System.out.println("  \\    |    / \\     ");
        System.out.println("   \\   |   /   \\    ");
        System.out.println("    \\  |  /     \\   ");
        System.out.println("     \\ | /       \\  ");
        System.out.println("      (2)--------(4)");
        
        HashMap<Integer, List<Integer>> edges = new HashMap<>();
        edges.put(0, Arrays.asList(1, 2));
        edges.put(1, Arrays.asList(2, 3, 0));
        edges.put(2, Arrays.asList(0, 1, 3, 4));
        edges.put(3, Arrays.asList(1, 2, 4));
        edges.put(4, Arrays.asList(2, 3));
        return edges;
    }

    @Test
    public void bfsTest() throws Exception {

        boolean[] visited = new boolean[5];

        HashMap<Integer, List<Integer>> edges = createSimpleGraph1();

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        visited[0] = true;
        while (!queue.isEmpty()) {
            Integer v = queue.poll();
            List<Integer> list = edges.get(v);
            for (Integer w : list)
                if (!visited[w]) {
                    visited[w] = true;
                    queue.offer(w);
                }
            System.out.println("\n Queue : " + queue);
            System.out.println(" Visited : " + Arrays.toString(visited));
        }

    }

    @Test
    public void bfsShortestPathFrom() throws Exception {
        HashMap<Integer, List<Integer>> edges = createSimpleGraph1();

        findShortestPath(edges, 0, 4);
        findShortestPath(edges, 1, 4);
        findShortestPath(edges, 1, 2);
        findShortestPath(edges, 2, 1);

    }

    private int findShortestPath(HashMap<Integer, List<Integer>> edges, int from, int to) {
        int[] level = new int[5];
        Arrays.fill(level, -1);

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(from);
        int currentLevel = 0;
        level[from] = currentLevel;
        while (!queue.isEmpty()) {
            Integer v = queue.poll();
            currentLevel = level[v];
            List<Integer> list = edges.get(v);
            for (Integer w : list)
                if (level[w] == -1) {
                    level[w] = currentLevel + 1;
                    queue.offer(w);
                }
            System.out.println("\n Queue : " + queue);
            System.out.println(" Visited : " + Arrays.toString(level));
        }

        System.out.printf("Shortest Path From: %s To: %s is %s %n", from, to, level[to]);

        return level[to];
    }

}
