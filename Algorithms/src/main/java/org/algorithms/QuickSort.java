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

    private void quickSort(int[] array) {
        quickSortOnRange(array, 0, array.length - 1);
    }

    private int[] quickSortOnRange(int[] arr, int from, int to) {

        if (to <= from)

            return arr;

        int point = partitionOnRange(arr, from, to);
        quickSortOnRange(arr, from, point - 1);
        quickSortOnRange(arr, point + 1, to);

        return arr;
    }

    private int partitionOnRange(int[] arr, int from, int to) {
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

    private void swap(int[] arr, int from, int to) {
        int temporary = arr[from];
        arr[from] = arr[to];
        arr[to] = temporary;
    }

}
