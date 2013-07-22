package com.itface.star.system.org.service;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.itface.star.system.org.model.Role;

public interface RoleService {

	public List<Role> findAll();
	public Role find(long id);
	public JSONObject findAllRoleJqgirdJson();
	public void add(Role role,Long[] modelIds,Long[] menuIds,Long[] operationIds);
	public void update(Role role,Long[] allmodelIds,Long[] allMenuIds,Long[] allOperationIds,Long[] checkedModelIds,Long[] checkedMenuIds,Long[] checkedOperationIds);
	public void remove(long id);
	public void delete(long[] ids);
	public JSONArray roleTreeJson(long userid);
}
