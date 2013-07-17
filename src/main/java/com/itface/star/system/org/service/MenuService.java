package com.itface.star.system.org.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.itface.star.system.easyui.TreeNode;
import com.itface.star.system.org.model.Menu;
import com.itface.star.system.org.model.Menu_tree;
import com.itface.star.system.org.model.Operation;
import com.itface.star.system.org.model.Role;

public interface MenuService {

	public Menu find(long id);
	public void add(long modelid,Menu menu);
	public void update(long modelid,Menu menu);
	public void remove(long id);
	public void removeList(long[] idArray);
	/**
	 * 查询modelid模块下一级的所有菜单集合,不安权限过滤
	 * @param modelid
	 * @return
	 */
	public List<Menu> findAllMenuByModelid(long modelid);
	/**
	 * 查询modelid模块下一级的菜单的排序序列
	 * @param modelid
	 * @return
	 */
	public List<Integer> findMenuOrderListByModelid(long modelid);
	/**
	 * 查询modelid模块下一级的所有菜单集合构成的 tree json对象,不安权限过滤
	 * @param modelid
	 * @return
	 */
	public JSONObject findAllMenuJsonByModelid(long modelid);
//	/**
//	 * 查出modelid模块下一级所有的模块和菜单的tree JSON对象，不按权限过滤
//	 * @param modelid
//	 * @return
//	 */
//	public JSONArray findAllSonsJsonOfMenuTreeByModelid(long modelid);
//	/**
//	 * 查出userid用户在modelid模块下一级有权限的所有模块和菜单
//	 * @param userid
//	 * @param modelid
//	 * @param treeMap
//	 * @return
//	 */
//	public JSONArray findSonsOfMenuTreeByModelid(String userid,long modelid,Map<Long,Menu_tree> treeMap);
//	/**
//	 * 查出modelid模块下一级所有的模块和菜单构建的TreeNode集合，不按权限过滤
//	 * @param modelid
//	 * @return
//	 */
//	public List<TreeNode> findAllSonsOfMenuTreeByModelid(long modelid);
//	/**
//	 * 查询modelid模块下一级的所有菜单集合构成的 tree json对象,不安权限过滤
//	 * @param modelid
//	 * @return
//	 */
//	public JSONObject findAllMenuJsonByModelid(long modelid);
//	/**
//	 * 查询modelid模块下一级的菜单的排序序列
//	 * @param modelid
//	 * @return
//	 */
//	public List<Integer> findMenuOrderListByModelid(long modelid);
//	/**
//	 * 查询parentModelid模块下一级的所有模块、菜单和操作.如果菜单下有操作，则操作为叶子节点。如果菜单无操作，则菜单为叶子节点.
//	 * 同时根据roleid角色下的菜单和操作设置节点的选中状态
//	 * @param roleid
//	 * @param parentModelid
//	 * @return
//	 */
//	public JSONArray findTreeOfModelAndMenuAndOperation(long roleid,long parentModelid);
}
