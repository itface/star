package com.itface.star.system.org.service;

import net.sf.json.JSONArray;

public interface GroupOrgUserRoleService {
	

	public JSONArray findSubCheckedTreeNodeJsonOfOrgAndUserByOrgid(long orgid,long groupid);
	public JSONArray groupRoleTreeJson(long groupid);
}
