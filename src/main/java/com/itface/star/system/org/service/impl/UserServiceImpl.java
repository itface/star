package com.itface.star.system.org.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itface.star.system.baseDao.BaseDao;
import com.itface.star.system.jqgrid.JqgridDataJson;
import com.itface.star.system.org.model.Organization;
import com.itface.star.system.org.model.Role;
import com.itface.star.system.org.model.User;
import com.itface.star.system.org.service.UserService;
@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private BaseDao<User> dao;

	@Override
	public User findByUserid(String userid) {
		// TODO Auto-generated method stub
		User user = (User)dao.findSingleResult("from User t where t.userid=?1", new Object[]{userid});
		return user;
	}

	@Override
	public User find(long id) {
		// TODO Auto-generated method stub
		return dao.find(User.class, id);
	}

	@Override
	@Transactional
	public void add(long orgid, User user,boolean openRoleTreeFlag,String checkedRoleIds) {
		// TODO Auto-generated method stub
		Organization org = new Organization();
		org.setId(orgid);
		user.setOrganization(org);
		List<User> sibling = this.findAllUserByOrgid(orgid);
		int order = user.getDisplayorder();
		for(User m : sibling){
			if(m.getDisplayorder()>=order){
				m.setDisplayorder(m.getDisplayorder()+1);
				this.update(m);
			}
		}
		if(openRoleTreeFlag&&checkedRoleIds!=null&&!"".equals(checkedRoleIds)){
			String[] roleids = checkedRoleIds.split(",");
			for(int i=0;i<roleids.length;i++){
				Role role = new Role();
				role.setId(Long.parseLong(roleids[i]));
				user.getRoles().add(role);
			}
		}
		user.setPassword(DigestUtils.md5Hex("123456"));
		dao.persist(user);
	}

	@Transactional
	public void update(User user) {
		dao.update(user);
	}
	@Override
	@Transactional
	public void update(long orgid, User user,boolean openRoleTreeFlag,String checkedRoleIds) {
		// TODO Auto-generated method stub
		Organization org = new Organization();
		org.setId(orgid);
		user.setOrganization(org);
		User oldUser = this.find(user.getId());
		user.setPassword(oldUser.getPassword());
		int oldOrder = oldUser.getDisplayorder();
		int newOrder = user.getDisplayorder();
		if(newOrder!=oldOrder){
			List<User> sibling = this.findAllUserByOrgid(orgid);
			for(User m : sibling){
				if(user.getId()!=m.getId()){
					if(newOrder>oldOrder){
						if(m.getDisplayorder()>oldOrder&&m.getDisplayorder()<newOrder){
							m.setDisplayorder(m.getDisplayorder()-1);
							this.update(m);
						}
					}else if(newOrder<oldOrder&&m.getDisplayorder()>=newOrder){
						m.setDisplayorder(m.getDisplayorder()+1);
						this.update(m);
					}
				}
			}
		}
		if(openRoleTreeFlag){
			if(checkedRoleIds!=null&&!"".equals(checkedRoleIds)){
				String[] roleids = checkedRoleIds.split(",");
				for(int i=0;i<roleids.length;i++){
					Role role = new Role();
					role.setId(Long.parseLong(roleids[i]));
					user.getRoles().add(role);
				}
			}
		}else{
			user.setRoles(oldUser.getRoles());
		}
		this.update(user);
	}

	@Override
	@Transactional
	public void remove(long id) {
		// TODO Auto-generated method stub
		dao.deleteById(User.class, id);
	}

	@Override
	@Transactional
	public void removeList(long[] idArray) {
		// TODO Auto-generated method stub
		if(idArray!=null&&idArray.length>0){
			for(int i=0;i<idArray.length;i++){
				this.remove(idArray[i]);
			}
		}
	}

	@Override
	public List<User> findAllUserByOrgid(long orgid) {
		// TODO Auto-generated method stub
		return dao.find("from User t where t.organization.id=?1 order by t.displayorder asc", new Object[]{orgid});
	}

	@Override
	public List<Integer> findUserOrderListByOrgid(long orgid) {
		// TODO Auto-generated method stub
		List<User> list = this.findAllUserByOrgid(orgid);
		List<Integer> orderList = new ArrayList<Integer>();
		for(int i=1;i<=list.size();i++){
			orderList.add(i);
		}
		return orderList;
	}

	@Override
	public JSONObject findAllUserJsonByOrgid(long orgid) {
		// TODO Auto-generated method stub
		List<User> list = this.findAllUserByOrgid(orgid);
		if(list!=null){
			Collections.sort(list);
			JqgridDataJson<User> jsonModel = new JqgridDataJson<User>(list);
			JsonConfig jsonConfig = new JsonConfig();
			//jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT); 
			jsonConfig.setExcludes(new String[]{"organization","roles","groups"});
			return JSONObject.fromObject(jsonModel,jsonConfig);
		}
		return null;
	}

}
