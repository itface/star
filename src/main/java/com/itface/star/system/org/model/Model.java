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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
@Entity
@Table(name="sys_org_model")
public class Model implements Comparable<Model>,Serializable{

	private static final long serialVersionUID = 6700106612734285933L;
	
	
	@Id
	@TableGenerator(name = "model_gen", //该表主键生成策略的名称,被@GeneratedValue.generator引用。
	                table = "tb_generator",       //表生成策略所持久化的表名。
	                pkColumnName = "gen_name",    //在持久化的表中，该主键生成策略所对应键值的名称。
	                valueColumnName = "gen_value", //在持久化的表中， 该主键当前所生成的值，它的值将会随着每次创建而加。
	                pkColumnValue = "sys_org_model_pk",//在持久化的表中，该生成策略所对应的主键
	                initialValue = 1,             //默认主键值为50
	                allocationSize = 1)           //每次主键值增加的大小
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "model_gen")
	private long id;
	
	@NotEmpty(message = "模块名称不可以为空")
	@Pattern(regexp = "[^'<>=\\\\]*", message = "名称不能包含特殊字符")
	@Length(max=100,message="模块名称长度不能超过100")
	@Column(name="name",length = 100)
    private String name;
    
    //显示顺序
	@Min(value=1,message = "显示顺序必须大于0")
	@Column(name="displayorder")
    private Integer displayorder;
	
	@Column(name="parentmodel")
	private long parentmodel;
	
	//只有在删除模块时，会删除模块下的菜单，更新模块信息时，不更新模块下的菜单
	@OneToMany(fetch=FetchType.LAZY,cascade = {CascadeType.REFRESH,CascadeType.REMOVE},mappedBy="model")  
    private Set<Menu> menus= new HashSet<Menu>();

	@ManyToMany(fetch = FetchType.LAZY,mappedBy="models")
	private Set<Role> roles= new HashSet<Role>();
	
	@Override
	public int compareTo(Model model) {
		// TODO Auto-generated method stub
		Model m = (Model)model;
	    return m.getDisplayorder() - this.getDisplayorder();
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
		if(!(obj instanceof Model)){
			return false;
		}
		Model obj2 = (Model)obj;
		if(this.id>0){
			return this.id==obj2.getId();
		}else{
			return false;
		}
	}

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

	public Integer getDisplayorder() {
		return displayorder;
	}

	public void setDisplayorder(Integer displayorder) {
		this.displayorder = displayorder;
	}

	public Set<Menu> getMenus() {
		return menus;
	}

	public void setMenus(Set<Menu> menus) {
		this.menus = menus;
	}

	public long getParentmodel() {
		return parentmodel;
	}

	public void setParentmodel(long parentmodel) {
		this.parentmodel = parentmodel;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
}
