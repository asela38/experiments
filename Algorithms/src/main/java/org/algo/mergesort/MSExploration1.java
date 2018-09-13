package org.algo.mergesort;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;

public class MSExploration1 {

	@Test
	public void testMergeSrort() throws Exception {
		sortVerify(new int[] { 1 }, new int[] { 1 });
		sortVerify(new int[] { 1, 2 }, new int[] { 1, 2 });
		sortVerify(new int[] { 1, 2, 3 }, new int[] { 1, 2, 3 });
		sortVerify(new int[] { 3, 2, 1 }, new int[] { 1, 2, 3 });

		int[] randomArr = ThreadLocalRandom.current().ints(1000, 0, 10_000).toArray(),
				sortedArr = Arrays.copyOf(randomArr, randomArr.length);
		Arrays.sort(sortedArr);

		sortVerify(randomArr, sortedArr);
	}

	private void sortVerify(int[] unsorted, int[] sorted) {
		assertThat(Arrays.toString(sort(unsorted)), is(Arrays.toString(sorted)));
	}

	private int[] sort(int[] unsorted) {
		int n = unsorted.length;
		if (n < 2)
			return unsorted;

		int[] C = new int[n];

		int[] A = sort(Arrays.copyOf(unsorted, n >> 1));
		int[] B = sort(Arrays.copyOfRange(unsorted, n >> 1, n));

		for (int si = 0, i1 = 0, i2 = 0; si < n; si++) {
			if (i1 >= A.length) {
				C[si] = B[i2++];
			} else if (i2 >= B.length) {
				C[si] = A[i1++];
			} else if (A[i1] < B[i2]) {
				C[si] = A[i1++];
			} else {
				C[si] = B[i2++];
			}
		}

		return C;
	}
}
