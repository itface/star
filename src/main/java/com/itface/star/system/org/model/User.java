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
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
@Entity
@Table(name="sys_org_user")
public class User implements Serializable{

	private static final long serialVersionUID = -3574996298189591943L;

	@Id
	@TableGenerator(name = "user_gen", //该表主键生成策略的名称,被@GeneratedValue.generator引用。
	                table = "tb_generator",       //表生成策略所持久化的表名。
	                pkColumnName = "gen_name",    //在持久化的表中，该主键生成策略所对应键值的名称。
	                valueColumnName = "gen_value", //在持久化的表中， 该主键当前所生成的值，它的值将会随着每次创建而加。
	                pkColumnValue = "sys_org_user_pk",//在持久化的表中，该生成策略所对应的主键
	                initialValue = 1,             //默认主键值为50
	                allocationSize = 1)           //每次主键值增加的大小
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "user_gen")
    private Long id;
 
	@NotEmpty(message = "帐号不可以为空！")
	@Column(name="userid",length = 100, unique = true)
	private String userid;

	
    @NotEmpty(message = "姓名不可以为空！")
    @Column(name="username",length = 100)
    //@Length(min = 2, max = 5)
    private String username;

    @NotEmpty(message = "密码不可以为空！")
    @Column(name="password",length = 50)
    private String password= "123456";

     
    @ManyToMany(fetch = FetchType.LAZY)
	//@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name="sys_org_user_role",joinColumns=@JoinColumn(name="username",referencedColumnName="username"),inverseJoinColumns=@JoinColumn(name="roleId",referencedColumnName="id"))
    private Set<Role> roles= new HashSet<Role>();
    
    /**
     * 得到该用户可访问的模块，模块中的菜单将实例化
     * @return
     */
    public List<Model> getModels() {
       List<Model> models = new ArrayList<Model>();
       for(Iterator<Menu> its = this.getMenus().iterator(); its.hasNext();){
           Menu m = its.next(); 
           if(!models.contains(m.getModel())) {
              Model model = new Model();
              model.setId(m.getModel().getId());
              model.setDisplayOrder(m.getDisplayOrder());
              model.setModelName(m.getModel().getModelName());
              model.getMenus().add(m);
              models.add(model);
           } else{
              for(Model model : models) {
                  if(model.getId() == m.getModel().getId()) {
                     model.getMenus().add(m);
                     break;
                  }
              }
           }
       }
       Collections.sort(models);
       return models;
    }
    
    /**
     * 得到用户可访问的菜单资源
     * @return
     */
    public Set<Menu> getMenus() {
       Set<Menu> menus = new HashSet<Menu>();
       for(Iterator<Role> role = this.getRoles().iterator(); role.hasNext();) {
           menus.addAll(role.next().getMenus());
       }
       return menus;
       
    }
    
    // 用户菜操作功能权限集合字符串描述
    public Set<String> getOperationPermissionsAsString() {
       Set<String> pomissions = new HashSet<String>();
       Iterator<Role> roles = this.getRoles().iterator();
       
       Operation op;
       
       Map<String, HashSet<String>> p_map = new HashMap<String,HashSet<String>>();
       
       while(roles.hasNext()) {
           Iterator<Operation> operations =roles.next().getOperations().iterator();
           while(operations.hasNext()) {
              op = operations.next();
              String key = op.getUrl();
              if(!key.startsWith("/")) {
                  key = "/"+ key;
              }
              if(p_map.get(key) == null) {
                  p_map.put(key, new HashSet<String>());
              }
              p_map.get(key).add(op.getActionflag());
           }
       }
       //构建形如：[doc:read, moveuser:modify, users:read,user:modify,read,create]的权限字串
       for(Entry<String, HashSet<String> > entry :p_map.entrySet()) {
           pomissions.add(entry.getKey() + ":"+ entry.getValue().toString().replace("[", "").replace("]", "").replace(" ", ""));
       }
       
       return pomissions;
    }
 
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
    /**
     * 得到我的全部权限
     * @return
     */
    public Set<String> getPermissionsAsString() {
       Set<String> permissions = new HashSet<String>();
       //permissions.addAll(getMenuPermissionsAsString());
       permissions.addAll(getOperationPermissionsAsString());
       return  permissions;
    }
 
    // 得到用户角色字符串描述
    public Set<String> getRolesAsString() {
       Set<String> str_roles = new HashSet<String>();
       Iterator<Role> roles = this.getRoles().iterator();
       while(roles.hasNext()) {
           str_roles.add(roles.next().getId()+"");
       }
       return str_roles;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

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
    
}
