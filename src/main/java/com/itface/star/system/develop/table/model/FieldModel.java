package com.itface.star.system.develop.table.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="sys_develop_table_field")
public class FieldModel implements Serializable{


	private static final long serialVersionUID = -1139183031146896777L;

	@Id
	@TableGenerator(name = "field_gen", //该表主键生成策略的名称,被@GeneratedValue.generator引用。
	                table = "tb_generator",       //表生成策略所持久化的表名。
	                pkColumnName = "gen_name",    //在持久化的表中，该主键生成策略所对应键值的名称。
	                valueColumnName = "gen_value", //在持久化的表中， 该主键当前所生成的值，它的值将会随着每次创建而加。
	                pkColumnValue = "sys_develop_table_field_pk",//在持久化的表中，该生成策略所对应的主键
	                initialValue = 100,             //默认主键值为50
	                allocationSize = 1)           //每次主键值增加的大小
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "field_gen")
	private long id;
	
	@NotEmpty(message = "字段名不可以为空")
	@Pattern(regexp = "[A-Za-z0-9_]*", message = "字段名只能包含大小写字母、数字和下划线")
	@Length(max=20,message="字段名长度不能超过20")
	@Column(name="name",length = 20)
	private String name;
	
	@NotEmpty(message = "名称不可以为空")
	@Pattern(regexp = "[^'<>=\\\\]*", message = "名称不能包含特殊字符")
	@Length(max=100,message="名称长度不能超过100")
	@Column(name="text",length = 100)
	private String text;
	
	@NotEmpty(message = "类型不可以为空")
	@Pattern(regexp = "[^'<>=\\\\]*", message = "类型不能包含特殊字符")
	@Length(max=20,message="类型长度不能超过20")
	@Column(name="fieldtype",length = 20)
	private String fieldtype;
	

	@Column(name="fieldlength")
	private int fieldlength;
	
	@ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "tablemodel_id", referencedColumnName = "id")
	private TableModel tableModel = new TableModel();
	
	
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
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getFieldtype() {
		return fieldtype;
	}
	public void setFieldtype(String fieldtype) {
		this.fieldtype = fieldtype;
	}
	public int getFieldlength() {
		return fieldlength;
	}
	public void setFieldlength(int fieldlength) {
		this.fieldlength = fieldlength;
	}
	public TableModel getTableModel() {
		return tableModel;
	}
	public void setTableModel(TableModel tableModel) {
		this.tableModel = tableModel;
	}

}
