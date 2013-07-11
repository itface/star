package com.itface.star.system.org.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.itface.star.system.baseDao.BaseDao;
import com.itface.star.system.org.model.Role;
import com.itface.star.system.org.service.RoleService;
@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	private BaseDao<Role> dao;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<Role> findAll(){
		// TODO Auto-generated method stub
		List<Role> roles = dao.find("from Role t");
		return roles;
	}
	

	


}
