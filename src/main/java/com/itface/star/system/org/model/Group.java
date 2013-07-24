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
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="sys_org_group")
public class Group implements Serializable{
	

	private static final long serialVersionUID = 864149727514782439L;
	
	@Id
	@TableGenerator(name = "group_gen", //该表主键生成策略的名称,被@GeneratedValue.generator引用。
	                table = "tb_generator",       //表生成策略所持久化的表名。
	                pkColumnName = "gen_name",    //在持久化的表中，该主键生成策略所对应键值的名称。
	                valueColumnName = "gen_value", //在持久化的表中， 该主键当前所生成的值，它的值将会随着每次创建而加。
	                pkColumnValue = "sys_org_group_pk",//在持久化的表中，该生成策略所对应的主键
	                initialValue = 100,             //默认主键值为50
	                allocationSize = 1)           //每次主键值增加的大小
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "group_gen")
    private long id;
	
	@NotEmpty(message = "名称不可以为空")
	@Pattern(regexp = "[^'<>=\\\\]*", message = "名称不能包含特殊字符")
	@Length(max=100,message="名称长度不能超过100")
	@Column(name="name",length = 100)
    private String name;
	
	@Column(name="parentid")
	private long parentid;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="sys_org_group_role",joinColumns=@JoinColumn(name="groupId",referencedColumnName="id"),inverseJoinColumns=@JoinColumn(name="roleId",referencedColumnName="id"))
    private Set<Role> roles= new HashSet<Role>();
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="sys_org_group_user",joinColumns=@JoinColumn(name="groupId",referencedColumnName="id"),inverseJoinColumns=@JoinColumn(name="userId",referencedColumnName="id"))
    private Set<User> users= new HashSet<User>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public long getParentid() {
		return parentid;
	}

	public void setParentid(long parentid) {
		this.parentid = parentid;
	}
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
		if(!(obj instanceof Group)){
			return false;
		}
		Group obj2 = (Group)obj;
		if(this.id>0){
			return this.id==obj2.getId();
		}else{
			return false;
		}
	}
}
