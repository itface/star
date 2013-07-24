package com.itface.star.system.org.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
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
	@NotEmpty(message = "名称不可以为空")
	@Pattern(regexp = "[^'<>=\\\\]*", message = "名称不能包含特殊字符")
	@Length(max=100,message="名称长度不能超过100")
	@Column(name="name",length = 100)
    private String name;
    
    //操作标志read：读取，create:新增，modify：修改，delete：删除
    //在rest风格中，将请求方式映射为上述四种操作，设计为字符串，以方便今后扩展
	@NotEmpty(message = "操作标志不可以为空")
	@Pattern(regexp = "[^'<>=\\\\]*", message = "操作标志不能包含特殊字符")
	@Column(name="actionflag",length = 30)
    private String actionflag;
    
    //操作url地址,比如:/user/*
	@NotEmpty(message = "url不可以为空")
	@Pattern(regexp = "[^'<>=\\\\]*", message = "url不能包含特殊字符")
	@Length(max=150,message="url长度不能超过150")
	@Column(name="url",length = 150)
    private String url;
	
    //所属菜单
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="menu_id")
    private Menu menu;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="sys_org_role_operation",inverseJoinColumns=@JoinColumn(name="roleId",referencedColumnName="id"),joinColumns=@JoinColumn(name="operationId",referencedColumnName="id"))
	private Set<Role> roles= new HashSet<Role>();
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
		//result = 37*result+(actionflag==null?0:actionflag.hashCode());
		//result = 37*result+(this.url==null?0:url.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(!(obj instanceof Operation)){
			return false;
		}
		Operation obj2 = (Operation)obj;
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
	public static List<String> actionFlagList() {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<String>();
		list.add("read");
		list.add("modify");
		list.add("delete");
		list.add("create");
		return list;
	}
    
}
