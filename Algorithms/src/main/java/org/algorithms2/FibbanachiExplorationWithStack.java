package org.algorithms2;

import java.util.HashMap;
import java.util.Stack;

import org.junit.Test;

public class FibbanachiExplorationWithStack {

    @Test
    public void test() throws Exception {
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(1, 1);
        map.put(2, 1);
        Stack<Integer> stack = new Stack<Integer>();
        int x = 10;
        stack.push(x);
        while (!stack.isEmpty()) {
            System.out.println("Map: " + map);
            System.out.println("Stack: " + stack);

            Integer v = stack.pop();
            if (!map.containsKey(v - 1)) {
                stack.push(v);
                stack.push(v - 1);
                continue;
            }
            if (!map.containsKey(v - 2)) {
                stack.push(v);
                stack.push(v - 2);
                continue;
            }
            map.put(v, map.get(v - 1) + map.get(v - 2));

        }

        System.out.println(map.get(x));
        ;
    }

 
}
