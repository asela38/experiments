package org.binary.tree;

import org.junit.Test;

public class NodeTest {

    @Test
    public void toStringMethod() throws Exception {

        Node<Integer> node = new Node<Integer>(100);
        System.out.println(node);
        node.setLeft(new Node<Integer>(30));
        System.out.println(node);

        node.left().ifPresent(ln -> {
            ln.setLeft(new Node<Integer>(20));
            ln.setRight(new Node<Integer>(50));
        });

        System.out.println(node);
    }

}
