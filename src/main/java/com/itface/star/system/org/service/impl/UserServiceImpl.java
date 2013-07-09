package com.itface.star.system.org.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.itface.star.system.baseDao.BaseDao;
import com.itface.star.system.org.model.User;
import com.itface.star.system.org.service.UserService;
@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private BaseDao<User> dao;
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public User findByUserid(String userid) {
		// TODO Auto-generated method stub
		return dao.findSingleResult("from User t where t.userid=?1", new Object[]{userid});
	}

}
