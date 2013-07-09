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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.validator.constraints.NotEmpty;
@Entity
@Table(name="sys_org_menu")
public class Menu implements  Comparable<Menu>, Serializable{

	private static final long serialVersionUID = 8472650163977939990L;
	@Id
	@TableGenerator(name = "menu_gen", //该表主键生成策略的名称,被@GeneratedValue.generator引用。
	                table = "tb_generator",       //表生成策略所持久化的表名。
	                pkColumnName = "gen_name",    //在持久化的表中，该主键生成策略所对应键值的名称。
	                valueColumnName = "gen_value", //在持久化的表中， 该主键当前所生成的值，它的值将会随着每次创建而加。
	                pkColumnValue = "sys_org_menu_pk",//在持久化的表中，该生成策略所对应的主键
	                initialValue = 1,             //默认主键值为50
	                allocationSize = 1)           //每次主键值增加的大小
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "menu_gen")
    private long id;
    @NotEmpty(message = "菜单名称不可以为空！")
	@Column(name="name",length=100)
    private String name;
    //显示顺序
    @NotEmpty(message = "菜单显示顺序不可以为空！")
	@Column(name="displayOrder")
    private int displayOrder;
    //url地址
	@NotEmpty(message = "菜单url不可以为空！")
	@Column(name="url",length = 150)
    private String url;
    //所属模块
    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false,fetch = FetchType.LAZY)
    @JoinColumn(name="model_id")
    private Model model;

    @OneToMany(fetch=FetchType.LAZY,cascade = { CascadeType.REFRESH, CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REMOVE },mappedBy="menu")  
    private Set<Operation> operations= new HashSet<Operation>();
 
    @Override
    public int compareTo(Menu menu) {
       Menu m = (Menu)menu;
       return m.getDisplayOrder() - this.getDisplayOrder();
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


	public int getDisplayOrder() {
		return displayOrder;
	}


	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public Model getModel() {
		return model;
	}


	public void setModel(Model model) {
		this.model = model;
	}


	
}
