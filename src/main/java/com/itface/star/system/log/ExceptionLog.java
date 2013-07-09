package com.itface.star.system.log;


import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * 异常处理类，该类只能处理spring mvc整个过程中产生的异常。比如执行的定时任务，异常无法捕获.throws给平台才能触发此日志
 * @author Administrator
 *
 */
public class ExceptionLog implements HandlerExceptionResolver{

	private final static transient Logger log = LoggerFactory.getLogger("errorLog");
	@Override
	public ModelAndView resolveException(HttpServletRequest request,HttpServletResponse response, Object handler, Exception exception) {
		// TODO Auto-generated method stub
		 //User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 String now = sd.format(Calendar.getInstance().getTime());
		 log.error(now, exception);
		 ModelAndView model = new ModelAndView("/commons/error");
		 response.setStatus(500);
		return model;
	}

}
