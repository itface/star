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
import com.itface.star.system.org.service.ModelService;
@Service
public class MenuServiceImpl implements MenuService{

	@Autowired
	private BaseDao<Menu> dao;
	@Autowired
	private ModelService modelService;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void add(long modelid,Menu menu) {
		// TODO Auto-generated method stub
		Model model = modelService.find(modelid);
		menu.setModel(model);
		List<Menu> sibling = this.findMenuByModelid(modelid);
		int order = menu.getDisplayorder();
		for(Menu m : sibling){
			if(m.getDisplayorder()>=order){
				m.setDisplayorder(m.getDisplayorder()+1);
				this.update(modelid,m);
			}
		}
		dao.persist(menu);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(long modelid,Menu menu) {
		// TODO Auto-generated method stub
		Model model = modelService.find(modelid);
		menu.setModel(model);
		List<Menu> sibling = this.findMenuByModelid(modelid);
		int order = menu.getDisplayorder();
		for(Menu m : sibling){
			if(menu.getId()!=m.getId()&&m.getDisplayorder()>=order){
				m.setDisplayorder(m.getDisplayorder()+1);
				this.update(modelid,m);
			}
		}
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
	public JSONObject findMenuJsonByModelid(long modelid) {
		// TODO Auto-generated method stub
		List<Menu> list = this.findMenuByModelid(modelid);
		if(list!=null){
			Collections.sort(list);
			JqgridDataJson<Menu> jsonModel = new JqgridDataJson<Menu>(list);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setExcludes(new String[]{"model","operations"});
			return JSONObject.fromObject(jsonModel,jsonConfig);
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Menu> findMenuByModelid(long modelid) {
		// TODO Auto-generated method stub
		List<Menu> list = dao.find("from Menu t where t.model.id=?1", new Object[]{modelid});
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Menu find(long id) {
		// TODO Auto-generated method stub
		return dao.find(Menu.class, id);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Integer> findOrderListByModelid(long modelid) {
		// TODO Auto-generated method stub
		List<Menu> list = this.findMenuByModelid(modelid);
		List<Integer> orderList = new ArrayList<Integer>();
		for(int i=1;i<=list.size();i++){
			orderList.add(i);
		}
		return orderList;
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
	
	
}
