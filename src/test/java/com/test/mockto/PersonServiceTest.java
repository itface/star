package com.test.mockto;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


/**
 * 
 * Mockito 支持对变量进行注解，例如将mock 对象设为测试类的属性，然后通过
注解的方式@Mock 来定义它，这样有利于减少重复代码，增强可读性，易于排
查错误等。除了支持@Mock，Mockito支持的注解还有@Spy（监视真实的对象），
@Captor（参数捕获器），@InjectMocks（mock对象自动注入）。

只有Annotation还不够，要让它们工作起来还需要进行初始化工作。初始化的方
法为：MockitoAnnotations.initMocks(testClass)参数testClass是你所写
的测试类。一般情况下在Junit4的@Before 定义的方法中执行初始化工作.

除了上述的初始化的方法外，还可以使用Mockito 提供的Junit Runner：
MockitoJUnitRunner这样就省略了上面的步骤。
@RunWith(MockitoJUnit44Runner.class)
 *
 */
public class PersonServiceTest {
	@Mock
	private PersonDao personDAO;
	private PersonService personService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		personService = new PersonService(personDAO);
	}

	@Test
	public void shouldUpdatePersonName() {
		Person person = new Person(1, "Phillip");
		when(personDAO.fetchPerson(1)).thenReturn(person);
		boolean updated = personService.update(1, "David");
		assertTrue(updated);
		verify(personDAO).fetchPerson(1);
		ArgumentCaptor<Person> personCaptor = ArgumentCaptor.forClass(Person.class);
		verify(personDAO).update(personCaptor.capture());
		Person updatedPerson = personCaptor.getValue();
		assertEquals("David", updatedPerson.getPersonName());
		// asserts that during the test, there are no other calls to the mock
		// object.
		verifyNoMoreInteractions(personDAO);
	}

	@Test
	public void shouldNotUpdateIfPersonNotFound() {
		when(personDAO.fetchPerson(1)).thenReturn(null);
		boolean updated = personService.update(1, "David");
		assertFalse(updated);
		verify(personDAO).fetchPerson(1);
		verifyZeroInteractions(personDAO);
		verifyNoMoreInteractions(personDAO);
	}

} 
