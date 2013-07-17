package com.itface.star.system.org.service.impl;

import java.util.List;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.itface.star.system.baseDao.BaseDao;
import com.itface.star.system.jqgrid.JqgridDataJson;
import com.itface.star.system.org.model.Menu;
import com.itface.star.system.org.model.Operation;
import com.itface.star.system.org.service.MenuService;
import com.itface.star.system.org.service.OperationService;
@Service
public class OperationServiceImpl implements OperationService{

	@Autowired
	private BaseDao<Operation> dao;
	//@Autowired
	//private MenuService menuService;
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void add(long menuid, Operation operation) {
		// TODO Auto-generated method stub
		//Menu menu = menuService.find(menuid);
		Menu menu = new Menu();
		menu.setId(menuid);
		operation.setMenu(menu);
		dao.persist(operation);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(long menuid, Operation operation) {
		// TODO Auto-generated method stub
		//Menu menu = menuService.find(menuid);
		Menu menu = new Menu();
		menu.setId(menuid);
		operation.setMenu(menu);
		dao.update(operation);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void remove(long id) {
		// TODO Auto-generated method stub
		dao.deleteById(Operation.class, id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void removeList(long[] idArray) {
		// TODO Auto-generated method stub
		if(idArray!=null&&idArray.length>0){
			for(int i=0;i<idArray.length;i++){
				this.remove(idArray[i]);
			}
		}
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public JSONObject findOperationJqgirdJsonByMenuid(long menuid) {
		// TODO Auto-generated method stub
		List<Operation> list = this.findOperationByMenuid(menuid);
		if(list!=null){
			JqgridDataJson<Operation> jsonModel = new JqgridDataJson<Operation>(list);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setExcludes(new String[]{"menu","operations"});
			return JSONObject.fromObject(jsonModel,jsonConfig);
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Operation> findOperationByMenuid(long menuid) {
		// TODO Auto-generated method stub
		return dao.find("from Operation t where t.menu.id=?1 order by t.name asc", new Object[]{menuid});
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Operation find(long id) {
		// TODO Auto-generated method stub
		return dao.find(Operation.class, id);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Operation> findOperationByIds(Long[] ids) {
		// TODO Auto-generated method stub
		return dao.find("from Operation t where t.id in (:ids)",ids);
	}


}
