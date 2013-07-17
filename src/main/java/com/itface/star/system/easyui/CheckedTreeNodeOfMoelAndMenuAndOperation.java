package com.itface.star.system.easyui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.itface.star.system.org.model.Menu;
import com.itface.star.system.org.model.Model;
import com.itface.star.system.org.model.Operation;

public class CheckedTreeNodeOfMoelAndMenuAndOperation implements Serializable {

	private static final long serialVersionUID = 9221715439546089587L;
	
	private String id;//
	private String text;//
	private String state="closed";//  'open' or 'closed'
	private TreeNodeAttributes attributes=new TreeNodeAttributes();
	public boolean leaf;
	public List<CheckedTreeNodeOfMoelAndMenuAndOperation> children = new ArrayList<CheckedTreeNodeOfMoelAndMenuAndOperation>();
	private boolean checked=false;

	public CheckedTreeNodeOfMoelAndMenuAndOperation(){
		
	}
	public CheckedTreeNodeOfMoelAndMenuAndOperation(Model model){
		if(model!=null){
			this.id=TreeNodeAttributes.NODETYPE_MODEL+model.getId();
			this.text=model.getName();
			this.state="closed";
			this.attributes.setId(model.getId());
			attributes.setNodetype(TreeNodeAttributes.NODETYPE_MODEL);
		}
	}
	public CheckedTreeNodeOfMoelAndMenuAndOperation(Menu menu,Set<Menu> menus,Set<Operation> operations){
		if(menu!=null){
			this.id=TreeNodeAttributes.NODETYPE_MENU+menu.getId();
			this.text=menu.getName();
			this.state="open";
			this.attributes.setId(menu.getId());
			attributes.setNodetype(TreeNodeAttributes.NODETYPE_MENU);
			Set<Operation> ops = menu.getOperations();
			//如果有操作，则操作为叶子节点，设置操作节点的选中状态
			if(ops!=null&&ops.size()>0){
				Iterator<Operation> it = ops.iterator();
				while(it.hasNext()){
					Operation op = it.next();
					CheckedTreeNodeOfMoelAndMenuAndOperation opNode = new CheckedTreeNodeOfMoelAndMenuAndOperation();
					opNode.setId(TreeNodeAttributes.NODETYPE_OPERATION+op.getId());
					opNode.setText(op.getName());
					opNode.setLeaf(true);
					opNode.state="open";
					opNode.attributes.setId(op.getId());
					opNode.attributes.setNodetype(TreeNodeAttributes.NODETYPE_OPERATION);
					if(operations!=null&&operations.size()>0){
						Iterator<Operation> itt = operations.iterator();
						while(itt.hasNext()&&op.getId()==itt.next().getId()){
							opNode.setChecked(true);
							break;
						}
					}
					this.getChildren().add(opNode);
				}
			}else{
				//如果菜单下没有操作，则菜单为叶子节点，设置菜单节点的选中状态
				if(menus!=null&&menus.size()>0){
					Iterator<Menu> itt = menus.iterator();
					while(itt.hasNext()&&menu.getId()==itt.next().getId()){
						this.setChecked(true);
						break;
					}
				}
			}
		}
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
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
	public List<CheckedTreeNodeOfMoelAndMenuAndOperation> getChildren() {
		return children;
	}
	public void setChildren(List<CheckedTreeNodeOfMoelAndMenuAndOperation> children) {
		this.children = children;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	
//	@Override
//	public int compareTo(TreeNode treeNode) {
//		// TODO Auto-generated method stub
//		TreeNode m = (TreeNode)treeNode;
//	    return this.getDisplayOrder()-m.getDisplayOrder() ;
//	}


	

}
