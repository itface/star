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
@Table(name="sys_develop_formmodel")
public class FormModel implements Serializable{

	
	
	private static final long serialVersionUID = 2935517445613988216L;

	@Id
	@TableGenerator(name = "formmodel_gen", //该表主键生成策略的名称,被@GeneratedValue.generator引用。
	                table = "tb_generator",       //表生成策略所持久化的表名。
	                pkColumnName = "gen_name",    //在持久化的表中，该主键生成策略所对应键值的名称。
	                valueColumnName = "gen_value", //在持久化的表中， 该主键当前所生成的值，它的值将会随着每次创建而加。
	                pkColumnValue = "sys_develop_formmodel_pk",//在持久化的表中，该生成策略所对应的主键
	                initialValue = 100,             //默认主键值为50
	                allocationSize = 1)           //每次主键值增加的大小
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "formmodel_gen")
	private long id;
	
	@NotEmpty(message = "名称不可以为空")
	@Pattern(regexp = "[A-Za-z0-9_]*", message = "名称只能包含大小写字母、数字和下划线")
	@Length(max=100,message="名称长度不能超过100")
	@Column(name="name",length = 100)
	private String name;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_id", referencedColumnName = "id")
	private TableModel mainTableModel = new TableModel();
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="sys_develop_subformmodel_table",inverseJoinColumns=@JoinColumn(name="tableId",referencedColumnName="id"),joinColumns=@JoinColumn(name="formId",referencedColumnName="id"))
	private Set<TableModel> subTableModels= new HashSet<TableModel>();
	
	@Transient
	private String mainTableModelId;
	@Transient
	private String subTableModelIds;
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
	
	public TableModel getMainTableModel() {
		return mainTableModel;
	}
	public void setMainTableModel(TableModel mainTableModel) {
		this.mainTableModel = mainTableModel;
	}
	public Set<TableModel> getSubTableModels() {
		return subTableModels;
	}
	public void setSubTableModels(Set<TableModel> subTableModels) {
		this.subTableModels = subTableModels;
	}
	
	public String getMainTableModelId() {
		TableModel t =  this.getMainTableModel();
		if(t!=null){
			return t.getId()+"";
		}
		return "";
	}
	public void setMainTableModelId(String mainTableModelId) {
		this.mainTableModelId = mainTableModelId;
		if(mainTableModelId!=null&&!"".equals(mainTableModelId)){
			TableModel t = new TableModel();
			t.setId(Long.parseLong(mainTableModelId));
			this.setMainTableModel(t);
		}
	}
	public String getSubTableModelIds() {
		Set<TableModel> set =  this.getSubTableModels();
		if(set!=null&&set.size()>0){
			StringBuffer sb = new StringBuffer();
			Iterator<TableModel> it = set.iterator();
			while(it.hasNext()){
				TableModel t = it.next();
				sb.append(t.getId()).append(",");
			}
			return sb.substring(0, sb.lastIndexOf(","));
		}
		return "";
	}
	public void setSubTableModelIds(String subTableModelIds) {
		this.subTableModelIds = subTableModelIds;
		Set<TableModel> set= new HashSet<TableModel>();
		if(subTableModelIds!=null&&!"".equals(subTableModelIds)){
			String[] ids = subTableModelIds.split(",");
			for(int i=0;i<ids.length;i++){
				TableModel t = new TableModel();
				t.setId(Long.parseLong(ids[i]));
				set.add(t);
			}
		}
		this.setSubTableModels(set);
	}
	//	public String getMainTableId() {
//		Set<FieldModel> set =  this.getMainFieldModels();
//		if(set!=null&&set.size()>0){
//			StringBuffer sb = new StringBuffer();
//			Set<TableModel> tables = new HashSet<TableModel>();
//			Iterator<FieldModel> it = set.iterator();
//			while(it.hasNext()){
//				FieldModel f = it.next();
//				TableModel t = f.getTableModel();
//				if(!tables.contains(t)){
//					sb.append(t.getId()).append(",");
//					tables.add(t);
//				}
//			}
//			return sb.substring(0, sb.lastIndexOf(","));
//		}
//		return "";
//	}
//	public String getSubTableIds() {
//		Set<FieldModel> set =  this.getSubFieldModels();
//		if(set!=null&&set.size()>0){
//			StringBuffer sb = new StringBuffer();
//			Set<TableModel> tables = new HashSet<TableModel>();
//			Iterator<FieldModel> it = set.iterator();
//			while(it.hasNext()){
//				FieldModel f = it.next();
//				TableModel t = f.getTableModel();
//				if(!tables.contains(t)){
//					sb.append(t.getId()).append(",");
//					tables.add(t);
//				}
//			}
//			return sb.substring(0, sb.lastIndexOf(","));
//		}
//		return "";
//	}
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
		if(!(obj instanceof FormModel)){
			return false;
		}
		FormModel obj2 = (FormModel)obj;
		if(this.id>0){
			return this.id==obj2.getId();
		}else{
			return false;
		}
	}
}
