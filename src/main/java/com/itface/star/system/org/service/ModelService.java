package com.itface.star.system.org.service;

import java.util.List;

import net.sf.json.JSONArray;

import com.itface.star.system.org.model.Menu;
import com.itface.star.system.org.model.Model;

public interface ModelService {

	public Model find(long id);
	public Model add(Model model);
	public Model update(Model model);
	public void remove(long id);
	public List<Integer> findOrderList(long id);
	public List<Integer> findSonOrderList(long id);
	public Model findParent(Model model);
	/**
	 * 查询模块树的下一级，不按权限过滤
	 * @param id
	 * @return
	 */
	public JSONArray findSonJsonWithoutAuth(long id);
	/**
	 * 查询模块树的下一级，不按权限过滤
	 * @param id
	 * @return
	 */
	public List<Model> findSonsWithoutAuth(long id);
	/**
	 * 查找模块树节点的兄弟节点（包含自身）,不按权限过滤
	 * @param id
	 * @return
	 */
	public List<Model> findSiblingsWithoutAuth(long id);
	
	/**
	 * 查找模块节点的全路径上的所有模块
	 * @param id
	 * @return
	 */
	public List<Model> findALlParents(long id);
}
