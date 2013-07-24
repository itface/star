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

	@RequestMapping(value=("/groupRoleTree/{groupid}"),method=RequestMethod.GET)
	public @ResponseBody Object getGroupRoleTree(@PathVariable long groupid){
		return groupOrgUserRoleService.groupRoleTreeJson(groupid);
	}
	@RequestMapping(value=("/groupUserTree/{groupid}/{orgid}"),method=RequestMethod.GET)
	public @ResponseBody Object getGroupUserTree(@PathVariable long groupid,@PathVariable long orgid){
		return groupOrgUserRoleService.findSubCheckedTreeNodeJsonOfOrgAndUserByOrgid(orgid, groupid);
	}
	@RequestMapping(value=("/groupTreeOfUser/{userid}/{groupid}"),method=RequestMethod.GET)
	public @ResponseBody Object getGroupTreeOfUser(@PathVariable long userid,@PathVariable long groupid){
		return groupOrgUserRoleService.findSubGroupTreeJsonByUseridAndParentGroupid(userid, groupid);
	}
	@RequestMapping(value=("/resourceTreeOfUser/{userid}/{modelid}"),method=RequestMethod.GET)
	public @ResponseBody Object getResourceTreeOfUser(@PathVariable String userid,@PathVariable long modelid){
		return groupOrgUserRoleService.findSubTreeNodeJsonOfModelAndMenuAndOperationByUserid(userid, modelid);
	}
	@RequestMapping(value=("/userRoleTree/{userid}"),method=RequestMethod.GET)
	public @ResponseBody Object getUserRoleTree(@PathVariable long userid){
		return groupOrgUserRoleService.userRoleTreeJson(userid);
	}
}
