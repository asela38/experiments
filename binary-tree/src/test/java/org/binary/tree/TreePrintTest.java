package org.binary.tree;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class TreePrintTest {

    public static List<String> tree(int depth, String string) {
        return new TreePrint().tree(depth, string);
    }

    @Test
    public void treeOfZeroElement() throws Exception {
        assertEquals(TreePrintTest.tree(0, "x"), Arrays.asList());
    }

    @Test
    public void treeOfOneElement() throws Exception {
        assertEquals(TreePrintTest.tree(1, "x"), Arrays.asList("x"));
    }

    @Test
    public void treeOfTwoElements() throws Exception {
        assertEquals(TreePrintTest.tree(2, "x"), Arrays.asList(" x ", "x x"));
    }

    @Test
    public void testOfThreeElements() throws Exception {
        assertEquals(TreePrintTest.tree(3, "x"), Arrays.asList("   x   ", " x   x ", "x x x x"));
    }

    @Test
    public void testOfMoreElements() throws Exception {
        TreePrintTest.tree(10, "*").stream().forEach(System.out::println);
    }

    @Test
    public void testOfTwoElementsWithSize2String() throws Exception {
        assertEquals(TreePrintTest.tree(2, "xx"), Arrays.asList("  xx  ", "xx  xx"));
    }

    @Test
    public void testOfThreeElementsWithSize2String() throws Exception {
        assertEquals(TreePrintTest.tree(3, "xx"),
                Arrays.asList("      xx      ", "  xx      xx  ", "xx  xx  xx  xx"));
    }

    @Test
    public void testOfMoreElementsSize2String() throws Exception {
        TreePrintTest.tree(6, "**").stream().forEach(System.out::println);
    }

    @Test
    public void testOfMoreElementsSize5String() throws Exception {
        TreePrintTest.tree(5, "*|+|*").stream().forEach(System.out::println);
    }

    @Test
    public void purposeVerification() throws Exception {
        TreePrintTest.tree(6, "%s").stream().forEach(System.out::println);
    }

}
