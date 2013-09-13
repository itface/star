package com.test;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class SquareTest {
	private int param;
	private int param2;
	private int result;
	
	@Parameters 
	public static Collection data() {
		return Arrays.asList(new Object[][]{ { 2, 4, 6 }, { 0, 0, 0 }, { -3, 9, 7 } });
	}
	 // 构造函数，对变量进行初始化
	public SquareTest(int param, int param2, int result) {
		this.param = param;
		this.param2 = param2;
		this.result = result;
	}

	@Test 
	public void run() {
	//do some thing use args, and assert it
		int expected = param + param2;
		Assert.assertEquals(expected, result);
	}
	@Ignore("lala")
	public void lala() {
		Assert.assertEquals(3,3);
	}
}
