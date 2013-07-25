package com.itface.star.system.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.itface.star.system.constant.Constants;
import com.itface.star.system.shiro.service.IAuthService;
import com.itface.star.system.shiro.service.ShiroService;
/**
 * 只有perms，roles，ssl，rest，port才是属于AuthorizationFilter，
 * 而anon，authcBasic，auchc，user是AuthenticationFilter，所以unauthorizedUrl设置后页面不跳转
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value="/shiro")
public class ShiroController {
	@Autowired
	private ShiroService shiroService;
	@Autowired
	private IAuthService authService;
	
	
	@RequestMapping(value="/unauthorized")
	public ModelAndView index(){
		return new ModelAndView("/commons/unauthorized");
	}
	@RequestMapping(value="/cache")
	public ModelAndView refreshPermissionsPage(){
		return new ModelAndView("/system/shiro/shiroCache");
	}
	@RequestMapping(value="/cachae/refreshCache")
	public @ResponseBody void refreshCache(){
		authService.reCreateFilterChains();
	}
	@RequestMapping(value="/refreshUserPermissions")
	public void refreshUserPermissions(){
		Subject currentUser = SecurityUtils.getSubject();
		String userid = (String)currentUser.getPrincipal();
		if(!userid.equals(Constants.SUPER_ADMINISTRATOR)){
     	   SecurityUtils.getSubject().getSession().setAttribute("menuTree",shiroService.findMenuTreeByUserid(userid));
		}
	}
}
