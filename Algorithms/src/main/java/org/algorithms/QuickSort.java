package org.algorithms;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;

public class QuickSort {

    @Test
    public void testQuickSort() throws Exception {
        int multiplier = 1000;
        int[] array1 = ThreadLocalRandom.current().ints(0, 20 * multiplier).limit(20 * multiplier).toArray();
        int[] array2 = Arrays.copyOf(array1, array1.length);
        quickSort(array1);
        Arrays.sort(array2);
        assertEquals(Arrays.toString(array1), Arrays.toString(array2));
    }

    private static int swapCounter = 0;

    
    @Test
    public void testToIntegerMax() throws Exception {
        int[] array1 = ThreadLocalRandom.current().ints(0, Integer.MAX_VALUE).limit(2_000_000).toArray();
        int[] array2 = Arrays.copyOf(array1, array1.length);
        long a = System.currentTimeMillis();
        Arrays.sort(array2);
        System.out.println(System.currentTimeMillis() - a);
        a = System.currentTimeMillis();
        quickSort(array1);
        System.out.println(System.currentTimeMillis() - a);
        assertEquals(Arrays.toString(array1),  Arrays.toString(array2));
        System.out.println(swapCounter);
    }
    
    @Test
    public void testName() throws Exception {
        testWithSwap(1, 3, 5, 2, 4, 6);
        testWithSwap(6, 5, 4, 3, 2, 1);
        testWithSwap(1, 2, 3, 4, 5, 6);
    }

    private void testWithSwap(int... array1) {
        swapCounter = 0;
        int[] array2 = Arrays.copyOf(array1, array1.length);
        quickSort(array1);
        Arrays.sort(array2);
        assertEquals(Arrays.toString(array1), Arrays.toString(array2));
        System.out.println(swapCounter);
    }

    private void quickSort(int[] array) {
        quickSortOnRange(array, 0, array.length - 1);
    }

    private int[] quickSortOnRange(int[] arr, int from, int to) {

        if (to <= from)
            return arr;

        int point = partitionOnRange(arr, from, to);

        if (point > from)
            quickSortOnRange(arr, from, point - 1);

        if (point < to)
            quickSortOnRange(arr, point + 1, to);

        return arr;
    }

    private int partitionOnRange(int[] arr, int from, int to) {

        int pivot = arr[from];
        int index = from + 1;

        int i = from;
        while (i <= to && arr[i] >= pivot)
            i++;

        while (i <= to) {
            if (arr[i] < pivot)
                swap(arr, index++, i);
            i++;
        }

        swap(arr, --index, from);

        return index;
    }

    private void swap(int[] arr, int from, int to) {
        if (from == to)
          return;
        int temporary = arr[from];
        arr[from] = arr[to];
        arr[to] = temporary;
        swapCounter++;
    }

}
