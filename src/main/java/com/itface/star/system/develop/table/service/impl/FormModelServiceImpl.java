package com.itface.star.system.develop.table.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itface.star.system.baseDao.BaseDao;
import com.itface.star.system.develop.table.model.FormModel;
import com.itface.star.system.develop.table.model.TableModel;
import com.itface.star.system.develop.table.service.FormModelService;
import com.itface.star.system.easyui.TreeNode;
@Service
public class FormModelServiceImpl implements FormModelService{

	@Autowired
	private BaseDao<FormModel> dao;
	@Override
	public JSONArray findFormTree() {
		// TODO Auto-generated method stub
		List<FormModel> list = this.findAll();
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		if(list!=null){
			for(FormModel f : list){
				nodes.add(new TreeNode(f));
			}
			//Collections.sort(nodes);
		}
		return JSONArray.fromObject(nodes);
	}

	@Override
	public List<FormModel> findAll() {
		// TODO Auto-generated method stub
		return dao.find("from FormModel t");
	}

	@Override
	public FormModel find(long id) {
		// TODO Auto-generated method stub
		return dao.find(FormModel.class, id);
	}

	@Override
	@Transactional
	public FormModel add(FormModel form) {
		// TODO Auto-generated method stub
		return dao.persist(form);
	}

	@Override
	@Transactional
	public FormModel update(FormModel form) {
		// TODO Auto-generated method stub
		return dao.update(form);
	}

	@Override
	@Transactional
	public void delete(long id) {
		// TODO Auto-generated method stub
		dao.deleteById(FormModel.class, id);
	}

	@Override
	public String getFormSettingGridHtml(long formid) {
		// TODO Auto-generated method stub
		StringBuffer s = new StringBuffer();
		FormModel form = this.find(formid);
		if(form!=null){
			TableModel mainTable = form.getMainTableModel();
			Set<TableModel> subTables= form.getSubTableModels();
			if(mainTable!=null){
				s.append("<div><table id='mainGrid'></table></div></br>");
			}
			if(subTables!=null&&subTables.size()>0){
				Iterator<TableModel> it = subTables.iterator();
				int count=1;
				while(count<=subTables.size()){
					s.append("<div><table id='subGrid"+(count++)+"'></table></div></br>");
				}
			}
		}
		return s.toString();
	}

	@Override
	public String getFormSettingGridScript(long formid) {
		// TODO Auto-generated method stub
		StringBuffer s = new StringBuffer();
		FormModel form = this.find(formid);
		if(form!=null){
			TableModel mainTable = form.getMainTableModel();
			Set<TableModel> subTables= form.getSubTableModels();
			if(mainTable!=null){
				s.append("	$('#mainGrid').jqGrid({").append("\n");
				s.append("		url:ctx+'/system/',").append("\n");
				s.append("		ajaxGridOptions: { contentType: 'application/json; charset=utf-8' },").append("\n");
				s.append("		datatype: 'json',").append("\n");
				s.append("		mtype: 'GET',").append("\n");
				s.append("		jsonReader:{").append("\n");
				s.append("			repeatitems:false").append("\n");
				s.append("		},").append("\n");
				s.append("		gridview: true,").append("\n");
				s.append("		rownumbers:true,").append("\n");
				s.append("		multiselect:true,").append("\n");
				s.append("		pager: 'false',").append("\n");
				s.append("		width:850,").append("\n");
				s.append("		height:500,").append("\n");
				s.append("		caption:'"+mainTable.getText()+"',").append("\n");
				s.append("		toolbar: [true,'top'],").append("\n");
				s.append("		colNames: ['ID','字段','名称','字段类型','字段长度','外观'],").append("\n");
				s.append("		colModel: [").append("\n");
				s.append("			{ name: 'id', index: 'id', hidden: true,key: true,editable:true},").append("\n");
				s.append("			{ name: 'name', index: 'name',width: 200,editable:true},").append("\n");
				s.append("			{ name: 'text', index: 'text',width: 200,editable:true},").append("\n");
				s.append("			{ name: 'fieldtype', index: 'fieldtype',width: 200,editable:true},").append("\n");
				s.append("			{ name: 'fieldlength', index: 'fieldlength',width: 200,editable:true},").append("\n");
				s.append("			{ name: 'edittype', index: 'edittype',width: 200,editable:true}").append("\n");
				s.append("		],").append("\n");
				s.append("		ondblClickRow:function(rowid,iCol,cellcontent,e){").append("\n");
				s.append("			createDialog('mainGrid','http://www.baidu.com');").append("\n");
				s.append("		}").append("\n");
				s.append("	});").append("\n");
			}
			if(subTables!=null&&subTables.size()>0){
				Iterator<TableModel> it = subTables.iterator();
				int count=1;
				while(it.hasNext()){
					TableModel t = it.next();
					String id = "subGrid"+(count++);
					s.append("	$('#"+id+"').jqGrid({").append("\n");
					s.append("		url:ctx+'/system/',").append("\n");
					s.append("		ajaxGridOptions: { contentType: 'application/json; charset=utf-8' },").append("\n");
					s.append("		datatype: 'json',").append("\n");
					s.append("		mtype: 'GET',").append("\n");
					s.append("		jsonReader:{").append("\n");
					s.append("			repeatitems:false").append("\n");
					s.append("		},").append("\n");
					s.append("		gridview: true,").append("\n");
					s.append("		rownumbers:true,").append("\n");
					s.append("		multiselect:true,").append("\n");
					s.append("		pager: 'false',").append("\n");
					s.append("		width:850,").append("\n");
					s.append("		height:500,").append("\n");
					s.append("		caption:'"+t.getText()+"',").append("\n");
					s.append("		toolbar: [true,'top'],").append("\n");
					s.append("		colNames: ['ID','字段','名称','字段类型','字段长度','外观'],").append("\n");
					s.append("		colModel: [").append("\n");
					s.append("			{ name: 'id', index: 'id', hidden: true,key: true,editable:true},").append("\n");
					s.append("			{ name: 'name', index: 'name',width: 200,editable:true},").append("\n");
					s.append("			{ name: 'text', index: 'text',width: 200,editable:true},").append("\n");
					s.append("			{ name: 'fieldtype', index: 'fieldtype',width: 200,editable:true},").append("\n");
					s.append("			{ name: 'fieldlength', index: 'fieldlength',width: 200,editable:true},").append("\n");
					s.append("			{ name: 'edittype', index: 'edittype',width: 200,editable:true}").append("\n");
					s.append("		],").append("\n");
					s.append("		ondblClickRow:function(rowid,iCol,cellcontent,e){").append("\n");
					s.append("			createDialog('"+id+"','http://www.baidu.com');").append("\n");
					s.append("		}").append("\n");
					s.append("	});").append("\n");
				}
			}
		}
		return s.toString();
	}
}
