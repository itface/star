package com.itface.star.system.org.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
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
	
    @NotEmpty(message = "菜单名称不可以为空")
    @Pattern(regexp = "[^'<>=\\\\]*", message = "名称不能包含特殊字符")
    @Length(max=100,message="菜单名称长度不能超过100")
	@Column(name="name",length=100)
    private String name;
    
    //显示顺序
    @Min(value=1,message = "显示顺序必须大于0")
	@Column(name="displayorder")
    private Integer displayorder;
    
    //url地址
	@NotEmpty(message = "url不可以为空！")
	@Pattern(regexp = "[^'<>=\\\\]*", message = "url不能包含特殊字符！")
	@Length(max=150,message="url长度不能超过150")
	@Column(name="url",length = 150)
    private String url;

    //所属模块
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id", referencedColumnName = "id")
    private Model model=new Model();

    @OneToMany(fetch=FetchType.LAZY,cascade = { CascadeType.REFRESH,CascadeType.REMOVE },mappedBy="menu")  
    private Set<Operation> operations= new HashSet<Operation>();
 
    @ManyToMany(fetch = FetchType.LAZY,mappedBy="menus")
	private Set<Role> roles= new HashSet<Role>();
    @Override
    public int compareTo(Menu menu) {
       Menu m = (Menu)menu;
       return this.getDisplayorder()-m.getDisplayorder() ;
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



	public Set<Operation> getOperations() {
		return operations;
	}


	public void setOperations(Set<Operation> operations) {
		this.operations = operations;
	}

	public Set<Role> getRoles() {
		return roles;
	}


	public void setRoles(Set<Role> roles) {
		this.roles = roles;
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
		if(!(obj instanceof Menu)){
			return false;
		}
		Menu obj2 = (Menu)obj;
		if(this.id>0){
			return this.id==obj2.getId();
		}else{
			return false;
		}
	}
	private boolean validateStringEquals(String s1,String s2){
		if((s1==null&&s2==null)||(s1!=null&&s2!=null&&s1.equals(s2))){
			return true;
		}else{
			return false;
		}
	}
//	public Map<Long,Menu_tree> getModelPath(){
//		Map<Long,Menu_tree> map = new HashMap<Long,Menu_tree>();
//		String modelpath = this.modelpath;
//		String[] modelIds = modelpath.split("/");
//		for(int i=0;i<modelIds.length&&modelIds[i]!=null&&!"".equals(modelIds[i]);i++){
//			long modelid = Long.parseLong(modelIds[i]);
//			Menu_tree menuNode = new Menu_tree();
//			if(i<modelIds.length-1){
//				long sonModelId = Long.parseLong(modelIds[i+1]);
//				menuNode.getModels().add(sonModelId);
//				map.put(modelid, menuNode);
//			}else{
//				menuNode.getMenus().add(id);
//				map.put(modelid, menuNode);
//			}
//			
//		}
//		return map;
//	}
	


	
}
