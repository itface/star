package com.itface.star.system.org.service;

import java.util.List;

import net.sf.json.JSONArray;

import com.itface.star.system.org.model.Organization;

public interface OrganizationService {

	public Organization find(long id);
	public Organization add(Organization organization);
	public Organization update(Organization organization);
	public void remove(long id);
	public JSONArray findSonJson(long id);
	public List<Organization> findSons(long id);
	public List<Organization> findSiblings(long id);
	public List<Integer> findOrderList(long id);
	public List<Integer> findSonOrderList(long id);
	public Organization findParent(Organization model);
	public List<Organization> findALlParents(long id);
}
