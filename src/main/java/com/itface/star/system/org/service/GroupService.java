package com.itface.star.system.org.service;

import java.util.List;
import java.util.Set;

import net.sf.json.JSONArray;

import com.itface.star.system.org.model.Group;
import com.itface.star.system.org.model.User;

public interface GroupService {

	public Group find(long id);
	public Group add(boolean openRoleTreeFlag,String checkedUserids,String checkedRoleids,Group group);
	public Group update(boolean openRoleTreeFlag,String checkedUserids,String checkedRoleids,Group group);
	public void remove(long id);
	public JSONArray findSonJson(long id);
	public List<Group> findSons(long id);
	public List<Group> findSiblings(long id);
	public Group findParent(Group group);
}
