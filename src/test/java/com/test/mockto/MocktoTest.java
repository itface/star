package com.test.mockto;

/*
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
*/
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.spy;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
/**
 * 
void方法的模拟不支持when(mock.someMethod()).thenReturn(value)这样的
语法，只支持下面的方式：
doNothing() 模拟不做任何返回（mock对象void方法的默认返回）
doNothing().when(i).remove();
doThrow(Throwable) 模拟返回异常
doThrow(new RuntimeException()).when(i).remove();
迭代风格
doNothing().doThrow(new RuntimeException()).when(i).remove();
第一次调用remove方法什么都不做，第二次调用抛出RuntimeException异常。


验证方法调用的顺序
Mockito 同样支持对不同Mock 对象不同方法的调用次序进行验证。进行次序验
证是，我们需要创建InOrder对象来进行支持。例：
创建 mock对象
List<String> firstMock = mock(List.class);
List<String> secondMock = mock(List.class);
调用mock对象方法
firstMock.add("was called first");
firstMock.add("was called first");
secondMock.add("was called second");
secondMock.add("was called third");
创建InOrder 对象
inOrder方法可以传入多个mock对象作为参数，这样便可对这些mock对象的方
法进行调用顺序的验证InOrder inOrder = inOrder( secondMock,
firstMock );
验证方法调用
接下来我们要调用InOrder对象的verify方法对mock方法的调用顺序进行验证。
注意，这里必须是你对调用顺序的预期。
InOrder对象的verify方法也支持调用次数验证，上例中，我们期望
firstMock.add("was called first")方法先执行并执行两次，所以进行了下
面的验证inOrder.verify(firstMock,times(2)).add("was called first")。
其次执行了secondMock.add("was called second")方法，继续验证此方法的
执行inOrder.verify(secondMock).add("was called second")。如果mock
方法的调用顺序和InOrder中verify的顺序不同，那么测试将执行失败。
 * @author Administrator
 *
 */
public class MocktoTest {

	@Test
	public void simpleTest(){
		//mock接口或者类
		Iterator i=mock(Iterator.class);
		when(i.next()).thenReturn("Hello").thenReturn("World");
		//act
		String result=i.next()+" "+i.next();
		//verify
		verify(i, times(2)).next();
		//assert
		assertEquals("Hello World", result);
	}
	/**
	 * Argument Matcher（参数匹配器）
	 */
	@Test
	public void argumentMatchersTest(){
		List<String> mock = mock(List.class);
		when(mock.get(anyInt())).thenReturn("Hello").thenReturn("World");
		String result=mock.get(100)+" "+mock.get(200);
		verify(mock,times(2)).get(anyInt());
		assertEquals("Hello World",result);
	}
	/**
	 * Argument Matcher（参数匹配器）
	 */
	@Test
	public void argumentMatchersTest2(){
		Map mapMock = mock(Map.class);
		when(mapMock.put(anyInt(), anyString())).thenReturn("world");
		mapMock.put(1, "hello");
		verify(mapMock).put(anyInt(), eq("hello"));
	}
	/**
	 * Mock对象的行为验证
	 */
	@Test
	public void verifyTestTest() {
		List<String> mock = mock(List.class);
		List<String> mock2 = mock(List.class);
		when(mock.get(0)).thenReturn("hello");
		mock.get(0);
		mock.get(1);
		mock.get(2);
		mock2.get(0);
		verify(mock).get(2);
		/**
		 * Mockito除了提供times(N)方法供我们调用外，还提供了很多可选的方法： 
			never() 没有被调用，相当于times(0) 
			atLeast(N) 至少被调用N次 
			atLeastOnce() 相当于atLeast(1) 
			atMost(N) 最多被调用N次 
			
			
			验证someMethod()是否能在指定的100毫秒中执行完毕
			verify(mock, timeout(100)).someMethod();
			在给定的时间内完成执行次数
			verify(mock, timeout(100).times(2)).someMethod();
			给定的时间内至少执行两次
			verify(mock, timeout(100).atLeast(2)).someMethod();
		 */
		verify(mock, never()).get(3);
		/**
		 * verifyNoMoreInteractions()方法可以传入多个mock对象作为参数，用来验证传入的这些mock对象是否存在没有验证过的调用方法。本例中传入参数mock，测试将不会通过，因为我们只verify了mock对象的get(2)方法，没有对get(0)和get(1)进行验证。为了增加测试的可维护性，官方不推荐我们过于频繁的在每个测试方法中都使用它，因为它只是测试的一个工具，只在你认为有必要的时候才用。 
		 */
		verifyNoMoreInteractions(mock);
		/**
		 * 查询没有交互的mock对象 
verifyZeroInteractions()也是一个测试工具，源码和verifyNoMoreInteractions()的实现是一样的，为了提高逻辑的可读性，所以只不过名字不同。在例子中，它的目的是用来确认mock2对象没有进行任何交互，但mock2执行了get(0)方法，所以这里测试会报错。由于它和verifyNoMoreInteractions()方法实现的源码都一样，因此如果在verifyZeroInteractions(mock2)执行之前对mock.get(0)进行了验证那么测试将会通过。 
		 */
		verifyZeroInteractions(mock2);
	}
	
	
	
	
	
	
	
	
	
