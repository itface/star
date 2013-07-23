package com.itface.star.system.org.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itface.star.system.easyui.CheckedTreeNode;
import com.itface.star.system.easyui.TreeNode;
import com.itface.star.system.easyui.TreeNodeAttributes;
import com.itface.star.system.org.model.Group;
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
	public JSONArray findSubCheckedTreeNodeJsonOfOrgAndUserByOrgid(long orgid,long groupid) {
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
	public JSONArray groupRoleTreeJson(long groupid) {
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
}
