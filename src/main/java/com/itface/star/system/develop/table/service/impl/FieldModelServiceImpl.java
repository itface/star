package com.itface.star.system.develop.table.service.impl;

import java.util.List;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itface.star.system.baseDao.BaseDao;
import com.itface.star.system.develop.table.model.FieldModel;
import com.itface.star.system.develop.table.model.TableModel;
import com.itface.star.system.develop.table.service.FieldModelService;
import com.itface.star.system.jqgrid.JqgridDataJson;
@Service
public class FieldModelServiceImpl implements FieldModelService{

	@Autowired
	private BaseDao<FieldModel> dao;
	
	@Override
	public FieldModel findFieldById(long id) {
		// TODO Auto-generated method stub
		return dao.find(FieldModel.class, id);
	}

	@Override
	@Transactional
	public void addField(FieldModel field,long tableid) {
		// TODO Auto-generated method stub
		TableModel t = new TableModel();
		t.setId(tableid);
		field.setTableModel(t);
		dao.persist(field);
	}

	@Override
	@Transactional
	public void updateField(FieldModel field,long tableid) {
		// TODO Auto-generated method stub
		TableModel t = new TableModel();
		t.setId(tableid);
		field.setTableModel(t);
		dao.update(field);
	}

	@Override
	@Transactional
	public void deleteField(String ids) {
		// TODO Auto-generated method stub
		if(ids!=null&&!"".equals(ids)){
			String[] arr = ids.split(",");
			for(int i=0;i<arr.length;i++){
				dao.deleteById(FieldModel.class, Long.parseLong(arr[i]));
			}
		}
	}

	@Override
	public JSONObject findGridDataOfField(long tableid) {
		// TODO Auto-generated method stub
		List<FieldModel> list = this.findAll(tableid);
		if(list!=null&&list.size()>0){
			JqgridDataJson<FieldModel> jsonModel = new JqgridDataJson<FieldModel>(list);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setExcludes(new String[]{"tableModel"});
			return JSONObject.fromObject(jsonModel,jsonConfig);
		}
		return null;
	}

	@Override
	public List<FieldModel> findAll(long tableid) {
		// TODO Auto-generated method stub
		return dao.find("select f from FieldModel f left join fetch f.tableModel t where t.id=?1",new Object[]{tableid});
	}

}
