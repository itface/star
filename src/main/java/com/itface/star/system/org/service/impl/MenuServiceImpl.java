package com.itface.star.system.org.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.itface.star.system.baseDao.BaseDao;
import com.itface.star.system.easyui.TreeNode;
import com.itface.star.system.jqgrid.JqgridDataJson;
import com.itface.star.system.org.model.Menu;
import com.itface.star.system.org.model.Model;
import com.itface.star.system.org.model.Role;
import com.itface.star.system.org.model.User;
import com.itface.star.system.org.service.MenuService;
import com.itface.star.system.org.service.ModelService;
import com.itface.star.system.org.service.UserService;
@Service
public class MenuServiceImpl implements MenuService{

	@Autowired
	private BaseDao<Menu> dao;
	@Autowired
	private ModelService modelService;
	@Autowired
	private UserService userService;


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
				this.update(m);
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
		int oldOrder = this.find(menu.getId()).getDisplayorder();
		int newOrder = menu.getDisplayorder();
		if(newOrder!=oldOrder){
			List<Menu> sibling = this.findMenuByModelid(modelid);
			for(Menu m : sibling){
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
		List<Menu> list = dao.find("from Menu t where t.model.id=?1 order by t.displayorder asc", new Object[]{modelid});
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
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public JSONArray findSonsOfMenuTreeByModelid(String userid,long modelid) {
		// TODO Auto-generated method stub
		if("admin".equals(userid)){
			return this.findAllSonsOfMenuTreeByModelid(modelid);
		}else{
			List<TreeNode> nodes = new ArrayList<TreeNode>();
			User user = userService.findByUserid(userid);
			List<Model> modelList = user.getModels();
			Set<Menu> menuList = user.getMenus();
			if(modelList!=null){
				for(Model model : modelList){
					nodes.add(new TreeNode(model));
				}
			}
			if(menuList!=null&&menuList.size()>0){
				Iterator<Menu> it = menuList.iterator();
				while(it.hasNext()){
					nodes.add(new TreeNode(it.next()));
				}
			}
			return JSONArray.fromObject(nodes);
		}
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public JSONArray findAllSonsOfMenuTreeByModelid(long modelid) {
		// TODO Auto-generated method stub
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		List<Model> modelList = modelService.findSons(modelid);
		List<Menu> menuList = this.findMenuByModelid(modelid);
		if(modelList!=null){
			for(Model model : modelList){
				nodes.add(new TreeNode(model));
			}
		}
		if(menuList!=null){
			for(Menu menu : menuList){
				nodes.add(new TreeNode(menu));
			}
		}
		return JSONArray.fromObject(nodes);
	}
	
	
}
