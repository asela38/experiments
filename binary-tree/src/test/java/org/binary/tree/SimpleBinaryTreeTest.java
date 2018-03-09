package org.binary.tree;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class SimpleBinaryTreeTest {

    public static class SNode {

        public SNode(int value) {
            this.value = value;
        }

        public final int value;
        public SNode     right;
        public SNode     left;

    }

    public static class STree {

        public final SNode root;

        public STree(SNode root) {
            this.root = root;
        }

        public void add(SNode sNode) {
            if (root.value < sNode.value)
                root.right = sNode;
            else
                root.left = sNode;
        }

    }

    @Test
    public void whenLessValueNodeAddItShouldBeSetToLeft() throws Exception {
        SNode node5 = new SNode(5), node2 = new SNode(2);
        STree sTree = new STree(node5);
        sTree.add(node2);
        assertThat(sTree.root.left, is(node2));
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
