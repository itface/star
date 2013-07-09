package com.itface.star.system.org.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/system/org/menu")
public class MenuController {
	
	@RequestMapping
	public ModelAndView index(){
		return new ModelAndView("/system/org/menu");
	}
	@RequestMapping(value=("/page/{modelid}"),method=RequestMethod.GET)
	public ModelAndView index(@PathVariable long modelid){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("modelid", modelid);
		return new ModelAndView("/system/org/menu_model_menu",map);
	}
}
