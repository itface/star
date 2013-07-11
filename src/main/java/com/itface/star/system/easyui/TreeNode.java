package com.itface.star.system.easyui;

import java.io.Serializable;

import com.itface.star.system.org.model.Menu;
import com.itface.star.system.org.model.Model;

public class TreeNode implements Serializable {

	private static final long serialVersionUID = 9221715439546089587L;
	
	private long id;//
	private String text;//
	private int displayOrder;
	private String state="closed";//  'open' or 'closed'
	private TreeNodeAttributes attributes=new TreeNodeAttributes();

	public TreeNode(){
		
	}
	public TreeNode(Model model){
		if(model!=null){
			this.id=model.getId();
			this.text=model.getName();
			this.displayOrder=model.getDisplayorder();
			this.state="closed";
		}
	}
	public TreeNode(Menu menu){
		if(menu!=null){
			this.id=menu.getId();
			this.text=menu.getName();
			this.displayOrder=menu.getDisplayorder();
			this.state="open";
			attributes.setUrl(menu.getUrl());
			attributes.setMenuNode(true);
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	public TreeNodeAttributes getAttributes() {
		return attributes;
	}
	public void setAttributes(TreeNodeAttributes attributes) {
		this.attributes = attributes;
	}

	
//	@Override
//	public int compareTo(TreeNode treeNode) {
//		// TODO Auto-generated method stub
//		TreeNode m = (TreeNode)treeNode;
//	    return this.getDisplayOrder()-m.getDisplayOrder() ;
//	}


	

}
