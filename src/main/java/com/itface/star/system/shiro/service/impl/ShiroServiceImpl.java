package com.itface.star.system.shiro.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itface.star.system.baseDao.BaseDao;
import com.itface.star.system.org.model.Group;
import com.itface.star.system.org.model.Menu_tree;
import com.itface.star.system.org.model.Operation;
import com.itface.star.system.org.model.Role;
import com.itface.star.system.org.model.User;
import com.itface.star.system.shiro.service.ShiroService;
@Service
public class ShiroServiceImpl implements ShiroService{

	@Autowired
    private BaseDao dao;

	@Override
	public User findUserByUserid(String userid) {
		// TODO Auto-generated method stub
		User user = (User)dao.findSingleResult("from User t where t.userid=?1", new Object[]{userid});
		return user;
	}

	@Override
	public List<Role> findAllRoles() {
		// TODO Auto-generated method stub
		List<Role> roles = dao.find("from Role t");
		return roles;
	}

	@Override
	public Set<Role> findAllRoles(String uid) {
		// TODO Auto-generated method stub
		Set<Role> allRoles = new HashSet<Role>();
		User user = this.findUserByUserid(uid);
		if(user!=null){
			Set<Role> userRoles = user.getRoles();
			Set<Group> groups = user.getGroups();
			Set<Role> groupRoles = new HashSet<Role>();
			if(groups!=null&&groups.size()>0){
				for(Group group : groups){
					this.getGroupRoles(groupRoles, group);
				}
			}
			if(userRoles!=null&&userRoles.size()>0){
				allRoles.addAll(userRoles);
			}
			if(groupRoles!=null&&groupRoles.size()>0){
				allRoles.addAll(groupRoles);
			}
		}
		return allRoles;
	}
	private void getGroupRoles(Set<Role> groupRoles,Group group){
		if(group!=null){
			Set<Role> roles = group.getRoles();
			if(roles!=null&&roles.size()>0){
				groupRoles.addAll(roles);
			}
			if(group.getParentid()>0){
				this.getGroupRoles(groupRoles,this.findParent(group));
			}
		}else{
			return;
		}
	}
	private Group findParent(Group group) {
		// TODO Auto-generated method stub
		Object parent = dao.findSingleResult("from Group t where t.id=?1", new Object[]{group.getParentid()});
		return parent==null?null:(Group)parent;
	}

	@Override
	public List<Operation> findAllOperations() {
		// TODO Auto-generated method stub
		List<Operation> operations = dao.find("from Operation o");
		return operations;
	}

	@Override
	public List<Role> findAllRolesWithMenus() {
		// TODO Auto-generated method stub
		List<Role> roles = dao.find("from Role r left join fetch r.menus");
		return roles;
	}

	@Override
	public Map<Long, Menu_tree> findMenuTreeByUserid(String userid) {
		// TODO Auto-generated method stub
		Set<Role> roles = this.findAllRoles(userid);
		Map<Long,Menu_tree> map = new HashMap<Long,Menu_tree>();
    	if(roles!=null&&roles.size()>0){
    		Iterator<Role> it = roles.iterator();
    		while(it.hasNext()){
    			Role role = it.next();
    			Map<Long,Menu_tree> menuNode = role.findMenuTree();
    			Iterator<Long> itt = menuNode.keySet().iterator();
    			while(itt.hasNext()){
    				long key = itt.next();
    				if(map.containsKey(key)){
    					Menu_tree mapTree = map.get(key);
    					mapTree.getModels().addAll(menuNode.get(key).getModels());
    					mapTree.getMenus().addAll(menuNode.get(key).getMenus());
    					mapTree.getOperations().addAll(menuNode.get(key).getOperations());
    				}else{
    					map.put(key, menuNode.get(key));
    				}
    			}
    		}
    	}
    	return map;
	}
}
