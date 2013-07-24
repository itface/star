package com.itface.star.system.org.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.itface.star.system.org.model.Role;
import com.itface.star.system.org.service.GroupOrgUserRoleService;
import com.itface.star.system.org.service.RoleService;

@Controller
@RequestMapping(value="/system/org/role")
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	
	/**
	 * 角色管理首页
	 * @return
	 */
	@RequestMapping
	public ModelAndView index(){
		return new ModelAndView("/system/org/role_listGrid");
	}
	/**
	 * 角色管理子表数据源
	 * @return
	 */
	@RequestMapping(value=("/grid"),method=RequestMethod.GET)
	public @ResponseBody Object getGridData(){
		JSONObject json = roleService.findAllRoleJqgirdJson();
		return json==null?"":json;
	}
	/**
	 * 新增和修改角色进入的页面
	 * @param roleid
	 * @return
	 */
	@RequestMapping(value=("/grid/{roleid}"),method=RequestMethod.GET)
	public ModelAndView getGridRowData(@PathVariable long roleid){
		Role role = null;
		if(roleid>0){
			role = roleService.find(roleid);
		}else{
			role = new Role();
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("role", role);
		return new ModelAndView("/system/org/role_listGrid_row",map);
	}
	/**
	 * 新增角色
	 * @param roleid
	 * @param checkedModelIds
	 * @param checkedMenuIds
	 * @param checkedOperationIds
	 * @param role
	 * @param result
	 * @return
	 */
	@RequestMapping(value=("/grid/{roleid}"),method=RequestMethod.POST)
	public @ResponseBody String add(@PathVariable long roleid,Long[] checkedModelIds,Long[] checkedMenuIds,Long[] checkedOperationIds,@Valid Role role,BindingResult result){
		if (!result.hasErrors()) { 
			roleService.add(role,checkedModelIds,checkedMenuIds,checkedOperationIds);
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
	 * 修改角色
	 * @param roleid
	 * @param allmodelIds
	 * @param allMenuIds
	 * @param allOperationIds
	 * @param checkedModelIds
	 * @param checkedMenuIds
	 * @param checkedOperationIds
	 * @param role
	 * @param result
	 * @return
	 */
	@RequestMapping(value=("/grid/{roleid}"),method=RequestMethod.PUT)
	public @ResponseBody String update(@PathVariable long roleid,Long[] allmodelIds,Long[] allMenuIds,Long[] allOperationIds,Long[] checkedModelIds,Long[] checkedMenuIds,Long[] checkedOperationIds,@Valid Role role,BindingResult result){
		if (!result.hasErrors()) { 
			roleService.update(role,allmodelIds,allMenuIds,allOperationIds,checkedModelIds,checkedMenuIds,checkedOperationIds);
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
	 * 删除角色
	 * @param roleid
	 */
	@RequestMapping(value=("/grid/{roleid}"),method=RequestMethod.DELETE)
	public @ResponseBody void delete(@PathVariable long roleid){
		roleService.remove(roleid);
	}
	/**
	 * 批量删除角色
	 * @param roleIds
	 */
	@RequestMapping(value=("/grid"),method=RequestMethod.DELETE)
	public @ResponseBody void deleteList(long[] roleIds){
		roleService.delete(roleIds);
	}
	
	
}
