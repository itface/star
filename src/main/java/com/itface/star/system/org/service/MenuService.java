package com.itface.star.system.org.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.itface.star.system.org.model.Menu;

public interface MenuService {

	public void add(long modelid,Menu menu);
	public void update(long modelid,Menu menu);
	public void remove(long id);
	public void removeList(long[] idArray);
	public JSONObject findMenuJsonByModelid(long modelid);
	public List<Menu> findMenuByModelid(long modelid);
	public Menu find(long id);
	public List<Integer> findOrderListByModelid(long modelid);
}
