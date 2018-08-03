package org.algorithms2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.junit.Test;

public class BfsForDisjointSetsExploration {

    /*
     *        (1)  (9)               (8)
     *         | \ /                / |
     *         | (5)    (2)--(4)  (6) |
     *         | / \                \ |
     *        (3)  (7)               (10)
     */
    private HashMap<Integer, List<Integer>> createSimpleGraph() {
        System.out.println("---------------------------");
        System.out.println("(1)  (9)               (8) ");
        System.out.println(" | \\ /                / |  ");
        System.out.println(" | (5)    (2)--(4)  (6) |  ");
        System.out.println(" | / \\                \\ |  ");
        System.out.println("(3)  (7)               (10)");
        System.out.println("----------------------------");

        HashMap<Integer, List<Integer>> edges = new HashMap<>();
        edges.put(0, Arrays.asList());
        edges.put(1, Arrays.asList(3, 5));
        edges.put(2, Arrays.asList(4));
        edges.put(3, Arrays.asList(1, 5));
        edges.put(4, Arrays.asList(2));
        edges.put(5, Arrays.asList(1, 9, 3, 7));
        edges.put(6, Arrays.asList(8, 10));
        edges.put(7, Arrays.asList(5));
        edges.put(8, Arrays.asList(6, 10));
        edges.put(9, Arrays.asList(5));
        edges.put(10, Arrays.asList(6, 8));
        return edges;
    }

    @Test
    public void testDisjoints() throws Exception {
        HashMap<Integer, List<Integer>> graph = createSimpleGraph();

        int[] reachable = new int[graph.size()];
        Arrays.fill(reachable, -1);

        for (int i = 0; i < reachable.length; i++)
            if (reachable[i] == -1) {
                reachable[i] = i;
                Queue<Integer> queue = new LinkedList<>();
                queue.offer(i);
                while(!queue.isEmpty()) {
                    Integer v = queue.poll();
                    List<Integer> wList = graph.get(v);
                    for (Integer w : wList) {
                        if(reachable[w] == -1) {
                            reachable[w] = reachable[v];
                            queue.offer(w);
                        }
                    }
                    
                    System.out.println("\n Queue : " + queue);
                    System.out.println(" Visited : " + Arrays.toString(reachable));
                }
            }

    }

}
