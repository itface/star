package com.itface.star.system.org.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.itface.star.system.org.model.Menu;
import com.itface.star.system.org.model.Model;
import com.itface.star.system.org.service.MenuService;

@Controller
@RequestMapping(value="/system/org/menu")
public class MenuController {
	@Autowired
	private MenuService menuService;
	@RequestMapping
	public ModelAndView index(){
		return new ModelAndView("/system/org/menu");
	}
	@RequestMapping(value=("/{modelid}"),method=RequestMethod.GET)
	public ModelAndView index(@PathVariable long modelid){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("modelid", modelid);
		return new ModelAndView("/system/org/menu_model_menu",map);
	}
	@RequestMapping(value=("/{modelid}/grid"),method=RequestMethod.GET)
	public @ResponseBody Object getGridData(@PathVariable long modelid){
		JSONObject json = menuService.findMenuJsonByModelid(modelid);
		return json==null?"":json;
	}
	@RequestMapping(value=("/{modelid}/grid/{menuid}"),method=RequestMethod.GET)
	public ModelAndView getGridRowData(@PathVariable long modelid,@PathVariable long menuid){
		Menu menu = null;
		List<Integer> orderList =  menuService.findOrderListByModelid(modelid);
		if(menuid>0){
			menu = menuService.find(menuid);
		}else{
			menu = new Menu();
			menu.setDisplayorder(orderList.size()+1);
			orderList.add(orderList.size()+1);
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("menu", menu);
		map.put("modelid", modelid);
		map.put("orderList", orderList);
		return new ModelAndView("/system/org/menu_model_menu_row",map);
	}
	@RequestMapping(value=("/{modelid}/grid/{menuid}"),method=RequestMethod.POST)
	public @ResponseBody void add(@PathVariable long modelid,Menu menu){
		menuService.add(modelid,menu);
	}
	@RequestMapping(value=("/{modelid}/grid/{menuid}"),method=RequestMethod.PUT)
	public @ResponseBody void update(@PathVariable long modelid,Menu menu){
		menuService.update(modelid,menu);
	}
	@RequestMapping(value=("/{modelid}/grid/{menuid}"),method=RequestMethod.DELETE)
	public @ResponseBody void delete(@PathVariable long menuid){
		menuService.remove(menuid);
	}
	@RequestMapping(value=("/{modelid}/grid"),method=RequestMethod.DELETE)
	public @ResponseBody void deleteList(long[] menuIdArr){
		menuService.removeList(menuIdArr);
	}
}
