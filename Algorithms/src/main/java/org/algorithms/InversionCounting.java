package org.algorithms;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.function.Consumer;

import org.junit.Test;

public class InversionCounting {

	@Test
	public void testInversions() throws Exception {
		assertThat(inversions("1").f, is(0));
	}

	@Test
	public void testInversionsInTwo() throws Exception {
		assertThat(inversions("1,2").f, is(0));
		Consumer<int[]> print = a -> System.out.println(Arrays.toString(a));
		print.accept(inversions("2,1").s);
		assertThat(inversions("2,1").f, is(1));
		assertThat(inversions("1,3,5,2,4,6").f, is(3));
		assertThat(inversions("6,5,4,3,2,1").f, is(15));
	}

	private Pair<Integer, int[]> inversions(String string) {
		return inversions(Arrays.stream(string.split(",")).mapToInt(Integer::parseInt).toArray());
	}

	private static class Pair<F, S> {
		F f;
		S s;

		static <FT, ST> Pair<FT, ST> of(FT f, ST s) {
			Pair<FT, ST> pair = new Pair<>();
			pair.f = f;
			pair.s = s;
			return pair;
		}
	}

	private Pair<Integer, int[]> inversions(int[] array) {
		if (array.length < 2)
			return Pair.of(0, array);
		int[] a1 = Arrays.copyOfRange(array, 0, array.length / 2);
		int[] a2 = Arrays.copyOfRange(array, array.length / 2, array.length);
		Pair<Integer, int[]> ia1 = inversions(a1), ia2 = inversions(a2), isi = splitInversions(a1, a2);
		return Pair.of(ia1.f + ia2.f + isi.f, isi.s);
	}

	private Pair<Integer, int[]> splitInversions(int[] a1, int[] a2) {
		int[] merged = new int[a1.length + a2.length];
		int c = 0;
		for (int k = 0, i = 0, j = 0; k < merged.length; k++) {
			if (i >= a1.length) {
				merged[k] = a2[j];
			} else if (j >= a2.length) {
				merged[k] = a1[i];
			} else if (a1[i] < a2[j]) {
				merged[k] = a1[i];
				i++;
			} else {
				c += a1.length - i;
				merged[k] = a2[j];
				j++;
			}
		}
		return Pair.of(c, merged);
	}

}
