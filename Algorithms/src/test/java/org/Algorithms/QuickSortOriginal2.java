package org.Algorithms;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;

public class QuickSortOriginal2 {

	private static final String FILE_PATH_1 = "/Users/asela.illayapparachc/Documents/algorithms/QuickSort.txt";

	@Test
	public void testQuickSort() throws Exception {
		int multiplier = 1000;
		int[] array1 = ThreadLocalRandom.current().ints(0, 20 * multiplier).limit(20 * multiplier).toArray();
		int[] array2 = Arrays.copyOf(array1, array1.length);
		quickSort(array1);
		Arrays.sort(array2);
		assertEquals(Arrays.toString(array1), Arrays.toString(array2));
	}

	@Test
	public void testWithFile() throws Exception {
		int[] ints = readNumbers();
		System.out.println(Arrays.stream(ints).summaryStatistics());
		System.out.printf("%n%s-%s-%s%n", ints[0], ints[ints.length / 2], ints[ints.length - 1]);
		a = 0;
		quickSort(ints);
		System.out.println(a);

		a = 0;
		ints = readNumbers();
		quickSort(ints);
		System.out.println(a);

		a = 0;
		ints = readNumbers();
		quickSort(ints);
		System.out.println(a);
	}

	private int[] readNumbers() throws IOException {
		return Files.readAllLines(new File(FILE_PATH_1).toPath()).stream().mapToInt(Integer::parseInt).toArray();
	}

	private void quickSort(int[] array) {
		quickSortOnRange(array, 0, array.length - 1);
	}

	int a = 0;

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

		int mid = (from + to) >> 1;
		if (isBetween(arr, from, to, mid))
			swap(arr, from, mid);
		if (isBetween(arr, from, mid, to))
			swap(arr, from, to);

		int pivot = arr[from];
		int index = from + 1;

		a += to - from;
		for (int i = from + 1; i <= to; i++)
			if (arr[i] < pivot) {
				swap(arr, index++, i);
			}

		swap(arr, --index, from);

		return index;
	}

	private boolean isBetween(int[] arr, int from, int to, int test) {
		return (arr[test] > arr[from] && arr[test] < arr[to]) || (arr[test] < arr[from] && arr[test] > arr[to]);
	}

	private void swap(int[] arr, int from, int to) {
		int temporary = arr[from];
		arr[from] = arr[to];
		arr[to] = temporary;
	}

}
