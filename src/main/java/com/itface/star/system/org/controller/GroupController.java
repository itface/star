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

import com.itface.star.system.org.model.Group;
import com.itface.star.system.org.service.GroupService;
@Controller
@RequestMapping(value="/system/org/group")
public class GroupController {

	@Autowired
	private GroupService groupService;
	
	/**
	 * 权限组管理菜单
	 * @return
	 */
	@RequestMapping
	public ModelAndView index(){
		return new ModelAndView("/system/org/group");
	}
	/**
	 * 权限组树找下一层的数据
	 * @param groupid
	 * @return
	 */
	@RequestMapping(value=("/findSons/{groupid}"),method=RequestMethod.GET)
	public @ResponseBody JSONArray getSons(@PathVariable long groupid){
		return groupService.findSonJson(groupid);
	}
	/**
	 * 进入新增权限组页面
	 * @param parentid
	 * @return
	 */
	@RequestMapping(value=("/newPage/{parentid}"),method=RequestMethod.GET)
	public ModelAndView newPage(@PathVariable long parentid){
		Group group = new Group();
		group.setParentid(parentid);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("group", group);
		map.put("checkedUserIds", "");
		return new ModelAndView("/system/org/group_group",map);
	}
	/**
	 * 进入修改权限组信息页面
	 * @param groupid
	 * @return
	 */
	@RequestMapping(value=("/{groupid}"),method=RequestMethod.GET)
	public ModelAndView get(@PathVariable long groupid){
		Map<String,Object> map = new HashMap<String,Object>();
		Group group = groupService.find(groupid);
		map.put("group", group);
		return new ModelAndView("/system/org/group_group",map);
	}
	/**
	 * 新增权限组
	 * @param openRoleTreeFlag
	 * @param checkedUserids
	 * @param checkedRoleids
	 * @param group
	 * @param result
	 * @return
	 */
	@RequestMapping(value=("/{groupid}"),method=RequestMethod.POST)
	public @ResponseBody String add(boolean openRoleTreeFlag,String checkedUserids,String checkedRoleids,@Valid Group group,BindingResult result){
		if (!result.hasErrors()) { 
			groupService.add(openRoleTreeFlag,checkedUserids,checkedRoleids,group);
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
	 * 修改权限组
	 * @param openRoleTreeFlag
	 * @param checkedUserids
	 * @param checkedRoleids
	 * @param group
	 * @param result
	 * @return
	 */
	@RequestMapping(value=("/{groupid}"),method=RequestMethod.PUT)
	public @ResponseBody String update(boolean openRoleTreeFlag,String checkedUserids,String checkedRoleids,@Valid Group group,BindingResult result){
		if (!result.hasErrors()) { 
			groupService.update(openRoleTreeFlag,checkedUserids,checkedRoleids,group);
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
	 * 删除权限组
	 * @param groupid
	 */
	@RequestMapping(value=("/{groupid}"),method=RequestMethod.DELETE)
	public @ResponseBody void delete(@PathVariable long groupid){
		groupService.remove(groupid);
	}
}
