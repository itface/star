package com.itface.star.system.org.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
@Entity
@Table(name="sys_org_role")
public class Role implements Serializable{

	private static final long serialVersionUID = -5761282198596551570L;
	@Id
	@TableGenerator(name = "role_gen", //该表主键生成策略的名称,被@GeneratedValue.generator引用。
	                table = "tb_generator",       //表生成策略所持久化的表名。
	                pkColumnName = "gen_name",    //在持久化的表中，该主键生成策略所对应键值的名称。
	                valueColumnName = "gen_value", //在持久化的表中， 该主键当前所生成的值，它的值将会随着每次创建而加。
	                pkColumnValue = "sys_org_role_pk",//在持久化的表中，该生成策略所对应的主键
	                initialValue = 1,             //默认主键值为50
	                allocationSize = 1)           //每次主键值增加的大小
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "role_gen")
    private long id;
	@NotEmpty(message = "角色名称不可以为空")
	@Pattern(regexp = "[^'<>=\\\\]*", message = "角色名称不能包含特殊字符")
	@Column(name="rolename",length = 100)
	@Length(max=100,message="角色名称长度不能超过150")
    private String rolename;
    //角色编码，用于生成权限框架的惟一标识
    //private String roleCode;
	@ManyToMany(fetch = FetchType.LAZY,mappedBy="roles",targetEntity = com.itface.star.system.org.model.User.class)
	private Set<User> users= new HashSet<User>();
    //角色可以操作的菜单
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="sys_org_role_menu",joinColumns=@JoinColumn(name="roleId",referencedColumnName="id"),inverseJoinColumns=@JoinColumn(name="menuId",referencedColumnName="id"))
    private Set<Menu> menus= new HashSet<Menu>();
    //角色可操作的操作功能【对应于增、删、查、改等功能】
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="sys_org_role_operation",joinColumns=@JoinColumn(name="roleId",referencedColumnName="id"),inverseJoinColumns=@JoinColumn(name="operationId",referencedColumnName="id"))
    private Set<Operation> operations= new HashSet<Operation>();
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="sys_org_role_model",joinColumns=@JoinColumn(name="roleId",referencedColumnName="id"),inverseJoinColumns=@JoinColumn(name="modelId",referencedColumnName="id"))
    private Set<Model> models= new HashSet<Model>();
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public Set<Menu> getMenus() {
		return menus;
	}
	public void setMenus(Set<Menu> menus) {
		this.menus = menus;
	}
	public Set<Operation> getOperations() {
		return operations;
	}
	public void setOperations(Set<Operation> operations) {
		this.operations = operations;
	}
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	public Set<Model> getModels() {
		return models;
	}
	public void setModels(Set<Model> models) {
		this.models = models;
	}
	//    public String getOperationIds(){
//    	StringBuffer ids = new StringBuffer();
//    	if(this.operations!=null&&operations.size()>0){
//    		Iterator<Operation> it = operations.iterator();
//    		while(it.hasNext()){
//    			Operation op = it.next();
//    			ids.append(op.getId()).append(",");
//    		}
//    		return ids.substring(0, ids.lastIndexOf(","));
//    	}
//    	return ids.toString();
//    }
	/**
	 * 构件该角色下的权限菜单，以模块id为key，Menu_tree为value,Menu_tree里放的是key模块下一级的所有模块集合和菜单集合
	 * @return
	 */
    public Map<Long,Menu_tree> getMenuTree(){
    	Map<Long,Menu_tree> map = new HashMap<Long,Menu_tree>();
    	if(this.models!=null&&this.models.size()>0){
    		Iterator<Model> it = models.iterator();
    		while(it.hasNext()){
    			Model model = it.next();
    			//如果父节点是0，则加入到key为0的对象中,因为菜单只能在模块下，所以根节点只可能是模块，不可能是菜单
    			if(model.getParentmodel()==0){
    				if(map.containsKey(0)){
    					map.get(0).getModels().add(model);
    				}else{
    					Menu_tree tree = new Menu_tree();
    					tree.getModels().add(model);
    					map.put(new Long(0), tree);
    				}
    			}
    			//把父节点为model节点的对象，放到以modelid为key的对象中
    			Iterator<Model> it2 = models.iterator();
    			while(it2.hasNext()){
    				Model model2 = it2.next();
    				if(model2.getParentmodel()==model.getId()){
    					if(map.containsKey(model.getId())){
	    					map.get(model.getId()).getModels().add(model2);
	    				}else{
	    					Menu_tree tree = new Menu_tree();
	    					tree.getModels().add(model2);
	    					map.put(model.getId(), tree);
	    				}
    				}
    			}
    			if(menus!=null&&menus.size()>0){
    				Iterator<Menu> itt = menus.iterator();
    	    		while(itt.hasNext()){
    	    			Menu menu = itt.next();
    	    			if(menu.getModel().getId()==model.getId()){
    	    				if(map.containsKey(model.getId())){
    	    					map.get(model.getId()).getMenus().add(menu);
    	    				}else{
    	    					Menu_tree tree = new Menu_tree();
    	    					tree.getMenus().add(menu);
    	    					map.put(model.getId(), tree);
    	    				}
    	    			}
    	    		}
    			}
    		}
    	}
    	return map;
    }
    /*
	[2.1]boolean型，计算(f ? 0 : 1); 
	[2.2]byte,char,short型，计算(int); 
	[2.3]long型，计算(int) (f ^ (f>>>32)); 
	[2.4]float型，计算Float.floatToIntBits(afloat); 
	[2.5]double型，计算Double.doubleToLongBits(adouble)得到一个long，再执行[2.3]; 
	*/
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		int result = 17;
		result = 37*result+(int) (id ^ (id>>>32));
		//result = 37*result+(name==null?0:name.hashCode());
		//result = 37*result+displayOrder;
		//result = 37*result+(this.url==null?0:url.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(!(obj instanceof Role)){
			return false;
		}
		Role obj2 = (Role)obj;
		if(this.id>0){
			return this.id==obj2.getId();
		}else{
			return false;
		}
	}
}
