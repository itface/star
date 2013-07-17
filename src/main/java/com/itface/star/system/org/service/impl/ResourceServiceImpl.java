package com.itface.star.system.org.service.impl;

import java.util.ArrayList;
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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public JSONArray findSubTreeNodesOfModelAndMenuWithAuthByModelid(String userid,long modelid,Map<Long,Menu_tree> treeMap) {
		// TODO Auto-generated method stub
		if("admin".equals(userid)){
			return this.findSubTreeNodeJsonOfModelAndMenuWithoutAuthByModelid(modelid);
		}else if(treeMap!=null&&treeMap.size()>0&&treeMap.containsKey(modelid)){
			List<TreeNode> nodes = new ArrayList<TreeNode>();
			List<TreeNode> allNodes = this.findSubTreeNodeOfModelAndMenuWithoutAuthByModelid(modelid);
			Menu_tree tree = treeMap.get(modelid);
			Set<Long> models = tree.getModels();
			Set<Long> menus = tree.getMenus();
			List<TreeNode> modelNode = new ArrayList<TreeNode>();
			List<TreeNode> menuNode = new ArrayList<TreeNode>();
			if(models!=null&&models.size()>0){
				for(Long mid : models){
					for(TreeNode node : allNodes){
						if(node.getAttributes().getId()==mid){
							modelNode.add(node);
							break;
						}
					}
				}
			}
			if(menus!=null&&menus.size()>0){
				for(Long mid : menus){
					for(TreeNode node : allNodes){
						if(node.getAttributes().getId()==mid){
							modelNode.add(node);
							break;
						}
					}
				}
			}
			return JSONArray.fromObject(nodes);
		}else{
			return null;
		}
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public JSONArray findSubTreeNodeJsonOfModelAndMenuWithoutAuthByModelid(long modelid) {
		// TODO Auto-generated method stub
		return JSONArray.fromObject(this.findSubTreeNodeOfModelAndMenuWithoutAuthByModelid(modelid));
	}
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
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
			String operationsIds = "";
			Set<Menu> menus = null;
			Set<Operation> operations = null;
			if(role!=null){
				menus = role.getMenus();
				operations = role.getOperations();
			}
			for(Menu menu : menuList){
				nodes.add(new CheckedTreeNodeOfMoelAndMenuAndOperation(menu,menus,operations));
			}
		}
		return JSONArray.fromObject(nodes);
	}

}
