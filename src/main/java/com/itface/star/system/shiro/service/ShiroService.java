package com.itface.star.system.shiro.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.itface.star.system.org.model.Menu_tree;
import com.itface.star.system.org.model.Operation;
import com.itface.star.system.org.model.Role;
import com.itface.star.system.org.model.User;
/**
 * 因为ShiroDbRealm直接注入其它service，不能开启service的事务，造成不能修改数据库，所以单独写一个service用于ShiroDbRealm。
 * 估计原因为，service都是单例，ShiroDbRealm是第一次调用这些service，因为方法不是public无法开启事务，造成后面调用这个service时也无法开启事务
 * @author Administrator
 *
 */
public interface ShiroService {

	public Set<Role> findAllRoles(String uid);
	public User findUserByUserid(String userid);
	public Map<Long,Menu_tree> findMenuTreeByUserid(String userid);
	public List<Role> findAllRoles();
	public List<Operation> findAllOperations();
	public List<Role> findAllRolesWithMenus();
}
