package com.itface.star.system.easyui;

public class TreeNodeAttributes {

	public final static String NODETYPE_MODEL="model";
	public final static String NODETYPE_MENU="menu";
	public final static String NODETYPE_OPERATION="operation";
	public final static String NODETYPE_ORGANIZATION="organization";
	//节点对应的数据库id,而非树中的id.因为树是由model和menu两块构成，id可能重复
	private long id;
	private String url;
	private String nodetype;//"model\menu\operation

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	public String getNodetype() {
		return nodetype;
	}

	public void setNodetype(String nodetype) {
		this.nodetype = nodetype;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
}
