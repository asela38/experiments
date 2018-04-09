package org.transformation.priority.premise;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;

public class Sorting {

	@Test(expected = IllegalArgumentException.class)
	public void nullArrayToSortIsExceptionalSituation() throws Exception {
		sort(null);
	}

	@Test
	public void emptyArrayAskedToSort() throws Exception {
		int[] arr = new int[0];
		sort(arr);
		assertThat(arr.length, is(0));
	}

	@Test
	public void testArrayWithOneElement() throws Exception {
		int element = ThreadLocalRandom.current().nextInt();
		int[] arr = new int[] { element };
		sort(arr);
		assertThat(arr[0], is(element));
	}

	private void testSorted(int[] arr) {
		sort(arr);
		for (int i = 0; i < arr.length - 1; i++) {
			if (arr[i] > arr[i + 1]) {
				fail(String.format("Array at index %d is not sorted [...,%d,%d,...]", i, arr[i], arr[i + 1]));
			}
		}
	}

	@Test
	public void allElementOrder() throws Exception {
		testSorted(new int[] { 10, 14, 15, 30, 40, 43, 44, 44, 45 });
	}

	@Test
	public void onlyFirstTwoNotInOrder() throws Exception {
		testSorted(new int[] { 14, 10, 15, 30, 40, 43, 44, 44, 45 });
	}

	@Test
	public void threeElementSetLastTwoNotInOrder() throws Exception {
		testSorted(new int[] { 14, 20, 15 });
	}

	@Test
	public void threeElementSetFirstAndLastNotInOrder() throws Exception {
		testSorted(new int[] { 24, 20, 15 });
	}

	private void sort(int[] arr) {
		if (arr == null)
			throw new IllegalArgumentException();

		int l = arr.length;

		if (l > 1 && arr[0] > arr[1]) {
			swap(arr, 0, 1);
		}

		if (l > 2 && arr[1] > arr[2]) {
			swap(arr, 1, 2);
		}

		if (l > 1 && arr[0] > arr[1]) {
			swap(arr, 0, 1);
		}
	}

	private void swap(int[] arr, int a, int b) {
		int i = arr[a];
		arr[a] = arr[b];
		arr[b] = i;
	}
}
