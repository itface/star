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
import com.itface.star.system.org.model.Role;
import com.itface.star.system.org.service.MenuService;
import com.itface.star.system.org.service.OperationService;
import com.itface.star.system.org.service.RoleService;
@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	private BaseDao<Role> dao;

	@Autowired
	private MenuService menuService;
	@Autowired
	private OperationService operationService;
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<Role> findAll(){
		// TODO Auto-generated method stub
		List<Role> roles = dao.find("from Role t");
		return roles;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public JSONObject findAllRoleJqgirdJson() {
		// TODO Auto-generated method stub
		List<Role> list = this.findAll();
		if(list!=null){
			JqgridDataJson<Role> jsonModel = new JqgridDataJson<Role>(list);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setExcludes(new String[]{"users","menus","operations"});
			return JSONObject.fromObject(jsonModel,jsonConfig);
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void add(Role role,Long[] menuIds,Long[] operationIds) {
		// TODO Auto-generated method stub
		if(role!=null){
			if(operationIds!=null&&operationIds.length>0){
				List<Operation> ops = operationService.findOperationByIds(operationIds);
				if(ops!=null&&ops.size()>0){
					role.getOperations().addAll(ops);
					for(Operation op : ops){
						Menu menu = op.getMenu();
						role.getMenus().add(menu);
					}
				}
			}
			if(menuIds!=null&&menuIds.length>0){
				role.getMenus().addAll(menuService.findMenuByIds(menuIds));
			}
			dao.persist(role);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void remove(long id) {
		// TODO Auto-generated method stub
		dao.deleteById(Role.class, id);
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(Role role,Long[] menuIds,Long[] operationIds) {
		// TODO Auto-generated method stub
		if(role!=null){
			if(operationIds!=null&&operationIds.length>0){
				List<Operation> ops = operationService.findOperationByIds(operationIds);
				if(ops!=null&&ops.size()>0){
					role.getOperations().addAll(ops);
					for(Operation op : ops){
						Menu menu = op.getMenu();
						role.getMenus().add(menu);
					}
				}
			}
			if(menuIds!=null&&menuIds.length>0){
				role.getMenus().addAll(menuService.findMenuByIds(menuIds));
			}
			dao.update(role);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(long[] idArray) {
		// TODO Auto-generated method stub
		if(idArray!=null&&idArray.length>0){
			for(int i=0;i<idArray.length;i++){
				this.remove(idArray[i]);
			}
		}
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Role find(long id) {
		// TODO Auto-generated method stub
		return dao.find(Role.class, id);
	}

	
	

	


}
