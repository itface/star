package com.itface.star.system.shiro.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.core.io.ClassPathResource;

import com.itface.star.system.baseDao.BaseDao;
import com.itface.star.system.org.model.Menu;
import com.itface.star.system.org.model.Operation;
import com.itface.star.system.org.model.Role;
import com.itface.star.system.shiro.service.IAuthService;
import com.itface.star.system.util.OrderedProperties;

public class AuthServiceImpl implements IAuthService{

	 //注意/r/n前不能有空格
    private static final String CRLF= "\r\n";
    private static final String LAST_AUTH_STR= "/** =authc\r\n";
    
    @Resource
    private ShiroFilterFactoryBean shiroFilterFactoryBean;
    
    @Resource
    private BaseDao dao;
 
    @Override
    public String loadFilterChainDefinitions() {
       StringBuffer sb = new StringBuffer("");
       sb.append(getFixedAuthRule())
       .append(getDynaAuthRule())
       .append(getRestfulOperationAuthRule())
       .append(LAST_AUTH_STR);
       System.out.println("************************权限配置开始***********************");
       System.out.println(sb.toString());
       System.out.println("************************权限配置结束***********************");
       return sb.toString();
    }
    
    //生成restful风格功能权限规则
    private String getRestfulOperationAuthRule() {
       
       List<Operation> operations = dao.find("from Operation o");
       
       Set<String> restfulUrls = new HashSet<String>();
       for(Operation op : operations) {
           restfulUrls.add(op.getUrl());
       }
       StringBuffer sb  = new StringBuffer("");
       for(Iterator<String> urls =  restfulUrls.iterator(); urls.hasNext(); ) {
           String url = urls.next();
           if(! url.startsWith("/")) {
              url = "/"+ url ;
           }
           sb.append(url).append("=").append("authc, rest[").append(url).append("]").append(CRLF);
       }
       return sb.toString();
       
       
    }
    
    
    //根据角色，得到动态权限规则
    private String getDynaAuthRule() {
       
       StringBuffer sb = new StringBuffer("");
       Map<String, Set<String>> rules = new HashMap<String,Set<String>>();
       
       List<Role> roles = dao.find("from Role r left join fetch r.menus");
       for(Role role: roles) {
           for(Iterator<Menu> menus =role.getMenus().iterator(); menus.hasNext();) {
              String url = menus.next().getUrl();
              if(!url.startsWith("/")) {
                  url = "/"+ url;
              }
              if(!rules.containsKey(url)) {
                  rules.put(url, new HashSet<String>());
              }
              rules.get(url).add((role.getId()+""));
           }
       }
       
       for(Map.Entry<String, Set<String>> entry :rules.entrySet()) {
           sb.append(entry.getKey()).append(" = ").append("authc,roleOrFilter").append(entry.getValue()).append(CRLF);
       }
       
       return sb.toString();
    }
    
    
    //得到固定权限验证规则串
    private String getFixedAuthRule() {
       
       StringBuffer sb = new StringBuffer("");
       
       ClassPathResource cp = new ClassPathResource("fixed_auth_res.properties");
       Properties properties = new OrderedProperties();
       try{
           properties.load(cp.getInputStream());
       } catch(IOException e) {
           throw new RuntimeException("load fixed_auth_res.properties error!");
       }
       for(Iterator its = properties.keySet().iterator();its.hasNext();) {
           String key = (String)its.next();
           sb.append(key).append(" = ").append(properties.getProperty(key).trim()).append(CRLF);
           
       }      
       return sb.toString();
       
    }
    
    @Override
    //此方法加同步锁,因为spring默认都是单例对象，所以不用static
    public synchronized void reCreateFilterChains() {
       
       AbstractShiroFilter shiroFilter = null;
       try{
           shiroFilter = (AbstractShiroFilter)shiroFilterFactoryBean.getObject();
       } catch(Exception e) {
           throw new RuntimeException("get ShiroFilter from shiroFilterFactoryBean error!");
       }
       
       PathMatchingFilterChainResolver filterChainResolver =(PathMatchingFilterChainResolver)shiroFilter.getFilterChainResolver();
       DefaultFilterChainManager manager =(DefaultFilterChainManager)filterChainResolver.getFilterChainManager();
 
       //清空老的权限控制
       manager.getFilterChains().clear();
       
 
       shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
       shiroFilterFactoryBean.setFilterChainDefinitions(loadFilterChainDefinitions());
       //重新构建生成
       Map<String, String> chains = shiroFilterFactoryBean.getFilterChainDefinitionMap();
        for(Map.Entry<String, String> entry :chains.entrySet()) {
            String url = entry.getKey();
            String chainDefinition =entry.getValue().trim().replace(" ", "");
            manager.createChain(url,chainDefinition);
        }
       
    }
}
