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
}
