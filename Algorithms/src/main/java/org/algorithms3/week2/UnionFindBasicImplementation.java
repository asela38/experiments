package org.algorithms3.week2;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Test;

public class UnionFindBasicImplementation {

	@Test
	public void testFind() throws Exception {
		int n = 10;
		int[] parents = new int[10];
		for (int i = 0; i < n; i++)
			parents[i] = i;

		for (int i = 0; i < n; i++)
			assertThat(find(i, parents), is(i));

		parents[4] = 5;

		for (int i = 0; i < n; i++) {
			if (i == 4)
				assertThat(find(4, parents), is(5));
			else
				assertThat(find(i, parents), is(i));
		}
	}

	private int find(int i, int[] parents) {
		int parent = i;
		while (parent != parents[parent]) {
			parent = parents[parent];
		}
		return parent;
	}

	@Test
	public void testUnion() throws Exception {
		int n = 10;
		int[] parents = new int[10];
		for (int i = 0; i < n; i++)
			parents[i] = i;

		unionVerify(parents, 4, 4);
		unionVerify(parents, 5, 4);
		unionVerify(parents, 1, 2);
		unionVerify(parents, 1, 0);
		unionVerify(parents, 4, 6);
		unionVerify(parents, 0, 6);
	}

	private void unionVerify(int[] parents, int u, int v) {
		union(u, v, parents);
		System.out.println(Arrays.toString(parents));
		assertThat(find(u, parents), is(find(v, parents)));
	}

	private void union(int u, int v, int[] parents) {
		int p1 = find(v, parents);
		int p2 = find(u, parents);
		if (p1 == p2)
			return;

		for (int i = 0; i < parents.length; i++) {
			if (find(i, parents) == p1 || parents[i] == p1) {
				parents[i] = p2;
			}
		}

	}

	@Test
	public void testLazyUnion() throws Exception {
		int n = 10;
		int[] parents = new int[10];
		for (int i = 0; i < n; i++)
			parents[i] = i;

		unionLazyVerify(parents, 4, 4);
		unionLazyVerify(parents, 5, 4);
		unionLazyVerify(parents, 1, 2);
		unionLazyVerify(parents, 1, 0);
		unionLazyVerify(parents, 4, 6);
		unionLazyVerify(parents, 0, 6);
	}

	private void unionLazyVerify(int[] parents, int u, int v) {
		lazyUnion(u, v, parents);
		System.out.println(Arrays.toString(parents));
		assertThat(find(u, parents), is(find(v, parents)));
	}

	private void lazyUnion(int u, int v, int[] parents) {
		int p1 = find(v, parents);
		int p2 = find(u, parents);
		if (p1 == p2)
			return;

		parents[p1] = p2;

	}
}
