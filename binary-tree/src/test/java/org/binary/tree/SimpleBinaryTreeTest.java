package org.binary.tree;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;

public class SimpleBinaryTreeTest {

    public static class SNode {

        public SNode(int value) {
            this.value = value;
        }

        public final int value;
        public SNode     right;
        public SNode     left;

        @Override
        public String toString() {
            if (left == null && right == null)
                return String.format("%s", value);
            if (left == null)
                return String.format("(%s->%s)", value, String.valueOf(right));
            if (right == null)
                return String.format("(%s<-%s)", String.valueOf(left), value);

            return String.format("(%s<-%s->%s)", String.valueOf(left), value, String.valueOf(right));
        }

    }

    public static class STree {

        public final SNode root;

        public STree(SNode root) {
            this.root = root;
        }

        public void add(SNode sNode) {
            add(root, sNode);
        }

        private void add(SNode parent, SNode child) {
            if (parent.value < child.value)
                if (parent.right == null)
                    parent.right = child;
                else
                    add(parent.right, child);
            else if (parent.left == null)
                parent.left = child;
            else
                add(parent.left, child);

        }

        @Override
        public String toString() {
            return root.toString();
        }

    }

    private SNode[] nodes = IntStream.range(0, 10).mapToObj(SNode::new).collect(Collectors.toList())
            .toArray(new SNode[0]);

    @Test
    public void toStringOfS() throws Exception {
        STree sTree = new STree(nodes[5]);
        sTree.add(nodes[3]);
        sTree.add(nodes[4]);
        sTree.add(nodes[2]);
        sTree.add(nodes[8]);
        sTree.add(nodes[7]);
       // sTree.add(nodes[9]);
        System.out.println(sTree);
    }

    @Test
    public void when2NodesInWithDescendingValuesAddedTree_shouldSetASConsecutiveLeft() {
        STree sTree = new STree(nodes[5]);
        sTree.add(nodes[4]);
        sTree.add(nodes[2]);
        assertThat(sTree.root.left, is(nodes[4]));
        assertThat(sTree.root.left.left, is(nodes[2]));
    }

    @Test
    public void when2NodesInWithAscendingValuesAddedTree_shouldSetASConsecutiveRight() {
        STree sTree = new STree(nodes[5]);
        sTree.add(nodes[7]);
        sTree.add(nodes[9]);
        assertThat(sTree.root.right, is(nodes[7]));
        assertThat(sTree.root.right.right, is(nodes[9]));
    }

    @Test
    public void whenLessValueNodeAddItShouldBeSetToLeft() throws Exception {
        STree sTree = new STree(nodes[5]);
        sTree.add(nodes[2]);
        assertThat(sTree.root.left, is(nodes[2]));
    }

    @Test
    public void whenAddANodeWhichHasValueGreaterThanRootOfOneNodeTreeItShouldBeSetToRightOfRootNode() throws Exception {
        SNode node5 = new SNode(5);
        STree sTree = new STree(node5);
        SNode node10 = new SNode(10);
        sTree.add(node10);
        assertThat(sTree.root.right, is(node10));
    }

    @Test
    public void sTreeShouldBeClassWhichHasRootNode() throws Exception {
        assertNotNull(new STree(new SNode(5)));
    }

    @Test
    public void sTreeShouldGiveTheRootNodeWhichHasBeenCreatedTreeWith() throws Exception {
        SNode sNode = new SNode(10);
        assertEquals(sNode, new STree(sNode).root);
    }

    @Test
    public void nodeShouldCreateWithEncapsulatedValue() throws Exception {
        assertNotNull(new SNode(10));
    }

    @Test
    public void nodeShouldSetItToInternalValue() throws Exception {
        assertThat(new SNode(10).value, is(10));
    }

    @Test
    public void nodeShouldHaveRightNodeWithDefaultNull() throws Exception {
        assertNull(new SNode(10).right);
    }

    @Test
    public void nodeShouldHaveLeftNodeWithDefaultNull() throws Exception {
        assertNull(new SNode(10).left);
    }
}
