package com.itface.star.system.develop.table.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
@Entity
@Table(name="sys_develop_formsetting")
public class FormSetting implements Serializable{

	
	private static final long serialVersionUID = -6122493370935401760L;

	@Id
	@TableGenerator(name = "formsetting_gen", //该表主键生成策略的名称,被@GeneratedValue.generator引用。
	                table = "tb_generator",       //表生成策略所持久化的表名。
	                pkColumnName = "gen_name",    //在持久化的表中，该主键生成策略所对应键值的名称。
	                valueColumnName = "gen_value", //在持久化的表中， 该主键当前所生成的值，它的值将会随着每次创建而加。
	                pkColumnValue = "sys_develop_formsetting_pk",//在持久化的表中，该生成策略所对应的主键
	                initialValue = 100,             //默认主键值为50
	                allocationSize = 1)           //每次主键值增加的大小
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "formsetting_gen")
	private long id;
	
	@NotEmpty(message = "外观类型不可以为空")
	@Pattern(regexp = "[^'<>=\\\\]*", message = "外观类型不能包含特殊字符")
	@Column(name="edittype",length = 20)
	private String edittype;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_id", referencedColumnName = "id")
	private FieldModel fieldModel = new FieldModel();
	
	
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
		if(!(obj instanceof FormSetting)){
			return false;
		}
		FormSetting obj2 = (FormSetting)obj;
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
	public String getEdittype() {
		return edittype;
	}
	public void setEdittype(String edittype) {
		this.edittype = edittype;
	}
	public FieldModel getFieldModel() {
		return fieldModel;
	}
	public void setFieldModel(FieldModel fieldModel) {
		this.fieldModel = fieldModel;
	}
	
}
