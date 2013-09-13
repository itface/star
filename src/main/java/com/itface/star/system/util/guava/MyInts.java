package com.itface.star.system.util.guava;

import com.google.common.primitives.Ints;

public class MyInts {

	public void test(){
		int[] array = { 1, 2, 3, 4, 5 };
		int[] array2 = { 6,7, 5 };
		int a = 4;
		boolean contains = Ints.contains(array, a);
		int indexOf = Ints.indexOf(array, a);
		int max = Ints.max(array);
		int min = Ints.min(array);
		int[] concat = Ints.concat(array, array2);
	}
}
