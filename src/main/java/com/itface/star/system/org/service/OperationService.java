package com.itface.star.system.org.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.itface.star.system.org.model.Operation;

public interface OperationService {

	public void add(long menuid,Operation operation);
	public void update(long menuid,Operation operation);
	public void remove(long id);
	public void removeList(long[] idArray);
	public JSONObject findOperationJqgirdJsonByMenuid(long menuid);
	public List<Operation> findOperationByMenuid(long menuid);
	public List<Operation> findOperationByIds(Long[] ids);
	public Operation find(long id);
}
