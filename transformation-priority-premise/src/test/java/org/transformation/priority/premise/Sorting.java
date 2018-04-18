package org.transformation.priority.premise;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Stopwatch;

public class Sorting {

    @Test
    public void emptyArraySort() throws Exception {
        int[] array = new int[0];
        sort(array);
        assertThat(array.length, is(0));
    }

    @Test
    public void oneElementArraySorted() throws Exception {
        int[] array = new int[] { 3 };
        sort(array);
        assertThat(array[0], is(3));
    }

    @Test
    // Here I suppose need to have a robust and easy way to test
    public void twoElementArraySorted() throws Exception {
        testSorted("3,1", "1,3");
        testSorted("1,3", "1,3");
    }

    @Test
    public void threeElementArrayWithTwoDisplacement() throws Exception {
        testSorted("1,2,3", "1,2,3");
        testSorted("1,3,2", "1,2,3");
        testSorted("2,1,3", "1,2,3");
    }

    @Test
    public void testThreeElementsWithThreeDisplacement() throws Exception {
        testSorted("2,3,1", "1,2,3");
        testSorted("3,2,1", "1,2,3");
        testSorted("3,1,2", "1,2,3");
    }

    @Test
    public void testFourElements() throws Exception {
        testSorted("1,3,2,4", "1,2,3,4");
        testSorted("1,3,4,2", "1,2,3,4");
        testSorted("4,3,2,1", "1,2,3,4");
    }

    @Test
    public void testRandomList() throws Exception {
        testSorted("10,9,8,7,6,5,4,3,2,1", "1,2,3,4,5,6,7,8,9,10");
    }

    @Rule
    public Stopwatch stopWatch = new Stopwatch() {};

    @Test
    public void testLarge() throws Exception {

        for (int i = 0; i < 100; i++) {
            sort(IntStream.generate(ThreadLocalRandom.current()::nextInt).limit(500 * i).toArray());
            System.out.println(stopWatch.runtime(TimeUnit.MILLISECONDS));
        }
    }

    @Test
    public void sort() throws Exception {
        for (int i = 0; i < 100; i++) {
            Arrays.sort(IntStream.generate(ThreadLocalRandom.current()::nextInt).limit(500 * i).toArray());
            System.out.println(stopWatch.runtime(TimeUnit.MILLISECONDS));
        }
    }

    public void testSorted(String array1, String array2) {
        assertTrue(array1.length() == array2.length());
        String[] numbersAsString = array1.split(",");
        int[] array = new int[numbersAsString.length];
        for (int i = 0; i < numbersAsString.length; i++) {
            array[i] = Integer.parseInt(numbersAsString[i]);
        }
        sort(array);

        String[] resulstAsString = array2.split(",");
        int[] result = new int[resulstAsString.length];
        for (int i = 0; i < resulstAsString.length; i++) {
            result[i] = Integer.parseInt(resulstAsString[i]);
        }

        for (int i = 0; i < array.length; i++) {
            assertThat(String.format("Arrays are not matching %noriginal:%s %nresult: %s %nexpected: %s, @ %d", array1,
                    Arrays.toString(array), array2, i), array[i], is(result[i]));
        }
    }

    // in in-memory sort can a merge sort be accomplished.
    // Or in-memory start of sorting is bad approache
    // Mutation or NonMutation
    // Solve problem for time complexity over space complexity
    private void sort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++)
            for (int j = 0; j < arr.length - i - 1; j++)
                ifGreaterThanNextSwap(arr, j);

    }

    private void ifGreaterThanNextSwap(int[] arr, int i) {
        if (arr[i] > arr[i + 1]) {
            swap(arr, i, i + 1);
        }
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
