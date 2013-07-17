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
	 * 系统菜单,带权限判断，带出modelid下一级的有权限的模块和菜单
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
	 * 菜统菜单，不带权限判断，带出modelid下一级的所有模块和菜单
	 * @param modelid
	 * @return
	 */
	@RequestMapping(value=("/subTreeNodesOfModelAndMenuWithoutAuth/{modelid}"),method=RequestMethod.GET)
	public @ResponseBody JSONArray menuTreeAll(@PathVariable long modelid){
		return resourceService.findSubTreeNodeJsonOfModelAndMenuWithoutAuthByModelid(modelid);
	}
	/**
	 * 资源菜单，不带权限判断，带出modelid下一级的所有模块和菜单和操作
	 * @param roleid
	 * @param modelid
	 * @return
	 */
	@RequestMapping(value=("/subTreeNodesOfModelAndMenuAndOperation/{roleid}/{modelid}"),method=RequestMethod.GET)
	public @ResponseBody Object getModelAndMenuAndOperation(@PathVariable long roleid,@PathVariable long modelid){
		JSONArray json = resourceService.findSubTreeNodeJsonOfModelAndMenuAndOperation(roleid,modelid);
		return json==null?"":json;
	}
}
