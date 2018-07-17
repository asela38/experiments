package org.algorithms;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;

public class MergeSort {

    @Test
    public void testPartitioning() throws Exception {
        verifyPartitioning(partition(new int[] {}), "[]", "[]");
        verifyPartitioning(partition(new int[] { 1 }), "[1]", "[]");
        verifyPartitioning(partition(new int[] { 1, 2 }), "[1]", "[2]");
        verifyPartitioning(partition(new int[] { 1, 2, 3 }), "[1,2]", "[3]");
        verifyPartitioning(partition(new int[] { 1, 2, 3, 4 }), "[1,2]", "[3,4]");
        verifyPartitioning(partition(new int[] { 1, 2, 3, 4, 5 }), "[1,2,3]", "[4,5]");
    }

    public int[][] partition(int[] a) {
        int[] b = new int[(a.length >> 1) + (a.length % 2)];
        int[] c = new int[a.length >> 1];

        for (int i = 0; i < b.length; i++) {
            b[i] = a[i];
        }

        for (int i = b.length; i < a.length; i++) {
            c[i - b.length] = a[i];
        }

        return new int[][] { b, c };

    }

    private void verifyPartitioning(int[][] result, String f, String s) {
        assertThat(Arrays.toString(result[0]).replaceAll("\\s", ""), is(f));
        assertThat(Arrays.toString(result[1]).replaceAll("\\s", ""), is(s));
    }

    @Test
    public void testMerge() throws Exception {
        assertThat(Arrays.toString(merge(new int[] {}, new int[] {})).replaceAll("\\s", ""), is("[]"));
        assertThat(Arrays.toString(merge(new int[] { 1 }, new int[] {})).replaceAll("\\s", ""), is("[1]"));
        assertThat(Arrays.toString(merge(new int[] {}, new int[] { 1 })).replaceAll("\\s", ""), is("[1]"));
        assertThat(Arrays.toString(merge(new int[] { 1 }, new int[] { 1 })).replaceAll("\\s", ""), is("[1,1]"));
        assertThat(Arrays.toString(merge(new int[] { 1 }, new int[] { 2 })).replaceAll("\\s", ""), is("[1,2]"));
        assertThat(Arrays.toString(merge(new int[] { 2 }, new int[] { 1 })).replaceAll("\\s", ""), is("[1,2]"));
        assertThat(Arrays.toString(merge(new int[] { 1, 2 }, new int[] { 1, 3 })).replaceAll("\\s", ""),
                is("[1,1,2,3]"));
        assertThat(Arrays.toString(merge(new int[] { 2 }, new int[] { 1, 3 })).replaceAll("\\s", ""), is("[1,2,3]"));
        assertThat(Arrays.toString(merge(new int[] { 1, 3 }, new int[] { 2 })).replaceAll("\\s", ""), is("[1,2,3]"));
        assertThat(Arrays.toString(merge(new int[] { 1, 3, 5, 7, 9 }, new int[] { 2, 4, 6, 8 })).replaceAll("\\s", ""),
                is("[1,2,3,4,5,6,7,8,9]"));
        assertThat(Arrays.toString(merge(new int[] { 1, 2, 3, 4 }, new int[] { 5, 6, 7, 8 })).replaceAll("\\s", ""),
                is("[1,2,3,4,5,6,7,8]"));

    }

    public int[] merge(int[] a, int[] b) {
        if (b.length == 0)
            return a;
        if (a.length == 0)
            return b;

        int[] c = new int[a.length + b.length];
        for (int i = 0, j = 0, k = 0; i < c.length; i++) {
            if (k >= b.length)
                c[i] = a[j++];
            else if (j >= a.length)
                c[i] = b[k++];
            else if (a[j] < b[k])
                c[i] = a[j++];
            else
                c[i] = b[k++];
        }
        return c;
    }

    @Test
    public void testMergeSort() throws Exception {

        int[] array = ThreadLocalRandom.current().ints(20, 0, 19).toArray();
        System.out.println(Arrays.toString(array));
        System.out.println(Arrays.toString(mergeSort(array)));

        int[] array2 = ThreadLocalRandom.current().ints(1000, 1, 9999).toArray();
        int[] array3 = Arrays.copyOf(array2, array2.length);
        Arrays.sort(array3);
        assertThat(Arrays.toString(mergeSort(array2)), is(Arrays.toString(array3)));

    }

    public int[] mergeSort(int[] array) {
        if (array.length < 2)
            return array;
        int[][] partition = partition(array);

        return merge(mergeSort(partition[0]), mergeSort(partition[1]));

    }

    @Test
    public void testMergeSort2() throws Exception {

        int[] array2 = ThreadLocalRandom.current().ints(1000, 1, 9999).toArray();
        int[] array3 = Arrays.copyOf(array2, array2.length);
        Arrays.sort(array3);
        assertThat(Arrays.toString(mergeSort2(array2)), is(Arrays.toString(array3)));

    }

    public int[] mergeSort2(int[] array) {
        if (array.length < 2)
            return array;
        int mid = (array.length >> 1) + (array.length % 2);
        return merge(mergeSort2(Arrays.copyOfRange(array, 0, mid)),
                mergeSort2(Arrays.copyOfRange(array, mid, array.length)));

    }

}
