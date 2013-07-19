package com.itface.star.system.org.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.itface.star.system.baseDao.BaseDao;
import com.itface.star.system.jqgrid.JqgridDataJson;
import com.itface.star.system.org.model.Menu;
import com.itface.star.system.org.model.Model;
import com.itface.star.system.org.service.MenuService;
@Service
public class MenuServiceImpl implements MenuService{

	@Autowired
	private BaseDao<Menu> dao;


	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void add(long modelid,Menu menu) {
		// TODO Auto-generated method stub
		Model model = new Model();
		model.setId(modelid);
		menu.setModel(model);
		List<Menu> sibling = this.findAllMenuByModelid(modelid);
		int order = menu.getDisplayorder();
		for(Menu m : sibling){
			if(m.getDisplayorder()>=order){
				m.setDisplayorder(m.getDisplayorder()+1);
				this.update(m);
			}
		}
		dao.persist(menu);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(long modelid,Menu menu) {
		// TODO Auto-generated method stub
		Model model = new Model();
		model.setId(modelid);
		menu.setModel(model);
		int oldOrder = this.find(menu.getId()).getDisplayorder();
		int newOrder = menu.getDisplayorder();
		if(newOrder!=oldOrder){
			List<Menu> sibling = this.findAllMenuByModelid(modelid);
			for(Menu m : sibling){
				if(menu.getId()!=m.getId()){
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
		this.update(menu);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(Menu menu) {
		dao.update(menu);
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void remove(long id) {
		// TODO Auto-generated method stub
		dao.deleteById(Menu.class, id);
	}

	

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Menu> findAllMenuByModelid(long modelid) {
		// TODO Auto-generated method stub
		List<Menu> list = dao.find("from Menu t where t.model.id=?1 order by t.displayorder asc", new Object[]{modelid});
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Menu find(long id) {
		// TODO Auto-generated method stub
		return (Menu)dao.find(Menu.class, id);
	}

	

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void removeList(long[] idArray) {
		// TODO Auto-generated method stub
		if(idArray!=null&&idArray.length>0){
			for(int i=0;i<idArray.length;i++){
				this.remove(idArray[i]);
			}
		}
	}
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Integer> findMenuOrderListByModelid(long modelid) {
		// TODO Auto-generated method stub
		List<Menu> list = this.findAllMenuByModelid(modelid);
		List<Integer> orderList = new ArrayList<Integer>();
		for(int i=1;i<=list.size();i++){
			orderList.add(i);
		}
		return orderList;
	}
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public JSONObject findAllMenuJsonByModelid(long modelid) {
		// TODO Auto-generated method stub
		List<Menu> list = this.findAllMenuByModelid(modelid);
		if(list!=null){
			Collections.sort(list);
			JqgridDataJson<Menu> jsonModel = new JqgridDataJson<Menu>(list);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setExcludes(new String[]{"model","operations","roles"});
			return JSONObject.fromObject(jsonModel,jsonConfig);
		}
		return null;
	}
}
