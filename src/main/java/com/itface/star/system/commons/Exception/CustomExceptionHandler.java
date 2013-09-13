package com.itface.star.system.commons.Exception;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;

import com.itface.star.system.log.ExceptionLog;
/**
 * 每个Controller内加上一个异常处理方法，并用\@ExceptionHandler标注。但此法要每个Controller写，太分散太累了。
 * 自定义的HandlerExceptionResolver ，替代原来的，将所有的活都包过去。但这个又太集中了，整个War里只有一个Resolver，而且API是基于ModelAndView的，不适合做Restful的输出。
 * 
 * Spring MVC 3.2，终于补上了这块短板，合成了两种写法的优点，可以用@ControllerAdvice定义多个公共的ExceptionHandler类，每个Handler类可以用@ExceptionHandler(MyException1.class, MyException2.class)标注handler方法，只处理自己关心的异常，而且API变成了Restful友好的ResponseEntity。
 * @author Administrator
 *
 */
@ControllerAdvice 
public class CustomExceptionHandler {

	private final static  Logger log = LoggerFactory.getLogger(CustomExceptionHandler.class);
	/*
	 	@ModelAttribute  
	    public User newUser() {  
	        System.out.println("============应用到所有@RequestMapping注解方法，在其执行之前把返回值放入Model");  
	        return new User();  
	    }  
	  
	    @InitBinder  
	    public void initBinder(WebDataBinder binder) {  
	        System.out.println("============应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器");  
	    }  
	  */
	    @ExceptionHandler(Exception.class)  
	    //@ResponseStatus(HttpStatus.UNAUTHORIZED)  
	    public String processUnauthenticatedException(NativeWebRequest request, Exception e) {  
	        System.out.println("===========应用到所有@RequestMapping注解的方法，在其抛出UnauthenticatedException异常时执行");  
	        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 String now = sd.format(Calendar.getInstance().getTime());
	        log.error(now,e);
	        return "/commons/error"; //返回一个逻辑视图名  
	    }  
}
