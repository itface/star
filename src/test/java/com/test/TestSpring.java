package com.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.itface.star.system.org.model.Organization;
import com.itface.star.system.org.model.User;
import com.itface.star.system.org.service.UserService;
/**
 * @RunWith：这个是指定使用的单元测试执行类，这里就指定的是SpringJUnit4ClassRunner.class；
@ContextConfiguration：这个指定Spring配置文件所在的路径，可以同时指定多个文件；
@TestExecutionListeners：这个用于指定在测试类执行之前，可以做的一些动作，如这里的DependencyInjectionTestExecutionListener.class，就可以对一测试类中的依赖进行注入，TransactionalTestExecutionListener.class用于对事务进行管理；这两个都是Srping自带的； 我们也可以实现自己的Listener类来完成我们自己的操作，只需要继续类org.springframework.test.context.support.AbstractTestExecutionListener就可以了，具体可以参照DependencyInjectionTestExecutionListener.class的实现，后面我会贴出实例。 Listener实在实现类执行之前被执行、实现类的测试方法之前被执行，这与Listener的实现有关。
@Transactional：这里的@Transactional不是必须的，这里是和@TestExecutionListeners中的TransactionalTestExecutionListener.class配合使用，用于保证插入的数据库中的测试数据，在测试完后，事务回滚，将插入的数据给删除掉，保证数据库的干净。如果没有显示的指定@Transactional，那么插入到数据库中的数据就是真实的插入了。
 * @author Administrator
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = { "classpath*:/applicationContext.xml"})  
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class })  
@Transactional  
public class TestSpring {

	@Autowired
	UserService userService;
	
	@Test
	@Transactional   //标明此方法需使用事务  
    @Rollback(true)  //标明使用完此方法后事务不回滚,true时为回滚 
	public void test(){
		User user=userService.findByUserid("admin");
		System.out.println(user.getId()+":"+user.getPassword());
		User junitUser = new User();
		junitUser.setDisplayorder(2);
		junitUser.setPassword("123");
		junitUser.setUserid("junit");
		junitUser.setUsername("测试用户");
		//Organization org = new Organization();
		//org.setId(1l);
		//junitUser.setOrganization(org);
		userService.add(1, junitUser, false, null);
	}
}
