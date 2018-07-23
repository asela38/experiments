package org.algorithms2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.junit.Test;

public class BFSExploration {

    @Test
    public void bfsTest() throws Exception {

        boolean[] visited = new boolean[5];

        HashMap<Integer, List<Integer>> edges = new HashMap<>();
        edges.put(0, Arrays.asList(1, 2));
        edges.put(1, Arrays.asList(2, 3, 0));
        edges.put(2, Arrays.asList(0, 1, 3, 4));
        edges.put(3, Arrays.asList(1, 2, 4));
        edges.put(4, Arrays.asList(2, 3));

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

}
