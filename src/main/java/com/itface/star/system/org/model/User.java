package com.itface.star.system.org.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
@Entity
@Table(name="sys_org_user")
public class User implements Comparable<User>,Serializable{

	private static final long serialVersionUID = -3574996298189591943L;

	@Id
	@TableGenerator(name = "user_gen", //该表主键生成策略的名称,被@GeneratedValue.generator引用。
	                table = "tb_generator",       //表生成策略所持久化的表名。
	                pkColumnName = "gen_name",    //在持久化的表中，该主键生成策略所对应键值的名称。
	                valueColumnName = "gen_value", //在持久化的表中， 该主键当前所生成的值，它的值将会随着每次创建而加。
	                pkColumnValue = "sys_org_user_pk",//在持久化的表中，该生成策略所对应的主键
	                initialValue = 100,             //默认主键值为50
	                allocationSize = 1)           //每次主键值增加的大小
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "user_gen")
    private long id;
 
	@NotEmpty(message = "帐号不可以为空")
	@Pattern(regexp = "[^'<>=\\\\]*", message = "帐号不能包含特殊字符")
	@Length(max=100,message="帐号长度不能超过100")
	@Column(name="userid",length = 100, unique = true)
	private String userid;

	
    @NotEmpty(message = "姓名不可以为空")
    @Pattern(regexp = "[^'<>=\\\\]*", message = "姓名不能包含特殊字符")
    @Length(max=100,message="姓名长度不能超过100")
    @Column(name="username",length = 100)
    //@Length(min = 2, max = 5)
    private String username;

    //@NotEmpty(message = "密码不可以为空！")
    @Pattern(regexp = "[^'<>=\\\\]*", message = "密码不能包含特殊字符")
    @Length(max=50,message="密码长度不能超过150")
    @Column(name="password",length = 50)
    private String password;

  //显示顺序
    @Min(value=1,message = "显示顺序必须大于0")
	@Column(name="displayorder")
    private Integer displayorder;
    
    @Column(name="status")
    private int status;
    
    @ManyToMany(fetch = FetchType.LAZY)
	//@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name="sys_org_user_role",joinColumns=@JoinColumn(name="userid",referencedColumnName="id"),inverseJoinColumns=@JoinColumn(name="roleId",referencedColumnName="id"))
    private Set<Role> roles= new HashSet<Role>();
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", referencedColumnName = "id")
    private Organization organization=new Organization();

    @ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="sys_org_group_user",inverseJoinColumns=@JoinColumn(name="groupId",referencedColumnName="id"),joinColumns=@JoinColumn(name="userId",referencedColumnName="id"))
	private Set<Group> groups= new HashSet<Group>();
    
//    /**
//     * 得到用户可访问的菜单资源
//     * @return
//     */
//    public Set<Menu> findMenus() {
//       Set<Menu> menus = new HashSet<Menu>();
//       for(Iterator<Role> role = this.getRoles().iterator(); role.hasNext();) {
//           menus.addAll(role.next().getMenus());
//       }
//       return menus;
//       
//    }
    // 用户菜操作功能权限集合字符串描述
//    public Set<String> operationPermissionsAsString() {
//       Set<String> pomissions = new HashSet<String>();
//       Iterator<Role> roles = this.getRoles().iterator();
//       
//       Operation op;
//       
//       Map<String, HashSet<String>> p_map = new HashMap<String,HashSet<String>>();
//       
//       while(roles.hasNext()) {
//           Iterator<Operation> operations =roles.next().getOperations().iterator();
//           while(operations.hasNext()) {
//              op = operations.next();
//              String key = op.getUrl();
//              if(!key.startsWith("/")) {
//                  key = "/"+ key;
//              }
//              if(p_map.get(key) == null) {
//                  p_map.put(key, new HashSet<String>());
//              }
//              p_map.get(key).add(op.getActionflag());
//           }
//       }
//       //构建形如：[doc:read, moveuser:modify, users:read,user:modify,read,create]的权限字串
//       for(Entry<String, HashSet<String> > entry :p_map.entrySet()) {
//           pomissions.add(entry.getKey() + ":"+ entry.getValue().toString().replace("[", "").replace("]", "").replace(" ", ""));
//       }
//       
//       return pomissions;
//    }
 
    // 用户菜单权限集合字符串描述
//  public Set<String> getMenuPermissionsAsString() {
//     Set<String> pomissions= newHashSet<String>();
//     Iterator<Role> roles = this.getRoles().iterator();
//     while (roles.hasNext()) {
//         Iterator<Menu> menus =roles.next().getMenus().iterator();
//         while (menus.hasNext()) {
//            pomissions.add(menus.next().getCode());
//         }
//     }
//     return pomissions;
//  }
//    /**
//     * 得到我的全部权限
//     * @return
//     */
//    public Set<String> permissionsAsString() {
//       Set<String> permissions = new HashSet<String>();
//       //permissions.addAll(getMenuPermissionsAsString());
//       permissions.addAll(operationPermissionsAsString());
//       return  permissions;
//    }
// 
//    // 得到用户角色字符串描述
//    public Set<String> rolesAsString() {
//       Set<String> str_roles = new HashSet<String>();
//       Iterator<Role> roles = this.getRoles().iterator();
//       while(roles.hasNext()) {
//           str_roles.add(roles.next().getId()+"");
//       }
//       return str_roles;
//    }

//    public Map<Long,Menu_tree> findMenuTree(){
//    	Map<Long,Menu_tree> map = new HashMap<Long,Menu_tree>();
//    	if(this.roles!=null&&this.roles.size()>0){
//    		Iterator<Role> it = roles.iterator();
//    		while(it.hasNext()){
//    			Role role = it.next();
//    			Map<Long,Menu_tree> menuNode = role.findMenuTree();
//    			Iterator<Long> itt = menuNode.keySet().iterator();
//    			while(itt.hasNext()){
//    				long key = itt.next();
//    				if(map.containsKey(key)){
//    					Menu_tree mapTree = map.get(key);
//    					mapTree.getModels().addAll(menuNode.get(key).getModels());
//    					mapTree.getMenus().addAll(menuNode.get(key).getMenus());
//    				}else{
//    					map.put(key, menuNode.get(key));
//    				}
//    			}
//    		}
//    	}
//    	return map;
//    }

	

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getDisplayorder() {
		return displayorder;
	}

	public void setDisplayorder(Integer displayorder) {
		this.displayorder = displayorder;
	}

	public long getId() {
		return id;
	}
	

	public Set<Group> getGroups() {
		return groups;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}

	@Override
	public int compareTo(User o) {
		// TODO Auto-generated method stub
		 User m = (User)o;
	     return this.getDisplayorder()-m.getDisplayorder() ;
	}

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
		if(!(obj instanceof User)){
			return false;
		}
		User obj2 = (User)obj;
		if(this.id>0){
			return this.id==obj2.getId();
		}else{
			return false;
		}
	}
}
