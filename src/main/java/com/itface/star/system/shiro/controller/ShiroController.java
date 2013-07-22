package com.itface.star.system.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.itface.star.system.org.model.User;
import com.itface.star.system.org.service.UserService;
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
	private UserService userService;
	@RequestMapping(value="/unauthorized")
	public ModelAndView index(){
		return new ModelAndView("/commons/unauthorized");
	}
	@RequestMapping(value="/refreshPermissions")
	public void refreshPermissions(){
		Subject currentUser = SecurityUtils.getSubject();
		String userid = (String)currentUser.getPrincipal();
		if(!userid.equals("admin")){
			User user = userService.findByUserid(userid);
     	   SecurityUtils.getSubject().getSession().setAttribute("menuTree",user.findMenuTree());

		}
	}
}
