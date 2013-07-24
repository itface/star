package com.itface.star.system.org.service;

import java.util.Set;

import net.sf.json.JSONArray;

import com.itface.star.system.org.model.Role;

public interface GroupOrgUserRoleService {
	

	public JSONArray findSubCheckedTreeNodeJsonOfOrgAndUserByOrgid(long orgid,long groupid);
	public JSONArray groupRoleTreeJson(long groupid);
	/**
	 * 一个用户所拥有的所有角色，包括用户自身的角色，和用户所有的权限组所包含的树型角色层
	 * @param uid
	 * @return
	 */
	public Set<Role> findAllRoles(String uid);
}
