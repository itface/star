package com.itface.star.system.org.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itface.star.system.baseDao.BaseDao;
import com.itface.star.system.easyui.TreeNode;
import com.itface.star.system.easyui.TreeNodeAttributes;
import com.itface.star.system.org.model.Group;
import com.itface.star.system.org.model.Role;
import com.itface.star.system.org.model.User;
import com.itface.star.system.org.service.GroupService;
@Service
public class GroupServiceImpl implements GroupService{

	@Autowired
	private BaseDao<Group> dao;
	
	
	@Override
	public Group find(long id) {
		// TODO Auto-generated method stub
		return dao.find(Group.class, id);
	}

	@Override
	@Transactional
	public Group add(boolean openRoleTreeFlag,String checkedUserids,String checkedRoleids,Group group) {
		// TODO Auto-generated method stub
		if(group!=null){
			if(openRoleTreeFlag&&checkedRoleids!=null&&!"".equals(checkedRoleids)){
				String[] ids = checkedRoleids.split(",");
				for(int i=0;i<ids.length;i++){
					Role role = new Role();
					role.setId(Long.parseLong(ids[i]));
					group.getRoles().add(role);
				}
			}
			if(checkedUserids!=null&&!"".equals(checkedUserids)){
				String[] ids = checkedUserids.split(",");
				for(int i=0;i<ids.length;i++){
					User user = new User();
					user.setId(Long.parseLong(ids[i]));
					group.getUsers().add(user);
				}
			}
			return dao.persist(group);
		}
		return null;
	}

	@Override
	@Transactional
	public Group update(boolean openRoleTreeFlag,String checkedUserids,String checkedRoleids,Group group) {
		// TODO Auto-generated method stub
		if(openRoleTreeFlag){
			if(checkedRoleids!=null&&!"".equals(checkedRoleids)){
				String[] ids = checkedRoleids.split(",");
				for(int i=0;i<ids.length;i++){
					Role role = new Role();
					role.setId(Long.parseLong(ids[i]));
					group.getRoles().add(role);
				}
			}
		}else{
			Group oldGroup = this.find(group.getId());
			if(oldGroup!=null){
				group.setRoles(oldGroup.getRoles());
			}
		}
		//group.getUsers().clear();
		if(checkedUserids!=null&&!"".equals(checkedUserids)){
			String[] ids = checkedUserids.split(",");
			for(int i=0;i<ids.length;i++){
				User user = new User();
				user.setId(Long.parseLong(ids[i]));
				group.getUsers().add(user);
			}
		}
		return dao.update(group);
	}

	@Override
	@Transactional
	public void remove(long id) {
		// TODO Auto-generated method stub
		dao.deleteById(Group.class, id);
	}

	@Override
	public JSONArray findSonJson(long id) {
		// TODO Auto-generated method stub
		List<Group> list = this.findSons(id);
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		if(list!=null){
			for(Group group : list){
				nodes.add(new TreeNode(group));
			}
			//Collections.sort(nodes);
		}
		return JSONArray.fromObject(nodes);
	}

	@Override
	public List<Group> findSons(long id) {
		// TODO Auto-generated method stub
		return dao.find("from Group t where t.parentid=?1", new Object[]{id});
	}

	@Override
	public List<Group> findSiblings(long id) {
		// TODO Auto-generated method stub
		return dao.find("select t2 from Group t1,Group t2 where t1.parentid=t2.parentid and t1.id=?1", new Object[]{id});
	}

	@Override
	public Group findParent(Group group) {
		// TODO Auto-generated method stub
		return dao.findSingleResult("from Group t where t.id=?1", new Object[]{group.getParentid()});
	}


}
