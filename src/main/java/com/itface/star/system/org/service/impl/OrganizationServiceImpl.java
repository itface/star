package com.itface.star.system.org.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.itface.star.system.baseDao.BaseDao;
import com.itface.star.system.easyui.TreeNode;
import com.itface.star.system.org.model.Organization;
import com.itface.star.system.org.service.OrganizationService;
@Service
public class OrganizationServiceImpl implements OrganizationService{

	@Autowired
	private BaseDao<Organization> baseDao;
	
	@Override
	public Organization find(long id) {
		// TODO Auto-generated method stub
		return baseDao.find(Organization.class, id);
	}

	@Override
	@Transactional
	public Organization add(Organization organization) {
		// TODO Auto-generated method stub
		List<Organization> sibling = this.findSons(organization.getParentid());
		int order = organization.getDisplayorder();
		for(Organization m : sibling){
			if(m.getDisplayorder()>=order){
				m.setDisplayorder(m.getDisplayorder()+1);
				this.updateOnly(m);
			}
		}
		return baseDao.persist(organization);
	}

	@Transactional
	public Organization updateOnly(Organization organization) {
		return baseDao.update(organization);
	}
	@Override
	@Transactional
	public Organization update(Organization organization) {
		// TODO Auto-generated method stub
		int oldOrder = this.find(organization.getId()).getDisplayorder();
		int newOrder = organization.getDisplayorder();
		if(oldOrder!=newOrder){
			List<Organization> sibling = this.findSiblings(organization.getId());
			for(Organization m : sibling){
				//如果顺序变小了，则大于新顺序的模块的顺序都加1
				//如果顺序变大了,则大于该模块原顺序并且小于该模块新顺序的都减1，大于模块新顺序的模块则不变
				if(organization.getId()!=m.getId()){
					if(newOrder>oldOrder){
						if(m.getDisplayorder()>oldOrder&&m.getDisplayorder()<newOrder){
							m.setDisplayorder(m.getDisplayorder()-1);
							this.updateOnly(m);
						}
					}else if(newOrder<oldOrder&&m.getDisplayorder()>=newOrder){
						m.setDisplayorder(m.getDisplayorder()+1);
						this.updateOnly(m);
					}
				}
			}
		}
		return this.updateOnly(organization);
	}

	@Override
	@Transactional
	public void remove(long id) {
		// TODO Auto-generated method stub
		List<Organization> sibling = this.findSiblings(id);
		Organization organization = this.find(id);
		int order = organization.getDisplayorder();
		for(Organization m : sibling){
			if(organization.getId()!=m.getId()&&m.getDisplayorder()>order){
				m.setDisplayorder(m.getDisplayorder()-1);
				this.update(m);
			}
		}
		List<Organization> sonList = this.findSons(id);
		if(sonList==null||sonList.size()<1){
			baseDao.deleteById(Organization.class, id);
		}else{
			for(Organization m : sonList){
				this.remove(m.getId());
			}
			baseDao.deleteById(Organization.class, id);
		}
	}

	@Override
	public JSONArray findSonJson(long id) {
		// TODO Auto-generated method stub
		List<Organization> list = this.findSons(id);
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		if(list!=null){
			for(Organization organization : list){
				nodes.add(new TreeNode(organization));
			}
			//Collections.sort(nodes);
		}
		return JSONArray.fromObject(nodes);
	}

	@Override
	public List<Organization> findSons(long id) {
		// TODO Auto-generated method stub
		return baseDao.find("from Organization t where t.parentid=?1 order by t.displayorder asc", new Object[]{id});
	}

	@Override
	public List<Organization> findSiblings(long id) {
		// TODO Auto-generated method stub
		return baseDao.find("select t2 from Organization t1,Organization t2 where t1.parentid=t2.parentid and t1.id=?1", new Object[]{id});
	}

	@Override
	public List<Integer> findOrderList(long id) {
		// TODO Auto-generated method stub
		List<Organization> list = this.findSiblings(id);
		List<Integer> orderList = new ArrayList<Integer>();
		for(int i=1;i<=list.size();i++){
			orderList.add(i);
		}
		return orderList;
	}

	@Override
	public List<Integer> findSonOrderList(long id) {
		// TODO Auto-generated method stub
		List<Organization> sonList = this.findSons(id);
		List<Integer> orderList = new ArrayList<Integer>();
		for(int i=1;i<=sonList.size();i++){
			orderList.add(i);
		}
		return orderList;
	}

	@Override
	public Organization findParent(Organization organization) {
		// TODO Auto-generated method stub
		return baseDao.findSingleResult("from Organization t where t.id=?1", new Object[]{organization.getParentid()});
	}

	@Override
	public List<Organization> findALlParents(long id) {
		// TODO Auto-generated method stub
		List<Organization> list = new ArrayList();
		Organization organization = this.find(id);
		Organization parent = this.findParent(organization);
		if(parent!=null){
			list.add(parent);
			list.addAll(this.findALlParents(parent.getId()));
		}
		return list;
	}

}
