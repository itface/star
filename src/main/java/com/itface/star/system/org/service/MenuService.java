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
	 * 查询modelid模块下一级的所有菜单集合,不按权限过滤
	 * @param modelid
	 * @return
	 */
	public List<Menu> findMenuWithoutAuthByModelid(long modelid);
	/**
	 * 查询modelid模块下一级的菜单的排序序列
	 * @param modelid
	 * @return
	 */
	public List<Integer> findMenuOrderListByModelid(long modelid);
	/**
	 * 查询modelid模块下一级的所有菜单集合构成的 tree json对象,不按权限过滤
	 * @param modelid
	 * @return
	 */
	public JSONObject findMenuJsonWithoutAuthByModelid(long modelid);

}
