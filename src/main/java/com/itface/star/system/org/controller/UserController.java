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

import com.itface.star.system.org.model.User;
import com.itface.star.system.org.service.UserService;

@Controller
@RequestMapping(value="/system/org/user")
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * 组织管理功能中管理用户的页面
	 * @param orgid
	 * @return
	 */
	@RequestMapping(value=("/{orgid}"),method=RequestMethod.GET)
	public ModelAndView index(@PathVariable long orgid){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("orgid", orgid);
		return new ModelAndView("/system/org/organization_org_user",map);
	}
	/**
	 * 用户管理子表数据源
	 * @param orgid
	 * @return
	 */
	@RequestMapping(value=("/{orgid}/grid"),method=RequestMethod.GET)
	public @ResponseBody Object getGridData(@PathVariable long orgid){
		JSONObject json = userService.findAllUserJsonByOrgid(orgid);
		return json==null?"":json;
	}
	/**
	 * 新增或者修改用户信息进入的页面
	 * @param orgid
	 * @param id
	 * @return
	 */
	@RequestMapping(value=("/{orgid}/grid/{id}"),method=RequestMethod.GET)
	public ModelAndView getGridRowData(@PathVariable long orgid,@PathVariable long id){
		User user = null;
		List<Integer> orderList =  userService.findUserOrderListByOrgid(orgid);
		if(id>0){
			user = userService.find(id);
		}else{
			user = new User();
			user.setDisplayorder(orderList.size()+1);
			orderList.add(orderList.size()+1);
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("user", user);
		map.put("orgid", orgid);
		map.put("orderList", orderList);
		return new ModelAndView("/system/org/organization_org_user_row",map);
	}
	/**
	 * 新增用户
	 * @param orgid
	 * @param openRoleTreeFlag
	 * @param checkedRoleIds
	 * @param user
	 * @param result
	 * @return
	 */
	@RequestMapping(value=("/{orgid}/grid/{id}"),method=RequestMethod.POST)
	public @ResponseBody String add(@PathVariable long orgid,boolean openRoleTreeFlag,String checkedRoleIds,@Valid User user,BindingResult result){
		if (!result.hasErrors()) { 
			userService.add(orgid,user,openRoleTreeFlag,checkedRoleIds);
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
	 * 更新用户
	 * @param orgid
	 * @param openRoleTreeFlag
	 * @param checkedRoleIds
	 * @param user
	 * @param result
	 * @return
	 */
	@RequestMapping(value=("/{orgid}/grid/{id}"),method=RequestMethod.PUT)
	public @ResponseBody String update(@PathVariable long orgid,boolean openRoleTreeFlag,String checkedRoleIds,@Valid User user,BindingResult result){
		if (!result.hasErrors()) { 
			userService.update(orgid,user,openRoleTreeFlag,checkedRoleIds);
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
	 * 删除用户
	 * @param id
	 */
	@RequestMapping(value=("/{orgid}/grid/{id}"),method=RequestMethod.DELETE)
	public @ResponseBody void delete(@PathVariable long id){
		userService.remove(id);
	}
	/**
	 * 批量删除用户
	 * @param idArr
	 */
	@RequestMapping(value=("/{orgid}/grid"),method=RequestMethod.DELETE)
	public @ResponseBody void deleteList(long[] idArr){
		userService.removeList(idArr);
	}
}
