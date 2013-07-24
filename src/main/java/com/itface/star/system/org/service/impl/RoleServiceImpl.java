package com.itface.star.system.org.service.impl;

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
import com.itface.star.system.org.model.Model;
import com.itface.star.system.org.model.Operation;
import com.itface.star.system.org.model.Role;
import com.itface.star.system.org.service.RoleService;
@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	private BaseDao<Role> dao;

	
	@Override
	public List<Role> findAll(){
		// TODO Auto-generated method stub
		List<Role> roles = dao.find("from Role t");
		return roles;
	}

	@Override
	public JSONObject findAllRoleJqgirdJson() {
		// TODO Auto-generated method stub
		List<Role> list = this.findAll();
		if(list!=null){
			JqgridDataJson<Role> jsonModel = new JqgridDataJson<Role>(list);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setExcludes(new String[]{"users","menus","operations","models","groups"});
			return JSONObject.fromObject(jsonModel,jsonConfig);
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void add(Role role,Long[] modelIds,Long[] menuIds,Long[] operationIds) {
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
			if(modelIds!=null&&modelIds.length>0){
				for(int i=0;i<modelIds.length;i++){
					Model model = new Model();
					model.setId(modelIds[i]);
					role.getModels().add(model);
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
	public void update(Role role,Long[] allmodelIds,Long[] allMenuIds,Long[] allOperationIds,Long[] checkedModelIds,Long[] checkedMenuIds,Long[] checkedOperationIds) {
		// TODO Auto-generated method stub
		if(role!=null){
			Role oldRole = this.find(role.getId());
			Set<Menu> oldMenus = oldRole.getMenus();
			Set<Operation> oldOperation = oldRole.getOperations();
			Set<Model> oldModels = oldRole.getModels();
			if(oldModels!=null&&oldModels.size()>0){
				for(int i=0;i<allmodelIds.length;i++){
					Model model = new Model();
					model.setId(allmodelIds[i]);
					if(oldModels.contains(model)){
						if(checkedModelIds!=null){
							boolean checked = false;
							for(int j=0;j<checkedModelIds.length;j++){
								if(model.getId()==checkedModelIds[j]){
									checked=true;
									break;
								}
							}
							if(!checked){
								oldModels.remove(model);
							}
						}else{
							oldModels.remove(model);
						}
					}else{
						if(checkedModelIds!=null){
							for(int j=0;j<checkedModelIds.length;j++){
								if(model.getId()==checkedModelIds[j]){
									oldModels.add(model);
									break;
								}
							}
						}
					}
				}
			}else if(checkedModelIds!=null){
				for(int j=0;j<checkedModelIds.length;j++){
					Model model = new Model();
					model.setId(checkedModelIds[j]);
					oldModels.add(model);
				}
			}
			if(oldMenus!=null&&oldMenus.size()>0){
				for(int i=0;i<allMenuIds.length;i++){
					Menu menu = new Menu();
					menu.setId(allMenuIds[i]);
					if(oldMenus.contains(menu)){
						if(checkedMenuIds!=null){
							boolean checked = false;
							for(int j=0;j<checkedMenuIds.length;j++){
								if(menu.getId()==checkedMenuIds[j]){
									checked=true;
									break;
								}
							}
							if(!checked){
								oldMenus.remove(menu);
							}
						}else{
							oldMenus.remove(menu);
						}
					}else{
						if(checkedMenuIds!=null){
							for(int j=0;j<checkedMenuIds.length;j++){
								if(menu.getId()==checkedMenuIds[j]){
									oldMenus.add(menu);
									break;
								}
							}
						}
					}
				}
			}else if(checkedMenuIds!=null){
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
						if(checkedOperationIds!=null){
							boolean checked = false;
							for(int j=0;j<checkedOperationIds.length;j++){
								if(op.getId()==checkedOperationIds[j]){
									checked=true;
									break;
								}
							}
							if(!checked){
								oldOperation.remove(op);
							}
						}else{
							oldOperation.remove(op);
						}
					}else{
						if(checkedOperationIds!=null){
							for(int j=0;j<checkedOperationIds.length;j++){
								if(op.getId()==checkedOperationIds[j]){
									oldOperation.add(op);
									break;
								}
							}
						}
					}
				}
			}else if(checkedOperationIds!=null){
				for(int j=0;j<checkedOperationIds.length;j++){
					Operation op = new Operation();
					op.setId(checkedOperationIds[j]);
					oldOperation.add(op);
				}
			}
			role.setModels(oldModels);
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
	public Role find(long id) {
		// TODO Auto-generated method stub
		return (Role)dao.find(Role.class, id);
	}
}
