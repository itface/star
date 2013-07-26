package com.itface.star.system.develop.table.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.itface.star.system.develop.table.model.FieldModel;

public interface FieldModelService {
	public List<FieldModel> findAll(long tableid);
	public FieldModel findFieldById(long id);
	public void addField(FieldModel field,long tableid);
	public void updateField(FieldModel field,long tableid);
	public void deleteField(String ids);
	public JSONObject findGridDataOfField(long tableid);
}
