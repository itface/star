package com.test.powermock;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ MyClass.class})
public class StaticClassSampleTest {

	@Test
	public void testPrivateMethod() throws Exception {
		// 模拟 private的方法
		MyClass spy = PowerMockito.spy(new MyClass());
		PowerMockito.doReturn(3).when(spy, "private_method", 1);
		Assert.assertEquals(3, spy.test_private_method(1));
		PowerMockito.verifyPrivate(spy, Mockito.times(1)).invoke("private_method", 1);
	}

	@Test
	public void testStaticReturnMethod() throws Exception {
		// 模拟 静态有返回值的方法
		PowerMockito.mockStatic(MyClass.class);
		Mockito.when(MyClass.static_return_method()).thenReturn(2);
		Assert.assertEquals(2, MyClass.static_return_method());
	}

	@Test
	public void testVoidMethod() throws Exception {
		// 模拟 不执行void的方法
		MyClass spy = PowerMockito.spy(new MyClass());
		PowerMockito.doNothing().when(spy).void_method();
		spy.void_method();
	}

	@Test
	public void testStaticMethod1() throws Exception {
		// 模拟 不执行没参数的静态void的方法
		PowerMockito.mockStatic(MyClass.class);
		PowerMockito.doNothing().when(MyClass.class, "static_void_method");
		MyClass.static_void_method();
	}

	@Test
	public void testStaticMethod2() throws Exception {
		// 模拟 不执行带参数的静态void的方法
		PowerMockito.mockStatic(MyClass.class);
		PowerMockito.doNothing().when(MyClass.class, "staticMethod", "123");
		MyClass.staticMethod("123");

		PowerMockito.doNothing().when(MyClass.class, "staticMethod", Mockito.anyString());
		MyClass.staticMethod("456");
	}

}

class MyClass {

	final private int private_method(int a) {
		return a;
	}

	public int test_private_method(int a) {
		return private_method(a);
	}

	public static int static_return_method() {
		return 1;
	}

	void void_method() {
		throw new IllegalStateException("should not go here");
	}

	public static void static_void_method() {
		throw new IllegalStateException("should not go here");
	}

	public static void staticMethod(String a) {
		throw new IllegalStateException(a);
	}
}

