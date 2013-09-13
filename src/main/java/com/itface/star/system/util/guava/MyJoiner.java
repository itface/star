package com.itface.star.system.util.guava;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.primitives.Ints;

public class MyJoiner {

	public void test1(){
		int[] numbers = { 1, 2, 3, 4, 5 };  
		String numbersAsString = Joiner.on(";").join(Ints.asList(numbers)); 
	}
	public void test2(){
		int[] numbers = { 1, 2, 3, 4, 5 };  
		String numbersAsStringDirectly = Ints.join(";", numbers);
		Iterable split = Splitter.on(",").split(numbersAsStringDirectly);
	}
	public void test3(){
		String testString = "foo , what,,,more,";
		Iterable<String> split = Splitter.on(",").omitEmptyStrings().trimResults().split(testString);
	}
}
