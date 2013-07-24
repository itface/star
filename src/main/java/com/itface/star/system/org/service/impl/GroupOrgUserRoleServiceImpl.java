package com.itface.star.system.org.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itface.star.system.easyui.CheckedTreeNode;
import com.itface.star.system.easyui.TreeNode;
import com.itface.star.system.easyui.TreeNodeAttributes;
import com.itface.star.system.org.model.Group;
import com.itface.star.system.org.model.Menu;
import com.itface.star.system.org.model.Menu_tree;
import com.itface.star.system.org.model.Model;
import com.itface.star.system.org.model.Operation;
import com.itface.star.system.org.model.Organization;
import com.itface.star.system.org.model.Role;
import com.itface.star.system.org.model.User;
import com.itface.star.system.org.service.GroupOrgUserRoleService;
import com.itface.star.system.org.service.GroupService;
import com.itface.star.system.org.service.OrganizationService;
import com.itface.star.system.org.service.RoleService;
import com.itface.star.system.org.service.UserService;
@Service
public class GroupOrgUserRoleServiceImpl implements GroupOrgUserRoleService{

	@Autowired
	private OrganizationService orgService;
	@Autowired
	private UserService userService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private RoleService roleService;
	


	@Override
	public JSONArray findSubCheckedTreeNodeJsonOfOrgAndUserByOrgidAndGroupId(long orgid,long groupid) {
		// TODO Auto-generated method stub
		List<User> users = userService.findAllUserByOrgid(orgid);
		List<Organization> orgs = orgService.findSons(orgid);
		List<CheckedTreeNode> nodes = new ArrayList<CheckedTreeNode>();
		if(orgs!=null&&orgs.size()>0){
			for(Organization org : orgs){
				nodes.add(new CheckedTreeNode(org));
			}
		}
		if(users!=null&&users.size()>0){
			Group group = groupService.find(groupid);
			Set<User> userSet= null;
			if(group!=null){
				userSet = group.getUsers();
			}
			for(User user : users){
				nodes.add(new CheckedTreeNode(user,userSet));
			}
		}
		return JSONArray.fromObject(nodes);
	}
	@Override
	public JSONArray findSubCheckedTreeNodeJsonOfRoleByOrgid(long groupid) {
		// TODO Auto-generated method stub
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		if(groupid!=-1){
			List<Role> roles = roleService.findAll();
			if(roles!=null&&roles.size()>0){
				Group group = groupService.find(groupid);
				Set<Role> groupRoles = null;
				if(group!=null){
					groupRoles = group.getRoles();
				}
				for(Role role : roles){
					nodes.add(new TreeNode(role,groupRoles));
				}
			}
		}else{
			TreeNode node = new TreeNode();
			node.setId(TreeNodeAttributes.NODETYPE_ROLE+"root");
			node.setText("角色");
			node.setState("closed");
			nodes.add(node);
		}
		return JSONArray.fromObject(nodes);
	}
	@Override
	public Set<Role> findAllRoles(String uid) {
		// TODO Auto-generated method stub
		Set<Role> allRoles = new HashSet<Role>();
		User user = userService.findByUserid(uid);
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
				this.getGroupRoles(groupRoles,groupService.findParent(group));
			}
		}else{
			return;
		}
	}
	@Override
	public JSONArray findSubGroupTreeJsonByUseridAndParentGroupid(long userid,long parentGroupId) {
		// TODO Auto-generated method stub
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		Set<Group> groups = this.findAllGroupByUserid(userid);
		if(groups!=null&&groups.size()>0){
			List<Group> sons = groupService.findSons(parentGroupId);
			if(sons!=null&&sons.size()>0){
				for(Group g : sons){
					if(groups.contains(g)){
						TreeNode node = new TreeNode(g);
						nodes.add(node);
					}
				}
			}
			
		}
		return JSONArray.fromObject(nodes);
	}
	@Override
	public Set<Group> findAllGroupByUserid(long userid){
		Set<Group> allGroups = new HashSet<Group>();
		User user = userService.find(userid);
		if(user!=null){
			Set<Group> groups = user.getGroups();
			if(groups!=null&&groups.size()>0){
				for(Group group : groups){
					this.getFullPathOfGroup(group, allGroups);
				}
			}
		}
		return allGroups;
	}
	private void getFullPathOfGroup(Group group,Set<Group> allGroups){
		if(group!=null){
			allGroups.add(group);
			Group g = groupService.findParent(group);
			if(g!=null){
				this.getFullPathOfGroup(g, allGroups);
			}
		}
	}
	@Override
	public JSONArray findRoleTreeJsonOfUser(long userid) {
		// TODO Auto-generated method stub
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		if(userid!=-1){
			List<Role> roles = roleService.findAll();
			if(roles!=null&&roles.size()>0){
				User user = userService.find(userid);
				Set<Role> userRoles = null;
				if(user!=null){
					userRoles = user.getRoles();
				}
				for(Role role : roles){
					nodes.add(new TreeNode(role,userRoles));
				}
				//Collections.sort(nodes);
			}
		}else{
			TreeNode node = new TreeNode();
			node.setId(TreeNodeAttributes.NODETYPE_ROLE+"root");
			node.setText("角色");
			node.setState("closed");
			nodes.add(node);
		}
		return JSONArray.fromObject(nodes);
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
	@Override
	public JSONArray findSubTreeNodeJsonOfModelAndMenuAndOperationByUserid(String userid, long parentModelid) {
		// TODO Auto-generated method stub
		Map<Long, Menu_tree> treeMap = this.findMenuTreeByUserid(userid);
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		if(treeMap!=null&&treeMap.containsKey(parentModelid)){
			Menu_tree tree = treeMap.get(parentModelid);
			Set<Model> models = tree.getModels();
			Set<Menu> menus = tree.getMenus();
			Set<Operation> ops = tree.getOperations();
			List<Model> modelList = new ArrayList<Model>();
			modelList.addAll(models);
			Collections.sort(modelList);
			for(Model m : modelList){
				nodes.add(new TreeNode(m));
			}
			List<Menu> menuList = new ArrayList<Menu>();
			menuList.addAll(menus);
			Collections.sort(menuList);
			for(Menu m : menuList){
				nodes.add(new TreeNode(m,ops));
			}
		}
		return JSONArray.fromObject(nodes);
	}
}
