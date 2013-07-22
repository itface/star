package com.itface.star.system.org.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.itface.star.system.easyui.CheckedTreeNodeOfMoelAndMenuAndOperation;
import com.itface.star.system.easyui.TreeNode;
import com.itface.star.system.org.model.Menu;
import com.itface.star.system.org.model.Menu_tree;
import com.itface.star.system.org.model.Model;
import com.itface.star.system.org.model.Operation;
import com.itface.star.system.org.model.Role;
import com.itface.star.system.org.service.MenuService;
import com.itface.star.system.org.service.ModelService;
import com.itface.star.system.org.service.ResourceService;
import com.itface.star.system.org.service.RoleService;
@Service
public class ResourceServiceImpl implements ResourceService{

	@Autowired
	private ModelService modelService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private MenuService menuService;
	
	
	@Override
	public JSONArray findSubTreeNodesOfModelAndMenuWithAuthByModelid(String userid,long modelid,Map<Long,Menu_tree> treeMap) {
		// TODO Auto-generated method stub
		if("admin".equals(userid)){
			return this.findSubTreeNodeJsonOfModelAndMenuWithoutAuthByModelid(modelid);
		}else if(treeMap!=null&&treeMap.size()>0&&treeMap.containsKey(modelid)){
			List<TreeNode> nodes = new ArrayList<TreeNode>();
			if(treeMap!=null&&treeMap.containsKey(modelid)){
				Menu_tree tree = treeMap.get(modelid);
				Set<Model> models = tree.getModels();
				Set<Menu> menus = tree.getMenus();
				for(Model m : models){
					nodes.add(new TreeNode(m));
				}
				for(Menu m : menus){
					nodes.add(new TreeNode(m));
				}
			}
			return JSONArray.fromObject(nodes);
		}else{
			return null;
		}
	}

	@Override
	public JSONArray findSubTreeNodeJsonOfModelAndMenuWithoutAuthByModelid(long modelid) {
		// TODO Auto-generated method stub
		return JSONArray.fromObject(this.findSubTreeNodeOfModelAndMenuWithoutAuthByModelid(modelid));
	}
	@Override
	public List<TreeNode> findSubTreeNodeOfModelAndMenuWithoutAuthByModelid(long modelid) {
		// TODO Auto-generated method stub
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		List<Model> modelList =modelService.findSons(modelid);
		List<Menu> menuList = menuService.findAllMenuByModelid(modelid);
		if(modelList!=null){
			for(Model model : modelList){
				nodes.add(new TreeNode(model));
			}
		}
		if(menuList!=null){
			for(Menu menu : menuList){
				nodes.add(new TreeNode(menu));
			}
		}
		return nodes;
	}
	@Override
	public JSONArray findSubTreeNodeJsonOfModelAndMenuAndOperation(long roleid,long parentModelid){
		// TODO Auto-generated method stub
		List<Model> modelList = modelService.findSons(parentModelid);
		List<Menu> menuList = menuService.findAllMenuByModelid(parentModelid);
		List<CheckedTreeNodeOfMoelAndMenuAndOperation> nodes = new ArrayList<CheckedTreeNodeOfMoelAndMenuAndOperation>();
		if(modelList!=null&&modelList.size()>0){
			for(Model model : modelList){
				nodes.add(new CheckedTreeNodeOfMoelAndMenuAndOperation(model));
			}
		}
		if(menuList!=null&&menuList.size()>0){
			Role role = roleService.find(roleid);
			Set<Menu> menus = null;
			Set<Operation> operations = null;
			if(role!=null){
				operations = role.getOperations();
				Map<Long,Menu_tree> menuTree = role.findMenuTree();
				if(menuTree.containsKey(parentModelid)){
					Menu_tree tree = menuTree.get(parentModelid);
					menus = tree.getMenus();
				}
			}
			for(Menu menu : menuList){
				nodes.add(new CheckedTreeNodeOfMoelAndMenuAndOperation(menu,menus,operations));
			}
		}
		return JSONArray.fromObject(nodes);
	}

	@Override
	public JSONArray findSubTreeNodeJsonOfModelAndMenuAndOperationByRoleid(long roleid, long parentModelid) {
		// TODO Auto-generated method stub
		Role role = roleService.find(roleid);
		if(role!=null){
			Map<Long,Menu_tree> tree = role.findMenuTree();
			if(tree!=null&&tree.containsKey(parentModelid)){
				Menu_tree t = tree.get(parentModelid);
				Set<Menu> menus = t.getMenus();
				Set<Model> models = t.getModels();
				Set<Operation> ops = role.getOperations();
				List<TreeNode> nodes = new ArrayList<TreeNode>();
				if(models!=null){
					List<Model> list = new ArrayList<Model>();
					list.addAll(models);
					Collections.sort(list);
					for(Model m : list){
						nodes.add(new TreeNode(m));
					}
				}
				if(menus!=null){
					List<Menu> list = new ArrayList<Menu>();
					list.addAll(menus);
					Collections.sort(list);
					for(Menu m : list){
						nodes.add(new TreeNode(m,ops));
					}
				}
				return JSONArray.fromObject(nodes);
			}
		}
		return null;
	}

}
