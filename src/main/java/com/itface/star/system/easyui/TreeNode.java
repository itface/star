package com.itface.star.system.easyui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.itface.star.system.org.model.Group;
import com.itface.star.system.org.model.Menu;
import com.itface.star.system.org.model.Model;
import com.itface.star.system.org.model.Operation;
import com.itface.star.system.org.model.Organization;
import com.itface.star.system.org.model.Role;
import com.itface.star.system.org.model.User;

public class TreeNode implements Serializable {

	private static final long serialVersionUID = 9221715439546089587L;
	
	private String id;//
	private String text;//
	private int displayOrder;
	private String state="closed";//  'open' or 'closed'
	private TreeNodeAttributes attributes=new TreeNodeAttributes();
	public List<TreeNode> children = new ArrayList<TreeNode>();
	private boolean checked=false;


	public TreeNode(){
		
	}
	public TreeNode(Model model){
		if(model!=null){
			this.id=TreeNodeAttributes.NODETYPE_MODEL+model.getId();
			this.text=model.getName();
			this.displayOrder=model.getDisplayorder();
			this.state="closed";
			attributes.setId(model.getId());
			attributes.setNodetype(TreeNodeAttributes.NODETYPE_MODEL);
		}
	}
	public TreeNode(Group group){
		if(group!=null){
			this.id=TreeNodeAttributes.NODETYPE_GROUP+group.getId();
			this.text=group.getName();
			this.state="closed";
			attributes.setId(group.getId());
			attributes.setNodetype(TreeNodeAttributes.NODETYPE_GROUP);
		}
	}
	public TreeNode(Role role,Set<Role> userRoles){
		if(role!=null){
			this.id=TreeNodeAttributes.NODETYPE_ROLE+role.getId();
			this.text=role.getRolename();
			this.state="open";
			attributes.setId(role.getId());
			attributes.setNodetype(TreeNodeAttributes.NODETYPE_ROLE);
			if(userRoles!=null&&userRoles.contains(role)){
				this.checked=true;
			}
		}
	}
	public TreeNode(Organization organization){
		if(organization!=null){
			this.id=TreeNodeAttributes.NODETYPE_ORGANIZATION+organization.getId();
			this.text=organization.getName();
			this.displayOrder=organization.getDisplayorder();
			this.state="closed";
			attributes.setId(organization.getId());
			attributes.setNodetype(TreeNodeAttributes.NODETYPE_ORGANIZATION);
		}
	}
	public TreeNode(User user){
		if(user!=null){
			this.id=TreeNodeAttributes.NODETYPE_USER+user.getId();
			this.text=user.getUsername()+"("+user.getUserid()+")";
			this.state="open";
			attributes.setId(user.getId());
			attributes.setNodetype(TreeNodeAttributes.NODETYPE_USER);
		}
	}
	public TreeNode(Menu menu){
		if(menu!=null){
			this.id=TreeNodeAttributes.NODETYPE_MENU+menu.getId();
			this.text=menu.getName();
			this.displayOrder=menu.getDisplayorder();
			this.state="open";
			attributes.setUrl(menu.getUrl());
			attributes.setNodetype(TreeNodeAttributes.NODETYPE_MENU);
			attributes.setId(menu.getId());
		}
	}
	public TreeNode(Menu menu,Set<Operation> ops){
		if(menu!=null){
			this.id=TreeNodeAttributes.NODETYPE_MENU+menu.getId();
			this.text=menu.getName();
			this.displayOrder=menu.getDisplayorder();
			this.state="open";
			attributes.setUrl(menu.getUrl());
			attributes.setNodetype(TreeNodeAttributes.NODETYPE_MENU);
			attributes.setId(menu.getId());
			if(ops!=null&&ops.size()>0){
				Iterator<Operation> it = ops.iterator();
				while(it.hasNext()){
					Operation op = it.next();
					if(op.getMenu().getId()==menu.getId()){
						TreeNode n = new TreeNode();
						n.setId(TreeNodeAttributes.NODETYPE_OPERATION+op.getId());
						n.setText(op.getName());
						n.state="open";
						n.attributes.setId(op.getId());
						n.attributes.setNodetype(TreeNodeAttributes.NODETYPE_OPERATION);
						this.getChildren().add(n);
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
	public List<TreeNode> getChildren() {
		return children;
	}
	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}


	
//	@Override
//	public int compareTo(TreeNode treeNode) {
//		// TODO Auto-generated method stub
//		TreeNode m = (TreeNode)treeNode;
//	    return this.getDisplayOrder()-m.getDisplayOrder() ;
//	}


	

}
