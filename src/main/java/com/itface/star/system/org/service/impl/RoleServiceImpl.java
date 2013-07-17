package com.itface.star.system.org.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
				for(int i=0;i<operationIds.length;i++){
					Operation op = new Operation();
					op.setId(operationIds[i]);
					role.getOperations().add(op);
				}
				
			}
			//前台传过来的选中的menuid包括了通过选择的操作所对应的菜单
			if(menuIds!=null&&menuIds.length>0){
				for(int i=0;i<menuIds.length;i++){
					Menu menu = new Menu();
					menu.setId(menuIds[i]);
					role.getMenus().add(menu);
				}
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
	public void update(Role role,Long[] allMenuIds,Long[] allOperationIds,Long[] checkedMenuIds,Long[] checkedOperationIds) {
		// TODO Auto-generated method stub
		if(role!=null){
			Role oldRole = this.find(role.getId());
			Set<Menu> oldMenus = oldRole.getMenus();
			Set<Operation> oldOperation = oldRole.getOperations();
			if(oldMenus!=null&&oldMenus.size()>0){
				for(int i=0;i<allMenuIds.length;i++){
					Menu menu = new Menu();
					menu.setId(allMenuIds[i]);
					if(oldMenus.contains(menu)){
						boolean checked = false;
						for(int j=0;j<checkedMenuIds.length;j++){
							if(allMenuIds[i]==checkedMenuIds[j]){
								checked=true;
								break;
							}
						}
						if(!checked){
							oldMenus.remove(menu);
						}
					}else{
						for(int j=0;j<checkedMenuIds.length;j++){
							if(allMenuIds[i]==checkedMenuIds[j]){
								oldMenus.add(menu);
								break;
							}
						}
					}
				}
			}else{
				for(int j=0;j<checkedMenuIds.length;j++){
					Menu menu = new Menu();
					menu.setId(checkedMenuIds[j]);
					oldMenus.add(menu);
				}
			}
			if(oldOperation!=null&&oldOperation.size()>0){
				for(int i=0;i<allOperationIds.length;i++){
					Operation op = new Operation();
					op.setId(allOperationIds[i]);
					if(oldOperation.contains(op)){
						boolean checked = false;
						for(int j=0;j<checkedOperationIds.length;j++){
							if(allOperationIds[i]==checkedOperationIds[j]){
								checked=true;
								break;
							}
						}
						if(!checked){
							oldOperation.remove(op);
						}
					}else{
						for(int j=0;j<checkedOperationIds.length;j++){
							if(allOperationIds[i]==checkedOperationIds[j]){
								oldOperation.add(op);
								break;
							}
						}
					}
				}
			}else{
				for(int j=0;j<checkedOperationIds.length;j++){
					Operation op = new Operation();
					op.setId(checkedOperationIds[j]);
					oldOperation.add(op);
				}
			}
			role.setMenus(oldMenus);
			role.setOperations(oldOperation);
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
		return (Role)dao.find(Role.class, id);
	}

	
	

	


}
