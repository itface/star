package com.itface.star.system.develop.table.service;

import java.util.List;

import net.sf.json.JSONArray;

import com.itface.star.system.develop.table.model.FormModel;

public interface FormModelService {

	public List<FormModel> findAll();
	public JSONArray findFormTree();
	public FormModel find(long id);
	public FormModel add(FormModel form);
	public FormModel update(FormModel form);
	public void delete(long id);
	
	public String getFormSettingGridHtml(long formid);
	public String getFormSettingGridScript(long formid);
}
