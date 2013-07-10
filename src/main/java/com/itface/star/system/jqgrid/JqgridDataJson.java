package com.itface.star.system.jqgrid;

import java.util.List;

public class JqgridDataJson<T> {

	private String total;
	private String page;
	private String records;
	private List<T> rows;
	public JqgridDataJson(List<T> list){
		this.total=list.size()+"";
		this.page="1";
		this.records=list.size()+"";
		this.rows=list;
		
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getRecords() {
		return records;
	}
	public void setRecords(String records) {
		this.records = records;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	
	
}
