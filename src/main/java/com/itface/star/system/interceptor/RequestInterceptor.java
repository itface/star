package com.itface.star.system.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//implements InitializingBean
/**
 * 一般情况下，对来自浏览器的请求的拦截，是利用Filter实现的，这种方式可以实现Bean预处理、后处理。 
Spring MVC的拦截器不仅可实现Filter的所有功能，还可以更精确的控制拦截精度。 
Spring为我们提供了org.springframework.web.servlet.handler.HandlerInterceptorAdapter这个适配器，继承此类，可以非常方便的实现自己的拦截器。
 */
public class RequestInterceptor extends HandlerInterceptorAdapter {

	//Logger accessLogger = Logger.getLogger("accessLog");

	/** 
     * 在Controller方法前进行拦截 
     * 如果返回false 
     *      从当前拦截器往回执行所有拦截器的afterCompletion方法,再退出拦截器链. 
     * 如果返回true 
     *      执行下一个拦截器,直到所有拦截器都执行完毕. 
     *      再运行被拦截的Controller. 
     *      然后进入拦截器链,从最后一个拦截器往回运行所有拦截器的postHandle方法. 
     *      接着依旧是从最后一个拦截器往回执行所有拦截器的afterCompletion方法. 
     */  
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		/*
		 *  
		MultiActionController maControl = (MultiActionController) handler;  
        ParameterMethodNameResolver pmrResolver = (ParameterMethodNameResolver) maControl.getMethodNameResolver();  
        String methodName = pmrResolver.getHandlerMethodName(request);  
        String className = handler.getClass().getName();  
		String uid = (String)request.getSession().getAttribute(LoginConstant.SESSION_LOGIN_FLAG_UID);
		request.getRequestURL();
		
		request.getRequestURI();
		if(uid==null||"".equals(uid.trim())){
			
		}
		String url = request.getRequestURI();
		accessLogger.info(url);
		*/
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {
		/*
		log.debug("==========postHandle=========");

		if (modelAndView != null) {
			String viewName = modelAndView.getViewName();
			log.debug("view name : " + viewName);
		} else {
			log.debug("view is null");
		}*/
	}

	/** 
     * 在Controller方法后进行拦截 
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有拦截器的afterCompletion方法 
     */  
	@Override
	public void afterCompletion(HttpServletRequest httpservletrequest,HttpServletResponse httpservletresponse, Object obj,Exception exception) throws Exception {
		//log.debug("=====afterCompletion====");
	}
}
