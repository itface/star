package com.itface.star.system.develop.table.model;

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
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.itface.star.system.org.model.Menu;
@Entity
@Table(name="sys_develop_table")
public class TableModel implements Serializable{

	private static final long serialVersionUID = 7963066861227765751L;
	
	@Id
	@TableGenerator(name = "table_gen", //该表主键生成策略的名称,被@GeneratedValue.generator引用。
	                table = "tb_generator",       //表生成策略所持久化的表名。
	                pkColumnName = "gen_name",    //在持久化的表中，该主键生成策略所对应键值的名称。
	                valueColumnName = "gen_value", //在持久化的表中， 该主键当前所生成的值，它的值将会随着每次创建而加。
	                pkColumnValue = "sys_develop_table_pk",//在持久化的表中，该生成策略所对应的主键
	                initialValue = 100,             //默认主键值为50
	                allocationSize = 1)           //每次主键值增加的大小
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "table_gen")
	private long id;
	
	@NotEmpty(message = "表名不可以为空")
	@Pattern(regexp = "[A-Za-z0-9_]*", message = "表名只能包含大小写字母、数字和下划线")
	@Length(max=20,message="表名长度不能超过20")
	@Column(name="name",length = 20)
	private String name;
	
	@NotEmpty(message = "名称不可以为空")
	@Pattern(regexp = "[^'<>=\\\\]*", message = "名称不能包含特殊字符")
	@Length(max=100,message="名称长度不能超过100")
	@Column(name="text",length = 100)
	private String text;
	
	@OneToMany(fetch=FetchType.LAZY,cascade = {CascadeType.REFRESH,CascadeType.REMOVE},mappedBy="tableModel")  
    private Set<FieldModel> fieldModels= new HashSet<FieldModel>();
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<FieldModel> getFieldModels() {
		return fieldModels;
	}
	public void setFieldModels(Set<FieldModel> fieldModels) {
		this.fieldModels = fieldModels;
	}
	
}
