package org.binary.tree;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class BinaryTreeTest {

    @Test
    public void inEmptyBinaryTreeRootNodeShouldBeEmpty() throws Exception {
        BinaryTree<Integer> binaryTree = new BinaryTree<Integer>();
        Optional<Integer> root = binaryTree.getRoot();
        assertFalse(root.isPresent());
    }

    @Test
    public void firstElementAddsToTheTreeShouldBeRoot() throws Exception {
        BinaryTree<Integer> binaryTree = new BinaryTree<Integer>();
        binaryTree.insert(10);
        Integer root = binaryTree.getRoot().orElseThrow(Exception::new);
        assertThat(root, is(10));
    }

    @Test
    public void secondElementAddsToTheTreeShouldNotBeRoot() throws Exception {
        BinaryTree<Integer> binaryTree = new BinaryTree<Integer>();
        binaryTree.insert(10);
        binaryTree.insert(20);
        Integer root = binaryTree.getRoot().orElseThrow(Exception::new);
        assertThat(root, is(not(20)));
    }

    @Test
    public void ifSecondElementIsBiggerItShouldBeOnRightSide() throws Exception {
        BinaryTree<Integer> binaryTree = new BinaryTree<Integer>();
        binaryTree.insert(10);
        binaryTree.insert(20);
        Optional<BinaryTree<Integer>> right = binaryTree.getRightTree();
        assertTrue(right.isPresent());
        assertThat(right.get().getRoot().get(), is(20));
    }

    @Test
    public void ifSecondElementIsLessOrEqualsItShouldBeOnRightSide() throws Exception {
        BinaryTree<Integer> binaryTree = new BinaryTree<Integer>();
        binaryTree.insert(10);
        binaryTree.insert(5);
        Optional<BinaryTree<Integer>> left = binaryTree.getLeftTree();
        assertTrue(left.isPresent());
        assertThat(left.get().getRoot().get(), is(5));
    }

    @Test
    public void ifThirdElementIsBiggerItShouldBeOnRightOfRightSide() throws Exception {
        BinaryTree<Integer> binaryTree = new BinaryTree<Integer>();
        binaryTree.insert(10);
        binaryTree.insert(20);
        binaryTree.insert(30);
        Optional<BinaryTree<Integer>> right = binaryTree.getRightTree();
        assertTrue(right.isPresent());
        assertThat(right.get().getRoot().get(), is(20));
        System.out.println(binaryTree);
    }

    @Test
    public void ifForthElementIsLessThanSecondItShouldBeOnLeftOfRightSide() throws Exception {
        BinaryTree<Integer> binaryTree = new BinaryTree<Integer>();
        binaryTree.insert(10);
        binaryTree.insert(20);
        binaryTree.insert(30);
        binaryTree.insert(15);
        Optional<BinaryTree<Integer>> right = binaryTree.getRightTree();
        assertTrue(right.isPresent());
        assertThat(right.get().getLeftTree().get().getRoot().get(), is(15));
        System.out.println(binaryTree);
    }

    @Test
    public void postOrderTraversalOfSingleElementTree() throws Exception {
        BinaryTree<Integer> tree = BinaryTree.with(11);
        List<Integer> potList = tree.postOrderTraverseList();
        assertThat(potList, is(hasItem(11)));
    }

    @Test
    public void postOrderTraversalOfTwoAscending() throws Exception {
        BinaryTree<Integer> tree = BinaryTree.with(11, 12);
        List<Integer> potList = tree.postOrderTraverseList();
        System.out.println(potList);
        assertEquals(potList, Arrays.asList(11, 12));
    }

    @Test
    public void postOrderTraversalEqualsSortedForInteger() throws Exception {
        List<Integer> list = IntStream.range(1, 100).boxed().collect(Collectors.toList());
        Collections.shuffle(list);
        System.out.println(list);
        BinaryTree<Integer> tree = BinaryTree.with(list.toArray(new Integer[0]));
        System.out.println(tree);
        assertEquals(tree.postOrderTraverseList(), sort(list));
    }

    private <T extends Comparable<T>> List<T> sort(List<T> list) {
        Collections.sort(list);
        return list;
    }

    @Test
    public void postOrderTraversalEqualsSortedForString() throws Exception {
        List<String> list = Arrays.asList("Post", "Order", "Traversal", "Equals", "Sorted", "For", "String");
        Collections.shuffle(list);
        System.out.println(list);
        BinaryTree<String> tree = BinaryTree.with(list.toArray(new String[0]));
        System.out.println(tree);
        assertEquals(tree.postOrderTraverseList(), sort(list));
    }

}
