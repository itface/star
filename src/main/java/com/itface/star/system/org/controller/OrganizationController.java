package com.itface.star.system.org.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.itface.star.system.org.model.Organization;
import com.itface.star.system.org.service.OrganizationService;

@Controller
@RequestMapping(value="/system/org/organization")
public class OrganizationController {

	@Autowired
	private OrganizationService organizationService; 
	/**
	 * 组织管理首页
	 * @return
	 */
	@RequestMapping
	public ModelAndView index(){
		return new ModelAndView("/system/org/organization");
	}
	/**
	 * 组织树
	 * @param orgid
	 * @return
	 */
	@RequestMapping(value=("/findSons/{orgid}"),method=RequestMethod.GET)
	public @ResponseBody JSONArray getSons(@PathVariable long orgid){
		return organizationService.findSonJson(orgid);
	}
	/**
	 * 新增组织进入的页面
	 * @param parentid
	 * @return
	 */
	@RequestMapping(value=("/newPage/{parentid}"),method=RequestMethod.GET)
	public ModelAndView newPage(@PathVariable long parentid){
		Organization organization = new Organization();
		organization.setParentid(parentid);
		Map<String,Object> map = new HashMap<String,Object>();
		List<Integer> list = organizationService.findSonOrderList(parentid);
		organization.setDisplayorder(list.size()+1);
		list.add(list.size()+1);
		map.put("organization", organization);
		map.put("list", list);
		return new ModelAndView("/system/org/organization_org",map);
	}
	/**
	 * 修改组织进入的页面
	 * @param orgid
	 * @return
	 */
	@RequestMapping(value=("/{orgid}"),method=RequestMethod.GET)
	public ModelAndView get(@PathVariable long orgid){
		Map<String,Object> map = new HashMap<String,Object>();
		Organization organization = organizationService.find(orgid);
		map.put("organization", organization);
		List<Integer> list = organizationService.findOrderList(orgid);
		map.put("list", list);
		return new ModelAndView("/system/org/organization_org",map);
	}
	/**
	 * 新增组织
	 * @param organization
	 * @param result
	 * @return
	 */
	@RequestMapping(value=("/{orgid}"),method=RequestMethod.POST)
	public @ResponseBody String add(@Valid Organization organization,BindingResult result){
		if (!result.hasErrors()) { 
			organizationService.add(organization);
			return "S";
		}else{
			List<ObjectError> errors = result.getAllErrors();
			StringBuffer sb = new StringBuffer();
			for(ObjectError error : errors){
				sb.append(error.getDefaultMessage()).append("\r");
			}
			return sb.toString();
		}
	}
	/**
	 * 修改组织
	 * @param organization
	 * @param result
	 * @return
	 */
	@RequestMapping(value=("/{orgid}"),method=RequestMethod.PUT)
	public @ResponseBody String update(@Valid Organization organization,BindingResult result){
		if (!result.hasErrors()) { 
			organizationService.update(organization);
			return "S";
		}else{
			List<ObjectError> errors = result.getAllErrors();
			StringBuffer sb = new StringBuffer();
			for(ObjectError error : errors){
				sb.append(error.getDefaultMessage()).append("\r");
			}
			return sb.toString();
		}
	}
	/**
	 * 删除组织
	 * @param orgid
	 */
	@RequestMapping(value=("/{orgid}"),method=RequestMethod.DELETE)
	public @ResponseBody void delete(@PathVariable long orgid){
		organizationService.remove(orgid);
	}
}
