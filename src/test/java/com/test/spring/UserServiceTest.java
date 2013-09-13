package com.test.spring;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;

import com.itface.star.system.baseDao.BaseDao;
import com.itface.star.system.org.model.User;
import com.itface.star.system.org.service.UserService;
import com.itface.star.system.org.service.impl.UserServiceImpl;

@RunWith(MockitoJUnitRunner.class)  
@ContextConfiguration(locations = { "classpath*:applicationContext.xml" })  
public class UserServiceTest {

	/**
	 * @InjectMocks creates an instance of the class and injects the mocks that are created with the @Mock (or @Spy) annotations into this instance.
	 */
	@InjectMocks  
	private UserServiceImpl userService = new UserServiceImpl(); 
	
	@Mock
	private BaseDao<User> dao;

	//使用了@RunWith(MockitoJUnitRunner.class) 不用MockitoAnnotations.initMocks(this);
//	@Before  
//	public void myBefore() {  
//		MockitoAnnotations.initMocks(this);  
//	}  
	@Test
	public void test(){
		User user = new User();
		user.setId(1);
		user.setUserid("admin");
		user.setPassword("123");
		Mockito.when(dao.findSingleResult("from User t where t.userid=?1",new Object[]{"USER"})).thenReturn(user);  
		User user2=userService.findByUserid("USER");
		System.out.println(user2.getId()+":"+user2.getPassword());
		Assert.assertEquals("admin", user2.getUserid());  
	}
	
	
}
