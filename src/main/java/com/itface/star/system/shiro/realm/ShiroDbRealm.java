package com.itface.star.system.shiro.realm;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;

import com.itface.star.system.org.model.Operation;
import com.itface.star.system.org.model.Role;
import com.itface.star.system.org.model.User;
import com.itface.star.system.org.service.RoleService;
import com.itface.star.system.org.service.UserService;
/**
 * 在认证、授权内部实现机制中都有提到，最终处理都将交给Real进行处理。
 * 因为在Shiro中，最终是通过Realm来获取应用程序中的用户、角色及权限信息的。
 * 通常情况下，在Realm中会直接从我们的数据源中获取Shiro需要的验证信息。
 * 可以说，Realm是专用于安全框架的DAO. 
 * @author Administrator
 *
 */
public class ShiroDbRealm extends AuthorizingRealm {

	@Resource
	private UserService userService;
	@Resource
	private RoleService roleService;

    public ShiroDbRealm() {
        super();
        // 设置认证token的实现类为用户名密码模式
        this.setAuthenticationTokenClass(UsernamePasswordToken.class);
        //设置验证方式，用户自行设定密码加密方式
        this.setCredentialsMatcher(new CredentialsMatcher() {   
            @Override
            public boolean doCredentialsMatch(AuthenticationToken token,AuthenticationInfo info) {
               UsernamePasswordToken upToken = (UsernamePasswordToken)token;
               String pwd = new String(upToken.getPassword());  
               User user = userService.findByUserid(upToken.getUsername());  
               if(user!=null&&user.getPassword().equals(DigestUtils.md5Hex(pwd))){
            	   return true;
               }
               //用户名或密码不正确
               return false;
            }
        });
     }

	/**
	 *  授权实现
	 * 
	 */
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		SimpleAuthorizationInfo sazi = new SimpleAuthorizationInfo();
		Collection collection = principalCollection.fromRealm(getName());
		if(collection!=null&&collection.size()>0){
			Iterator it = collection.iterator();
			while(it.hasNext()){
				String userid = (String)it.next();
				//取当前用户
				User user = userService.findByUserid(userid);
				//添加角色及权限信息
				if(user.getUserid().equals("admin")){
					List<Role> roles = roleService.findAll();
					if(roles!=null&&roles.size()>0){
						Set<String> str_roles = new HashSet<String>();
						Set<String> pomissions = new HashSet<String>();
						Map<String, HashSet<String>> p_map = new HashMap<String,HashSet<String>>();
					       for(Role role : roles){
					    	   str_roles.add(role.getId()+"");
					    	   Iterator<Operation> operations =role.getOperations().iterator();
					    	   while(operations.hasNext()) {
					    		  Operation op = operations.next();
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
						sazi.addRoles(user.getRolesAsString());
						//构建形如：[doc:read, moveuser:modify, users:read,user:modify,read,create]的权限字串
				       for(Entry<String, HashSet<String> > entry :p_map.entrySet()) {
				           pomissions.add(entry.getKey() + ":"+ entry.getValue().toString().replace("[", "").replace("]", "").replace(" ", ""));
				       }
				       sazi.addStringPermissions(pomissions);
					}
					SecurityUtils.getSubject().getSession().setAttribute("menuTree",null);
				}else{
					sazi.addRoles(user.getRolesAsString());
					sazi.addStringPermissions(user.getPermissionsAsString());
					SecurityUtils.getSubject().getSession().setAttribute("menuTree", user.getMenuTree());
				}
				break;
			}
		}
       return sazi;
	}

	/**
	 * 认证实现
	 * 认证回调函数,登录时调用.
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		User user = userService.findByUserid(token.getUsername());
		if (user != null) {
			//throw new LockedAccountException();//锁定帐号等其它登录异常可以此抛出
			 //要放在作用域中的东西，请在这里进行操作
			return new SimpleAuthenticationInfo(user.getUserid(),user.getPassword(), user.getUsername());
		} 
		return null;
	}
	@Override
    protected void clearCachedAuthorizationInfo(PrincipalCollection pc) {
       SimplePrincipalCollection principals= new SimplePrincipalCollection(pc, getName()); 
       super.clearCachedAuthorizationInfo(pc);
    }

}

