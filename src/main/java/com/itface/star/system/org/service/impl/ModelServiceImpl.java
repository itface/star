package com.itface.star.system.org.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.itface.star.system.baseDao.BaseDao;
import com.itface.star.system.easyui.TreeNode;
import com.itface.star.system.org.model.Model;
import com.itface.star.system.org.service.ModelService;
@Service
public class ModelServiceImpl implements ModelService{

	@Autowired
	private BaseDao<Model> baseDao;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Model add(Model model) {
		// TODO Auto-generated method stub
		List<Model> sibling = this.findSons(model.getParentModel());
		int order = model.getDisplayOrder();
		for(Model m : sibling){
			if(m.getDisplayOrder()>=order){
				m.setDisplayOrder(m.getDisplayOrder()+1);
				this.update(m);
			}
		}
		return baseDao.persist(model);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Model update(Model model) {
		// TODO Auto-generated method stub
		List<Model> sibling = this.findSiblings(model.getId());
		int order = model.getDisplayOrder();
		for(Model m : sibling){
			if(model.getId()!=m.getId()&&m.getDisplayOrder()>=order){
				m.setDisplayOrder(m.getDisplayOrder()+1);
				this.update(m);
			}
		}
		return baseDao.update(model);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void remove(long id) {
		// TODO Auto-generated method stub
		List<Model> sonList = this.findSons(id);
		if(sonList==null||sonList.size()<1){
			baseDao.deleteById(Model.class, id);
		}else{
			for(Model model : sonList){
				this.remove(model.getId());
			}
			baseDao.deleteById(Model.class, id);
		}
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Model find(long id) {
		// TODO Auto-generated method stub
		return baseDao.find(Model.class, id);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public JSONArray findSonJson(long id) {
		// TODO Auto-generated method stub
		List<Model> list = this.findSons(id);
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		if(list!=null){
			for(Model model : list){
				nodes.add(new TreeNode(model));
			}
			Collections.sort(nodes);
		}
		return JSONArray.fromObject(nodes);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Model> findSons(long id) {
		// TODO Auto-generated method stub
		List<Model> list = baseDao.find("from Model t where t.parentModel=?1", new Object[]{id});
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Model> findSiblings(long id) {
		// TODO Auto-generated method stub
		List<Model> list = baseDao.find("select t2 from Model t1,Model t2 where t1.parentModel=t2.parentModel and t1.id=?1", new Object[]{id});
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Integer> findOrderList(long id) {
		// TODO Auto-generated method stub
		List<Model> list = this.findSiblings(id);
		List<Integer> orderList = new ArrayList<Integer>();
		for(int i=1;i<=list.size();i++){
			orderList.add(i);
		}
		return orderList;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Integer> findSonOrderList(long id) {
		// TODO Auto-generated method stub
		List<Model> sonList = this.findSons(id);
		List<Integer> orderList = new ArrayList<Integer>();
		for(int i=1;i<=sonList.size();i++){
			orderList.add(i);
		}
		return orderList;
	}

}
