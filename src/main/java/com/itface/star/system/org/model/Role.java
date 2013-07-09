package com.itface.star.system.org.model;

import java.io.Serializable;
import java.util.HashSet;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

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
	@NotEmpty(message = "帐号不可以为空！")
	@Column(name="rolename",length = 100)
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
    
}
