package com.itface.star.system.develop.table.service;

import java.util.List;

import net.sf.json.JSONArray;

import com.itface.star.system.develop.table.model.TableModel;

public interface TableModelService {

	public List<TableModel> findAll();
	public TableModel findTableById(long id);
	public TableModel addTable(TableModel table);
	public TableModel updateTable(TableModel table);
	public void deleteTable(long id);
	public JSONArray findTableTree();
	public JSONArray findCheckedTableTree();
}
