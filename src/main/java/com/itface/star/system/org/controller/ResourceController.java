package com.itface.star.system.org.controller;

import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itface.star.system.org.model.Menu_tree;
import com.itface.star.system.org.service.ResourceService;

@Controller
@RequestMapping(value="/system/org/resource")
public class ResourceController {
 
	
	@Autowired
	private ResourceService resourceService;

	/**
	 * 显示菜单功能,带权限判断，带出modelid下一级的有权限的模块和菜单
	 * @param modelid
	 * @return
	 */
	@RequestMapping(value=("/subTreeNodesOfModelAndMenuWithAuth/{modelid}"),method=RequestMethod.GET)
	public @ResponseBody JSONArray menuTree(@PathVariable long modelid){
		Subject currentUser = SecurityUtils.getSubject();
		Object menuTree = currentUser.getSession().getAttribute("menuTree"); 
		String userid = (String)currentUser.getPrincipal();
		return resourceService.findSubTreeNodesOfModelAndMenuWithAuthByModelid(userid,modelid,menuTree==null?null:(Map<Long,Menu_tree>)menuTree);
	}
	/**
	 * 操作管理功能中显示的菜单树，显示系统中所有菜单的树，不带权限判断，带出modelid下一级的所有模块和菜单
	 * @param modelid
	 * @return
	 */
	@RequestMapping(value=("/subTreeNodesOfModelAndMenuWithoutAuth/{modelid}"),method=RequestMethod.GET)
	public @ResponseBody JSONArray menuTreeAll(@PathVariable long modelid){
		return resourceService.findSubTreeNodeJsonOfModelAndMenuWithoutAuthByModelid(modelid);
	}
	/**
	 * 角色管理功能中，用于显示所有资源，带出modelid下一级的所有模块和菜单和操作,如果角色包含了该菜单或菜单下的操作，则该节点处于选中状态
	 * @param roleid
	 * @param modelid
	 * @return
	 */
	@RequestMapping(value=("/subTreeNodesOfModelAndMenuAndOperation/{roleid}/{modelid}"),method=RequestMethod.GET)
	public @ResponseBody Object getModelAndMenuAndOperation(@PathVariable long roleid,@PathVariable long modelid){
		JSONArray json = resourceService.findSubTreeCheckedNodeJsonOfModelAndMenuAndOperation(roleid,modelid);
		return json==null?"":json;
	}
	/**
	 * 权限组管理功能、用户管理和角色管理中，用于显示某角色能访问的所有资源，带出roleid角色在modelid下一级的所有模块和菜单和操作
	 * @param roleid
	 * @param modelid
	 * @return
	 */
	@RequestMapping(value=("/subTreeNodesOfModelAndMenuAndOperationByRole/{roleid}/{modelid}"),method=RequestMethod.GET)
	public @ResponseBody Object getModelAndMenuAndOperationByRole(@PathVariable long roleid,@PathVariable long modelid){
		JSONArray json = resourceService.findSubTreeNodeJsonOfModelAndMenuAndOperationByRoleid(roleid, modelid);
		return json==null?"":json;
	}
}
