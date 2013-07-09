package com.itface.star.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
@Component("myApplicationContextUtil")
public class MyApplicationContextUtil implements ApplicationContextAware{

	private static ApplicationContext context;
	
	@Override
	public void setApplicationContext(ApplicationContext context)throws BeansException {
		// TODO Auto-generated method stub
		this.context = context;
		
	}
	public static ApplicationContext getApplicationContext(){
		return context;
	}

}
