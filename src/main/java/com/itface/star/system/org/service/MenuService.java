package com.itface.star.system.org.service;

import net.sf.json.JSONArray;

import com.itface.star.system.org.model.Menu;

public interface MenuService {

	public Menu add(Menu menu);
	public Menu update(Menu menu);
	public void remove(long id);
	public JSONArray findByModelid(long modelid);
}
