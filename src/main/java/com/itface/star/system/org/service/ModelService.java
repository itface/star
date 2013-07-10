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
	public JSONArray findSonJson(long id);
	public List<Model> findSons(long id);
	public List<Model> findSiblings(long id);
	public List<Integer> findOrderList(long id);
	public List<Integer> findSonOrderList(long id);
}
