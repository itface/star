package com.itface.star.system.org.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
@Entity
@Table(name="sys_org_organization")
public class Organization implements Comparable<Organization>,Serializable{

	private static final long serialVersionUID = -2308622234565944879L;

	@Id
	@TableGenerator(name = "organization_gen", //该表主键生成策略的名称,被@GeneratedValue.generator引用。
	                table = "tb_generator",       //表生成策略所持久化的表名。
	                pkColumnName = "gen_name",    //在持久化的表中，该主键生成策略所对应键值的名称。
	                valueColumnName = "gen_value", //在持久化的表中， 该主键当前所生成的值，它的值将会随着每次创建而加。
	                pkColumnValue = "sys_org_organization_pk",//在持久化的表中，该生成策略所对应的主键
	                initialValue = 1,             //默认主键值为50
	                allocationSize = 1)           //每次主键值增加的大小
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "organization_gen")
    private long id;
 

	
    @NotEmpty(message = "名称不可以为空")
    @Pattern(regexp = "[^'<>=\\\\]*", message = "名称不能包含特殊字符")
    @Length(max=100,message="名称长度不能超过100")
    @Column(name="name",length = 100)
    //@Length(min = 2, max = 5)
    private String name;
    
    
    //显示顺序
   	@Min(value=1,message = "显示顺序必须大于0")
   	@Column(name="displayorder")
    private Integer displayorder;
   	
   	@Column(name="parentid")
	private long parentid;
   	
   	@OneToMany(fetch=FetchType.LAZY,cascade = {CascadeType.REFRESH,CascadeType.REMOVE},mappedBy="organization")  
    private Set<User> users= new HashSet<User>();


	@Override
	public int compareTo(Organization o) {
		// TODO Auto-generated method stub
		Organization m = (Organization)o;
	    return m.getDisplayorder() - this.getDisplayorder();
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Integer getDisplayorder() {
		return displayorder;
	}


	public void setDisplayorder(Integer displayorder) {
		this.displayorder = displayorder;
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
	

    
   	

    
}
