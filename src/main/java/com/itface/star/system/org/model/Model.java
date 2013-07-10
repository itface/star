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
	
	@NotEmpty(message = "模块名称不可以为空！")
	@Pattern(regexp = "[^'<>=\\\\]+", message = "名称不能包含特殊字符！")
	@Column(name="name",length = 100)
    private String name;
    
    //显示顺序
	@Min(value=1,message = "显示顺序必须大于0！")
	@Column(name="displayorder")
    private Integer displayorder;
	
	@Column(name="parentmodel")
	private long parentmodel;
	
	//只有在删除模块时，会删除模块下的菜单，更新模块信息时，不更新模块下的菜单
	@OneToMany(fetch=FetchType.LAZY,cascade = {CascadeType.REFRESH,CascadeType.REMOVE},mappedBy="model")  
    private Set<Menu> menus= new HashSet<Menu>();

	@Override
	public int compareTo(Model model) {
		// TODO Auto-generated method stub
		Model m = (Model)model;
	    return m.getDisplayorder() - this.getDisplayorder();
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
}
