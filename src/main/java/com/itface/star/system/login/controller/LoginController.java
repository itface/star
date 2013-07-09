package com.itface.star.system.login.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class LoginController {


	@RequestMapping(value="/login")
	public String login(@RequestParam(value = "username", defaultValue = "") String username,@RequestParam(value = "password", defaultValue = "") String password,HttpServletRequest req) {
		Subject currentUser = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		//token.setRememberMe(true);
		try {
			currentUser.login(token);
			/*
			//生成用户菜单(当然，也可以在main中完成，但在重定向的情况下会有hibernatesession延迟问题,即user.getmenus和getmodels在重定向后不能取出，因为session已经关闭)
			 * redirect时，因为是两个请求，所以参数放到request是不能传递到redirect后的页面的，只能放在session里
	           User s = (User)currentUser.getSession().getAttribute("c_user");
	           //currentUser.getSession().setAttribute("menus",s.getMenus());
	           currentUser.getSession().setAttribute("models", s.getModels());
	           */
			currentUser.getSession().removeAttribute("error_info");
			return "redirect:/main";
		} catch ( UnknownAccountException uae ) {  
			currentUser.getSession().setAttribute("error_info", "不存在该用户名，请核对！");
			token.clear();
			return "redirect:/";
		} catch ( IncorrectCredentialsException ice ) { 
			currentUser.getSession().setAttribute("error_info", "密码错误，请核对！");
			token.clear();
			return "redirect:/";
		} catch ( LockedAccountException lae ) {  
			currentUser.getSession().setAttribute("error_info", "该帐号已锁，请核对！");
			token.clear();
			return "redirect:/";
		} catch ( ExcessiveAttemptsException eae ) { 
			currentUser.getSession().setAttribute("error_info", "密码输错的次数太多，请核对！");
			token.clear();
			return "redirect:/";
		} catch (AuthenticationException e) {
			currentUser.getSession().setAttribute("error_info", "用户名或密码错，请核对！");
			token.clear();
			return "redirect:/";
		}
	}
	@RequestMapping(value="/")
	public ModelAndView index() {
		return new ModelAndView("login");
	}
	@RequestMapping(value="/logout")
	public String logout() {
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.logout();
		//return new ModelAndView(new RedirectView("/"));
		return "redirect:/";
	}
	 @RequestMapping(value="/main")
	 public String main() {
	       return "system/main";
	}
	 @RequestMapping(value="/main/left")
	 public String main_left() {
	       return "system/main_left";
	}	 
	 /**
	  * 验证出没有权限
	  * @return
	  */
	 @RequestMapping(value="/unauthorized")
	 public String unauthorized(){
		 return "commons/error";
	 }
}
