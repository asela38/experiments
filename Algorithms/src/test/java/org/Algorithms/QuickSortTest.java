package org.Algorithms;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.Test;

public class QuickSortTest {

    @Test
    public void partitionTest() throws Exception {
        assertThat(partition("1"), is("1"));
        assertThat(partition("1,2"), is("1,2"));
        assertThat(partition("2,1"), is("1,2"));
        assertThat(partition("2,2"), is("2,2"));
        assertThat(partition("1,2,3"), is("1,3,2"));
        assertThat(partition("2,3,1"), is("1,2,3"));
    }

    @Test
    public void quickTest() throws Exception {
        System.out.println(quick("23,3,4,5,6,2,8,9,0,11,22,33,55,78,32,12,12,3"));
    }

    private String partition(String string) {
        int[] original = Arrays.stream(string.split(",")).mapToInt(Integer::parseInt).toArray();
        int[] partitioned = partition(original);
        return Arrays.stream(partitioned).mapToObj(String::valueOf).collect(Collectors.joining(","));
    }

    private String quick(String string) {
        int[] original = Arrays.stream(string.split(",")).mapToInt(Integer::parseInt).toArray();
        int[] partitioned = quick(original);
        return Arrays.stream(partitioned).mapToObj(String::valueOf).collect(Collectors.joining(","));
    }

    // Implementation Error 1. No Swap
    private int[] partition(int[] original) {
        System.out.printf("%nOriginal : %s%n", Arrays.toString(original));
        int ig = original.length - 1;
        int il = 0;
        int pivot = original[0];
        for (int i = 1; i <= ig; i++) {
            System.out.printf("(Pivot : %s) %s ( value : %s ) %n", pivot,
                    Integer.compare(pivot, original[i]) < 0 ? "<" : ">=", original[i]);
            if (original[i] < pivot) {
                swap(original, il++, i);
            } else if (original[i] >= pivot) {
                swap(original, ig--, i--);
            }
            System.out.println(Arrays.toString(original));
        }
        original[il] = pivot;
        return original;
    }

    private int[] quick(int[] original) {
        return quick(original, 0, original.length - 1);
    }

    private int[] quick(int[] original, int low, int high) {

        int oLow = low, oHigh = high;
        System.out.printf("%nOriginal : %s (i:%s -> %s) %n", Arrays.toString(original),  low, high);

        if (low >= high)
            return original;

        int i = low;
        int pivot = original[i];
        while (++i <= high) {
            System.out.printf("(Pivot : %s) %s ( value : %s ) %n", pivot,
                    Integer.compare(pivot, original[i]) < 0 ? "<" : ">=", original[i]);
            if (original[i] < pivot) {
                swap(original, low++, i);
            } else if (original[i] >= pivot) {
                swap(original, high--, i--);
            }
            System.out.println(Arrays.toString(original));
        }
        original[i] = pivot;

        quick(original, oLow, i - 1);
        quick(original, i + 1, oHigh);

        return original;
    }

    private void swap(int[] original, int f, int s) {
        int temp = original[f];
        original[f] = original[s];
        original[s] = temp;
    }
}