	/**
	 * 自定义参数匹配器
	 */
	@Test
	public void argumentMatchersTest3(){
		List mock = mock(List.class);
		when(mock.addAll(argThat(new IsListOfTwoElements()))).thenReturn(true);
		mock.addAll(Arrays.asList("one", "two", "three"));
		verify(mock).addAll(argThat(new IsListOfTwoElements()));
	}
	
	
	
	/**
	 * 利用ArgumentCaptor（参数捕获器）捕获方法参数进行验证
	 * 
	 * 	argument.capture() 捕获方法参数
		argument.getValue() 获取方法参数值，如果方法进行了多次调用，它将返回最后一个参数值
		argument.getAllValues() 方法进行多次调用后，返回多个参数值
	 */
	@Test
	public void argumentCaptorTest() { 
		List mock = mock(List.class); 
		List mock2 = mock(List.class); 
		mock.add("John"); 
		mock2.add("Brian"); 
		mock2.add("Jim");      
		ArgumentCaptor argument = ArgumentCaptor.forClass(String.class);      
		verify(mock).add(argument.capture()); 
		assertEquals("John", argument.getValue());      
		verify(mock2, times(2)).add(argument.capture());  
		assertEquals("Jim", argument.getValue()); 
		assertArrayEquals(new Object[]{"Brian","Jim"},argument.getAllValues().toArray());
	}
	
	
	
	
	/**
	 * Spy-对象的监视
	 * Mock 对象只能调用stubbed 方法，调用不了它真实的方法。但Mockito 可以监
视一个真实的对象，这时对它进行方法调用时它将调用真实的方法，同时也可以
stubbing 这个对象的方法让它返回我们的期望值。另外不论是否是真实的方法调
用都可以进行verify验证。和创建mock对象一样，对于final类、匿名类和Java
的基本类型是无法进行spy的。
	 * 
	 * 
	 * 
	 */
	@Test
	public void spyTest2() {
		List list = new LinkedList();
		List spy = spy(list);
		//optionally, you can stub out some methods:
		when(spy.size()).thenReturn(100);
		//using the spy calls real methods
		spy.add("one");
		spy.add("two");
		//prints "one" - the first element of a list
		System.out.println(spy.get(0));
		//size() method was stubbed - 100 is printed
		System.out.println(spy.size());
		//optionally, you can verify
		verify(spy).add("one");
		verify(spy).add("two");
	}
}
/**
 * 自定义的参数匹配器是匹配size大小为2 的List
 * @author Administrator
 *
 */
class IsListOfTwoElements extends ArgumentMatcher<List> {
	public boolean matches(Object list) {
		return ((List) list).size() == 2;
	}
}
