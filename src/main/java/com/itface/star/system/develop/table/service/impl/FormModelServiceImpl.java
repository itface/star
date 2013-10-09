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
import com.itface.star.system.develop.table.model.FieldModel;
import com.itface.star.system.develop.table.model.FormModel;
import com.itface.star.system.develop.table.model.TableModel;
import com.itface.star.system.develop.table.service.FormModelService;
import com.itface.star.system.develop.table.service.TableModelService;
import com.itface.star.system.easyui.TreeNode;
@Service
public class FormModelServiceImpl implements FormModelService{

	@Autowired
	private BaseDao<FormModel> dao;
	@Autowired
	private TableModelService tableModelService;
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

	@Override
	public String getFormHtml(long formid) {
		// TODO Auto-generated method stub
		StringBuffer html = new StringBuffer();
		html.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">").append("\n");
		html.append("	<html>").append("\n");
		html.append("		<head>").append("</br>");
		html.append("			<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">").append("</br>");
		html.append("			<link rel=\"stylesheet\" type=\"text/css\" href=\"<c:url value='/resources/script/easyui/css/icon.css'/>\">").append("</br>");
		html.append("			<script src=\"<c:url value='/resources/script/jquery-1.7.2.min.js'/>\" type=\"text/javascript\"></script>").append("</br>");
		html.append("			<title>测试生成</title>").append("</br>");
		html.append("			<style>").append("</br>");
		html.append("				.bodyClass{").append("</br>");
		html.append("					background-color:#EEEEEE;").append("</br>");
		html.append("				}").append("</br>");
		html.append("				.outterClass{").append("</br>");
		html.append("					position:absolute;").append("</br>");
		html.append("					width:100%;").append("</br>");
		html.append("					top:0px;").append("</br>");
		html.append("					left:0px;").append("</br>");
		html.append("				}").append("</br>");
		html.append("				.innerClass{").append("</br>");
		html.append("					width:1024px;").append("</br>");
		html.append("					height:100%;").append("</br>");
		html.append("					margin:0 auto;").append("</br>");
		html.append("					background-color:#ffffff;").append("</br>");
		html.append("				}").append("</br>");
		html.append("				.titleClass{").append("</br>");
		html.append("					height:80px;").append("</br>");
		html.append("					width:100%;").append("</br>");
		html.append("					margin-top:20px;").append("</br>");
		html.append("					padding-top:30px;").append("</br>");
		html.append("					line-height:50px;").append("</br>");
		html.append("					text-align:center;").append("</br>");
		html.append("					font-family: 宋体;").append("</br>");
		html.append("					font-weight: bold;").append("</br>");
		html.append("					font-size: 22px;").append("</br>");
		html.append("				}").append("</br>");
		html.append("				.contentClass{").append("</br>");
		html.append("					width:100%;").append("</br>");
		html.append("					font-family: 宋体;").append("</br>");
		html.append("					font-size: 12px;").append("</br>");
		html.append("				}").append("</br>");
		html.append("				.contentTableClass{").append("</br>");
		html.append("					width:100%;").append("</br>");
		html.append("				}").append("</br>");
		html.append("				.contentTableTrClass{").append("</br>");
		html.append("					line-height:35px").append("</br>");
		html.append("				}").append("</br>");
		html.append("				.contentTableTdLabelClass{").append("</br>");
		html.append("					width:15%;").append("</br>");
		html.append("					text-align:right;").append("</br>");
		html.append("				}").append("</br>");
		html.append("				.contentTableTdDataClass{").append("</br>");
		html.append("					width:35%;").append("</br>");
		html.append("				}").append("</br>");
		html.append("			</style>").append("</br>");
		html.append("		</head>").append("</br>");
		html.append("	<body class=\"bodyClass\">").append("</br>");
		html.append("		<form>").append("</br>");
		html.append("			<div class=\"outterClass\">").append("</br>");
		html.append("				<div class=\"innerClass\">").append("</br>");
		html.append("					<div class=\"titleClass\">测试标题</div>").append("</br>");
		html.append("					<div class=\"contentClass\">").append("</br>");
		html.append("						<table class='contentTableClass'>").append("</br>");
		html.append("							").append("</br>");
		FormModel form = this.find(formid);
		if(form!=null){
			TableModel mainTable = form.getMainTableModel();
			Set<TableModel> subTables = form.getSubTableModels();
			if(mainTable!=null){
				Set<FieldModel> mainFields = mainTable.getFieldModels();
				if(mainFields!=null){
					Iterator<FieldModel> it= mainFields.iterator();
					int i=0;
					while(it.hasNext()){
						i++;
						FieldModel f = it.next();
						String name = f.getName();
						String text = f.getText();
						String style = f.getStyle();
						String defalutValue = f.getDefaultvalue();
						StringBuffer td = new StringBuffer();
						if("select".equals(style)){
							td.append("<select id='"+name+"' name='"+name+"'>").append("</br>");
							if(defalutValue!=null&&!"".equals(defalutValue)){
								String[] vs = defalutValue.split(" ");
								for(int j=0;j<vs.length;j++){
									String value = "";
									String n="";
									if(vs[j].contains(":")){
										value = vs[j].substring(vs[j].lastIndexOf(":")+1);
										n = vs[j].substring(0,vs[j].lastIndexOf(":")-1);
									}else{
										value = vs[j];
										n = vs[j];
									}
									td.append("	<option value='"+value+"'>"+n+"</option>").append("</br>");
								}
							}
							td.append("</select>").append("</br>");
						}else if("radio".equals(style)){
							if(defalutValue!=null&&!"".equals(defalutValue)){
								String[] vs = defalutValue.split(" ");
								for(int j=0;j<vs.length;j++){
									String value = "";
									String n="";
									if(vs[j].contains(":")){
										value = vs[j].substring(vs[j].lastIndexOf(":")+1);
										n = vs[j].substring(0,vs[j].lastIndexOf(":")-1);
									}else{
										value = vs[j];
										n = vs[j];
									}
									td.append("<input type='radio' value='"+value+"' name='"+name+"'/><label>"+n+"</label>&nbsp;").append("</br>");
								}
							}
							
						}else if("checkbox".equals(style)){
							if(defalutValue!=null&&!"".equals(defalutValue)){
								String[] vs = defalutValue.split(" ");
								for(int j=0;j<vs.length;j++){
									String value = "";
									String n="";
									if(vs[j].contains(":")){
										value = vs[j].substring(vs[j].lastIndexOf(":")+1);
										n = vs[j].substring(0,vs[j].lastIndexOf(":")-1);
									}else{
										value = vs[j];
										n = vs[j];
									}
									td.append("<input type='checkbox' value='"+value+"' name='"+name+"'/><label>"+n+"</label>&nbsp;").append("</br>");
								}
							}
						}else if("date".equals(style)){
							
						}else{
							td.append("<input type='text' value='"+defalutValue+"' name='"+name+"' id='"+name+"'/>").append("</br>");
						}
						if(i%2==0){
							html.append("								<td  class='contentTableTdLabelClass'>").append("</br>");
							html.append("									<label>"+text+"：</label>").append("</br>");
							html.append("								</td>").append("</br>");
							html.append("								<td class='contentTableTdDataClass'>").append("</br>");
							html.append("									").append(td.toString()).append("</br>");
							html.append("								</td>").append("</br>");
							html.append("							</tr>").append("</br>");
							
						}else{
							html.append("							<tr class='contentTableTrClass'>").append("</br>");
							html.append("								<td  class='contentTableTdLabelClass'>").append("</br>");
							html.append("									<label>"+text+"：</label>").append("</br>");
							html.append("								</td>").append("</br>");
							html.append("								<td class='contentTableTdDataClass'>").append("</br>");
							html.append("									").append(td.toString()).append("</br>");
							html.append("								</td>").append("</br>");
						}
					}
				}
			}
			
		}
		html.append("						</table>").append("</br>");
		html.append("					</div>").append("</br>");
		html.append("				</div>").append("</br>");
		html.append("			</div>").append("</br>");
		html.append("		</form>").append("</br>");
		html.append("	</body>").append("</br>");
		html.append("</html>").append("</br>");
		return html.toString();
	}
}
