package com.itface.star.system.org.model;

import java.util.HashSet;
import java.util.Set;

public class Menu_tree {

	private Set<Model> models=new HashSet<Model>();
	private Set<Menu> menus=new HashSet<Menu>();
	public Set<Model> getModels() {
		return models;
	}
	public void setModels(Set<Model> models) {
		this.models = models;
	}
	public Set<Menu> getMenus() {
		return menus;
	}
	public void setMenus(Set<Menu> menus) {
		this.menus = menus;
	}
	
	
}
