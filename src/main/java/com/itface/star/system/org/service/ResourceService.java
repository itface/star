package com.itface.star.system.org.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.itface.star.system.easyui.TreeNode;
import com.itface.star.system.org.model.Menu_tree;

/**
 * 构建菜单树时所用到的service,而modelService、menuService和operationService只是单独管理各自的service
 * @author Administrator
 *
 */
public interface ResourceService {

	/**
	 * 查出modelid模块下一级所有的模块和菜单的tree JSON对象，不按权限过滤
	 * @param modelid
	 * @return
	 */
	public JSONArray findSubTreeNodeJsonOfModelAndMenuWithoutAuthByModelid(long modelid);
	/**
	 * 查出userid用户在modelid模块下一级有权限的所有模块和菜单
	 * @param userid
	 * @param modelid
	 * @param treeMap
	 * @return
	 */
	public JSONArray findSubTreeNodesOfModelAndMenuWithAuthByModelid(String userid,long modelid,Map<Long,Menu_tree> treeMap);
	/**
	 * 查出modelid模块下一级所有的模块和菜单构建的TreeNode集合，不按权限过滤
	 * @param modelid
	 * @return
	 */
	public List<TreeNode> findSubTreeNodeOfModelAndMenuWithoutAuthByModelid(long modelid);
	
	/**
	 * 查询parentModelid模块下一级的所有模块、菜单和操作.如果菜单下有操作，则操作为叶子节点。如果菜单无操作，则菜单为叶子节点.
	 * 同时根据roleid角色下的菜单和操作设置节点的选中状态
	 * @param roleid
	 * @param parentModelid
	 * @return
	 */
	public JSONArray findSubTreeNodeJsonOfModelAndMenuAndOperation(long roleid,long parentModelid);
}
