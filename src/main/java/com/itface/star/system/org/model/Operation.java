package com.itface.star.system.org.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.validator.constraints.NotEmpty;
@Entity
@Table(name="sys_org_operation")
public class Operation implements Serializable{

	private static final long serialVersionUID = 3270623324068610697L;
	@Id
	@TableGenerator(name = "operation_gen", //该表主键生成策略的名称,被@GeneratedValue.generator引用。
	                table = "tb_generator",       //表生成策略所持久化的表名。
	                pkColumnName = "gen_name",    //在持久化的表中，该主键生成策略所对应键值的名称。
	                valueColumnName = "gen_value", //在持久化的表中， 该主键当前所生成的值，它的值将会随着每次创建而加。
	                pkColumnValue = "sys_org_operation_pk",//在持久化的表中，该生成策略所对应的主键
	                initialValue = 1,             //默认主键值为50
	                allocationSize = 1)           //每次主键值增加的大小
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "operation_gen")
    private long id;

    //名称
	@NotEmpty(message = "操作名称不可以为空！")
	@Column(name="name",length = 100)
    private String name;
    
    //操作标志read：读取，create:新增，modify：修改，delete：删除
    //在rest风格中，将请求方式映射为上述四种操作，设计为字符串，以方便今后扩展
	@NotEmpty(message = "操作标志不可以为空！")
	@Column(name="actionflag",length = 30)
    private String actionflag;
    
    //操作url地址,比如:/user/*
	@NotEmpty(message = "url地址不可以为空！")
	@Column(name="url",length = 150)
    private String url;
    //所属菜单
	@ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false,fetch = FetchType.LAZY)
    @JoinColumn(name="menu_id")
    private Menu menu;
	
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Menu getMenu() {
		return menu;
	}
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	public String getActionflag() {
		return actionflag;
	}
	public void setActionflag(String actionflag) {
		this.actionflag = actionflag;
	}
	
    
}
