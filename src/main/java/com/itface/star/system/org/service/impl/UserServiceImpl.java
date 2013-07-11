package com.itface.star.system.org.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.itface.star.system.baseDao.BaseDao;
import com.itface.star.system.org.model.Role;
import com.itface.star.system.org.model.User;
import com.itface.star.system.org.service.RoleService;
import com.itface.star.system.org.service.UserService;
@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private BaseDao<User> dao;
	@Autowired
	private RoleService roleService;
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public User findByUserid(String userid) {
		// TODO Auto-generated method stub
		User user = dao.findSingleResult("from User t where t.userid=?1", new Object[]{userid});
		if("admin".equals(userid)){
			List<Role> roles = roleService.findAll();
			user.getRoles().addAll(roles);
		}
		return user;
	}

}
