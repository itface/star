package com.itface.star.system.org.model;

import java.util.HashSet;
import java.util.Set;

public class Menu_tree {

	private Set<Long> models=new HashSet<Long>();
	private Set<Long> menus=new HashSet<Long>();
	public Set<Long> getModels() {
		return models;
	}
	public void setModels(Set<Long> models) {
		this.models = models;
	}
	public Set<Long> getMenus() {
		return menus;
	}
	public void setMenus(Set<Long> menus) {
		this.menus = menus;
	}
	
	
}
