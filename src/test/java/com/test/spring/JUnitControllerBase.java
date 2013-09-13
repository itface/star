package com.test.spring;

import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@ContextConfiguration(locations = {"classpath*:applicationContext.xml","classpath*:springmvc.xml"})  
public class JUnitControllerBase extends AbstractJUnit4SpringContextTests{

	/** 
     * default http port. 
     */  
    private static final int DEFAULT_PORT = 80;  
  
    /** 
     * DefaultAnnotationHandlerMapping. 
     * 1、HandlerMapping实现：使用DefaultAnnotationHandlerMapping（spring3.1之前）或RequestMappingHandlerMapping（spring3.1）
替换之前的BeanNameUrlHandlerMapping。
注解式处理器映射会扫描spring容器中的bean，发现bean实现类上拥有
@Controller或@RequestMapping注解的bean，并将它们作为处理器。
2、HandlerAdapter实现：使用AnnotationMethodHandlerAdapter（spring3.1之前）或RequestMappingHandlerAdapter（spring3.1）替换之前的SimpleControllerHandlerAdapter。
注解式处理器适配器会通过反射调用相应的功能处理方法（方法上拥有@RequestMapping注解）。
     */  
    @Resource(type = RequestMappingHandlerMapping.class)  
    protected HandlerMapping handlerMapping;  
    /** 
     * AnnotationMethodHandlerAdapter. 
     */  
    @Resource(type = RequestMappingHandlerAdapter.class)  
    protected HandlerAdapter handlerAdapter;  
  
    /** 
     * Simulate Request to URL appoint by MockHttpServletRequest. 
     *  
     * @param request 
     *        HttpServletRequest 
     * @param response 
     *        HttpServletResponse 
     * @return ModelAndView 
     * @throws Exception 
     *         runtimeException 
     */  
    public final ModelAndView excuteAction(final HttpServletRequest request, final HttpServletResponse response)throws Exception {  
        HandlerExecutionChain chain = this.handlerMapping.getHandler(request);  
        final ModelAndView model = this.handlerAdapter.handle(request, response, chain.getHandler());  
        return model;  
    }  
  
    /** 
     * Simulate Request to URL appoint by MockHttpServletRequest, default POST, port 80. 
     *  
     * @param url 
     *        requestURL 
     * @param objects 
     *        parameters 
     * @return ModelAndView 
     */  
    public final ModelAndView excuteAction(final String url, final Map<String,String> params) {  
        return this.excuteAction("POST", url, JUnitControllerBase.DEFAULT_PORT, params);  
    }  
  
    /** 
     * Simulate Request to URL appoint by MockHttpServletRequest, default POST. 
     *  
     * @param url 
     *        requestURL 
     * @param port 
     *        int 
     * @param objects 
     *        parameters 
     * @return ModelAndView 
     */  
    public final ModelAndView excuteAction(final String url, final int port, final Map<String,String> params) {  
        return this.excuteAction("POST", url, port, params);  
    }  
  
    /** 
     * Simulate Request to URL appoint by MockHttpServletRequest. 
     *  
     * @param method 
     *        POST/GET 
     * @param url 
     *        requestURL 
     * @param port 
     *        int 
     * @param objects 
     *        parameters 
     * @return ModelAndView 
     */  
    public final ModelAndView excuteAction(final String method, final String url, final int port,final Map<String,String> params) {  
        MockHttpServletRequest request = new MockHttpServletRequest(method, url);  
        MockHttpServletResponse response = new MockHttpServletResponse();  
        request.setServerPort(port);  
        request.setLocalPort(port);  
        if (params != null) {  
        	Iterator<String> it = params.keySet().iterator();
        	while(it.hasNext()){
        		String key = it.next();
        		String value = params.get(key);
        		request.addParameter(key, value);  
        	} 
        }  
        MockHttpSession session = new MockHttpSession();  
        request.setSession(session);  
        try {  
            return this.excuteAction(request, response);  
        } catch (Exception e) {  
            e.printStackTrace();   
        }  
        return null;  
    }  
}
