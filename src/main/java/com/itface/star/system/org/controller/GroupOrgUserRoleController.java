package com.itface.star.system.org.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itface.star.system.org.service.GroupOrgUserRoleService;

@Controller
@RequestMapping(value="/system/org/gour")
public class GroupOrgUserRoleController {

	@Autowired
	private GroupOrgUserRoleService groupOrgUserRoleService; 

	/**
	 * 权限组编辑页面里，显示权限组包含的角色的树
	 * @param groupid
	 * @return
	 */
	@RequestMapping(value=("/roleTreeOfGroup/{groupid}"),method=RequestMethod.GET)
	public @ResponseBody Object getRoleTreeOfGroup(@PathVariable long groupid){
		return groupOrgUserRoleService.findSubCheckedTreeNodeJsonOfRoleByOrgid(groupid);
	}
	/**
	 * 权限组编辑页面里，显示权限组包含的用户的树,用户节点是页子节点，有选择框,用于选择用户到该权限组
	 * @param groupid
	 * @param orgid
	 * @return
	 */
	@RequestMapping(value=("/userTreeOfGroup/{groupid}/{orgid}"),method=RequestMethod.GET)
	public @ResponseBody Object getUserTreeOfGroup(@PathVariable long groupid,@PathVariable long orgid){
		return groupOrgUserRoleService.findSubCheckedTreeNodeJsonOfOrgAndUserByOrgidAndGroupId(orgid, groupid);
	}
	/**
	 * 用户编辑页面里，显示用户所在的权限组里的树
	 * @param userid
	 * @param groupid
	 * @return
	 */
	@RequestMapping(value=("/groupTreeOfUser/{userid}/{groupid}"),method=RequestMethod.GET)
	public @ResponseBody Object getGroupTreeOfUser(@PathVariable long userid,@PathVariable long groupid){
		return groupOrgUserRoleService.findSubGroupTreeJsonByUseridAndParentGroupid(userid, groupid);
	}
	/**
	 * 用户编辑页面里,显示用户能访问的所有资源，包括模块、菜单和操作
	 * @param userid
	 * @param modelid
	 * @return
	 */
	@RequestMapping(value=("/resourceTreeOfUser/{userid}/{modelid}"),method=RequestMethod.GET)
	public @ResponseBody Object getResourceTreeOfUser(@PathVariable String userid,@PathVariable long modelid){
		return groupOrgUserRoleService.findSubTreeNodeJsonOfModelAndMenuAndOperationByUserid(userid, modelid);
	}
	/**
	 * 用户编辑页面里，显示用户包含的角色的树(不包括用户所在权限组里的角色)
	 * @param userid
	 * @return
	 */
	@RequestMapping(value=("/roleTreeOfUser/{userid}"),method=RequestMethod.GET)
	public @ResponseBody Object getRoleTreeOfUser(@PathVariable long userid){
		return groupOrgUserRoleService.findRoleTreeJsonOfUser(userid);
	}
}
