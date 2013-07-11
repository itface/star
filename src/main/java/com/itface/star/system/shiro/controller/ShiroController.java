package com.itface.star.system.shiro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
/**
 * 只有perms，roles，ssl，rest，port才是属于AuthorizationFilter，
 * 而anon，authcBasic，auchc，user是AuthenticationFilter，所以unauthorizedUrl设置后页面不跳转
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value="/unauthorized")
public class ShiroController {
	@RequestMapping
	public ModelAndView index(){
		return new ModelAndView("/commons/unauthorized");
	}
}
