package org.Algorithms;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.junit.Test;

public class QuickSortExploration {

    @Test
    public void testSwap() throws Exception {
        int[] arr = new int[] { 2, 1 };
        swap(arr, 0, 1);
        assertTrue(Arrays.equals(arr, new int[] { 1, 2 }));
    }

    @Test
    public void testSwapInLargeArray() throws Exception {
        int[] arr = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17 };
        swap(arr, 5, 7);
        assertTrue(Arrays.equals(arr, new int[] { 1, 2, 3, 4, 5, 8, 7, 6, 9, 10, 11, 12, 13, 14, 15, 16, 17 }));
    }

    private void swap(int[] arr, int i, int j) {
        int temporary = arr[i];
        arr[i] = arr[j];
        arr[j] = temporary;
    }

    @Test
    public void testPartition() throws Exception {
        testPartitioned(1, 2);
    }

    @Test
    public void testPartitionTrivial() throws Exception {
        testPartitioned(2, 1);
    }

    private void testPartitioned(int... arr) {
        System.out.printf("%n Original : %s %n", Arrays.toString(arr));
        int i = partition(arr);
        System.out.printf("Partitioned : %s %n", Arrays.toString(arr));
        testPartitionedOnIndex(arr, i);
    }

    @Test
    public void testPartitionOnNonTrivial() throws Exception {
        testPartitioned(3, 8, 1, 2, 4, 6, 7, 8, 9, 0, 12, 11, 13, 1, 2, 3, 4);
    }

    private void testPartitionedOnIndex(int[] arr, int i) {
        for (int j = i + 1; j < arr.length; j++)
            assertTrue(arr[j] >= arr[i]);
        for (int j = i - 1; j >= 0; j--)
            assertTrue(arr[j] < arr[i]);
    }

    private int partition(int[] arr) {
        int pivot = arr[0];
        int index = 1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < pivot) {
                swap(arr, index++, i);
            }
        }

        swap(arr, --index, 0);

        return index;
    }

    @Test
    public void testPartitionOnRange() throws Exception {
        testPartitionedOnRange(3, 10, 3, 8, 1, 2, 4, 6, 7, 8, 9, 0, 12, 11, 13, 1, 2, 3, 4);
    }

    @Test
    public void testPartitionOnRangeTrivial() throws Exception {
        testPartitionedOnRange(1, 3, 6, 3, 5, 2, 1);
        testPartitionedOnRange(1, 3, 6, 2, 5, 3, 1);
        testPartitionedOnRange(1, 3, 6, 2, 3, 5, 1);
        testPartitionedOnRange(1, 3, 6, 2, 3, 2, 1);
        testPartitionedOnRange(1, 3, 6, 2, 1, 1, 1);
    }

    private void testPartitionedOnRange(int from, int to, int... arr) {
        System.out.printf("%n Original : %s %n", Arrays.toString(arr));
        int i = partitionOnRange(from, to, arr);
        System.out.printf("Partitioned : %s %n", Arrays.toString(arr));
        testPartitionedOnIndexInRange(arr, i, from, to);
    }

    private int partitionOnRange(int from, int to, int[] arr) {
        if (to <= from)
            return to;
        int pivot = arr[from];
        int index = from + 1;
        for (int i = from; i <= to; i++) {
            if (arr[i] < pivot) {
                swap(arr, index++, i);
            }
        }

        swap(arr, --index, from);

        return index;
    }

    private void testPartitionedOnIndexInRange(int[] arr, int i, int from, int to) {
        for (int j = i + 1; j < to; j++)
            assertTrue(arr[j] >= arr[i]);
        for (int j = i - 1; j >= from; j--)
            assertTrue(arr[j] < arr[i]);
    }

    @Test
    public void testQuickSortTrivial() throws Exception {
        quickSortTest("1,2,3", 0, 2, 1, 2, 3);
        quickSortTest("1,2,3", 0, 2, 1, 3, 2);
        quickSortTest("1,2,3", 0, 2, 2, 1, 3);
        quickSortTest("1,2,3", 0, 2, 2, 3, 1);
        quickSortTest("1,2,3", 0, 2, 3, 2, 1);
        quickSortTest("1,2,3", 0, 2, 3, 1, 2);

    }
    
    @Test
    public void testQuickSort() throws Exception {
        int multiplier = 1000;
        int[] array1 = ThreadLocalRandom.current().ints(0, 20 * multiplier).limit(20 * multiplier ).toArray();
        int[] array2 = Arrays.copyOf(array1, array1.length);
        quickSort(array1);
        Arrays.sort(array2);
        assertEquals(Arrays.toString(array1),  Arrays.toString(array2));
    }
    
    @Test
    public void testToIntegerMax() throws Exception {
        int[] array1 = ThreadLocalRandom.current().ints(0, Integer.MAX_VALUE).limit(2_000_000).toArray();
        int[] array2 = Arrays.copyOf(array1, array1.length);
        long a = System.currentTimeMillis();
        quickSort(array1);
        System.out.println(System.currentTimeMillis() - a);
        a = System.currentTimeMillis();
        Arrays.sort(array2);
        System.out.println(System.currentTimeMillis() - a);
        assertEquals(Arrays.toString(array1),  Arrays.toString(array2));
    }

    private void quickSort(int[] array) {
        quickSortOnRange(0, array.length - 1, array);
    }

    private void quickSortTest(String result, int from, int to, int... arr) {
        sortedArrayTest(quickSortOnRange(from, to, arr), "1,2,3");
    }

    private void sortedArrayTest(int[] sorted, String result) {
        assertThat(Arrays.stream(sorted).boxed().map(Object::toString).collect(Collectors.joining(",")), is(result));
    }

    private int[] quickSortOnRange(int from, int to, int... arr) {
        if (to <= from)
            return arr;

        int point = partitionOnRange(from, to, arr);
        quickSortOnRange(from, point - 1, arr);
        quickSortOnRange(point + 1, to, arr);

        return arr;
    }
}
