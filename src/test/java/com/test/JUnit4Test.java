package com.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 * 有一个类是负责对大文件（超过 500 兆）进行读写，他的每一个方法都是对文件进行操作。换句话说，在调用每一个方法之前，我们都要打开一个大文件并读入文件内容，这绝对是一个非常耗费时间的操作。如果我们使用 @Before 和 @After ，那么每次测试都要读取一次文件，效率及其低下。这里我们所希望的是在所有测试一开始读一次文件，所有测试结束之后释放文件，而不是每次测试都读文件。 JUnit 的作者显然也考虑到了这个问题，它给出了 @BeforeClass 和 @AfterClass 两个 Fixture 来帮我们实现这个功能。从名字上就可以看出，用这两个 Fixture 标注的函数，只在测试用例初始化时执行 @BeforeClass 方法，当所有测试执行完毕之后，执行 @AfterClass 进行收尾工作。在这里要注意一下，每个测试类只能有一个方法被标注为 @BeforeClass 或 @AfterClass ，并且该方法必须是 Public 和 Static 的。
 * @author Administrator
 *
 */
public class JUnit4Test {

	private int count=1;
	@Before
	public void before() {

		System.out.println("@Before");

	}

	@Test
	public void test() {

		System.out.println("@Test"+count++);

		Assert.assertEquals(5 + 5, 10);

	}

	@Ignore
	@Test
	public void testIgnore() {

		System.out.println("@Ignore");

	}

	@Test(timeout = 50)
	public void testTimeout() {
		System.out.println("@Test(timeout = 50)"+count++);
		Assert.assertEquals(5 + 5, 10);

	}

	@Test(expected = ArithmeticException.class)
	public void testExpected() {

		System.out.println("@Test(expected = Exception.class)"+count++);

		throw new ArithmeticException();

	}

	@After
	public void after() {

		System.out.println("@After");

	}

	@BeforeClass
	public static void beforeClass() {

		System.out.println("@BeforeClass");

	}

	@AfterClass
	public static void afterClass() {

		System.out.println("@AfterClass");

	}
}
