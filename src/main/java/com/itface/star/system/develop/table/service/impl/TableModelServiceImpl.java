package com.itface.star.system.develop.table.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itface.star.system.baseDao.BaseDao;
import com.itface.star.system.develop.table.model.TableModel;
import com.itface.star.system.develop.table.service.TableModelService;
import com.itface.star.system.easyui.TreeNode;
import com.itface.star.system.org.model.Group;
@Service
public class TableModelServiceImpl implements TableModelService{

	@Autowired
	private BaseDao<TableModel> dao;
	
	
	@Override
	public TableModel findTableById(long id) {
		// TODO Auto-generated method stub
		return dao.find(TableModel.class, id);
	}

	@Override
	@Transactional
	public TableModel addTable(TableModel table) {
		// TODO Auto-generated method stub
		return dao.persist(table);
	}

	@Override
	@Transactional
	public TableModel updateTable(TableModel table) {
		// TODO Auto-generated method stub
		return dao.update(table);
	}

	@Override
	@Transactional
	public void deleteTable(long id) {
		// TODO Auto-generated method stub
		dao.deleteById(TableModel.class, id);
	}

	@Override
	public JSONArray findTableTree() {
		// TODO Auto-generated method stub
		List<TableModel> list = this.findAll();
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		if(list!=null){
			for(TableModel t : list){
				nodes.add(new TreeNode(t));
			}
			//Collections.sort(nodes);
		}
		return JSONArray.fromObject(nodes);
	}

	@Override
	public List<TableModel> findAll() {
		// TODO Auto-generated method stub
		return dao.find("from TableModel");
	}

}
