package com.itface.star.system.org.service;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.itface.star.system.org.model.User;

public interface UserService {

	public User findByUserid(String userid);
	public User find(long id);
	public void add(long orgid,User user,boolean openRoleTreeFlag,String checkedRoleIds);
	public void update(long orgid,User user,boolean openRoleTreeFlag,String checkedRoleIds);
	public void remove(long id);
	public void removeList(long[] idArray);
	public List<User> findAllUserByOrgid(long orgid);
	public List<Integer> findUserOrderListByOrgid(long orgid);
	public JSONObject findAllUserJsonByOrgid(long orgid);
}
