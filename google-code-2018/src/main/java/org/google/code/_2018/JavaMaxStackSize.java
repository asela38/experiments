package org.google.code._2018;

public class JavaMaxStackSize {

	public static void main(String[] args) {
		call(1);
	}

	private static void call(int i) {
		System.out.println(i);
		call(++i);
	}
}
