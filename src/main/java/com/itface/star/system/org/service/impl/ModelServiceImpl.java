package com.itface.star.system.org.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.itface.star.system.baseDao.BaseDao;
import com.itface.star.system.easyui.TreeNode;
import com.itface.star.system.org.model.Menu;
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
		List<Model> sibling = this.findSons(model.getParentmodel());
		int order = model.getDisplayorder();
		for(Model m : sibling){
			if(m.getDisplayorder()>=order){
				m.setDisplayorder(m.getDisplayorder()+1);
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
		int oldOrder = this.find(model.getId()).getDisplayorder();
		int newOrder = model.getDisplayorder();
		for(Model m : sibling){
			//如果顺序变小了，则大于新顺序的模块的顺序都加1
			//如果顺序变大了,则大于该模块原顺序并且小于该模块新顺序的都减1，大于模块新顺序的模块则不变
			if(model.getId()!=m.getId()){
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
		return baseDao.update(model);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void remove(long id) {
		// TODO Auto-generated method stub
		List<Model> sibling = this.findSiblings(id);
		Model model = this.find(id);
		int order = model.getDisplayorder();
		for(Model m : sibling){
			if(model.getId()!=m.getId()&&m.getDisplayorder()>order){
				m.setDisplayorder(m.getDisplayorder()-1);
				this.update(m);
			}
		}
		List<Model> sonList = this.findSons(id);
		if(sonList==null||sonList.size()<1){
			baseDao.deleteById(Model.class, id);
		}else{
			for(Model m : sonList){
				this.remove(m.getId());
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
		List<Model> list = baseDao.find("from Model t where t.parentmodel=?1", new Object[]{id});
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Model> findSiblings(long id) {
		// TODO Auto-generated method stub
		List<Model> list = baseDao.find("select t2 from Model t1,Model t2 where t1.parentmodel=t2.parentmodel and t1.id=?1", new Object[]{id});
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
