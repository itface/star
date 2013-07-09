package com.itface.star.system.org.service.impl;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.itface.star.system.baseDao.BaseDao;
import com.itface.star.system.org.model.Menu;
import com.itface.star.system.org.model.Model;
import com.itface.star.system.org.model.User;
import com.itface.star.system.org.service.MenuService;
@Service
public class MenuServiceImpl implements MenuService{

	@Autowired
	private BaseDao<Menu> dao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Menu add(Menu menu) {
		// TODO Auto-generated method stub
		return dao.persist(menu);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Menu update(Menu menu) {
		// TODO Auto-generated method stub
		return dao.update(menu);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void remove(long id) {
		// TODO Auto-generated method stub
		dao.deleteById(Menu.class, id);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public JSONArray findByModelid(long modelid) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
