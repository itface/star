package com.itface.star.system.org.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.itface.star.system.org.model.Menu;
import com.itface.star.system.org.service.MenuService;
import com.itface.star.system.org.service.ModelService;

@Controller
@RequestMapping(value="/system/org/menu")
public class MenuController {
 
	
	@Autowired
	private MenuService menuService;

	
	/**
	 * 菜单管理功能首页
	 * @return
	 */
	@RequestMapping
	public ModelAndView index(){
		return new ModelAndView("/system/org/menu");
	}
	/**
	 * 菜单管理功能中管理菜单的页面
	 * @param modelid
	 * @return
	 */
	@RequestMapping(value=("/{modelid}"),method=RequestMethod.GET)
	public ModelAndView index(@PathVariable long modelid){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("modelid", modelid);
		return new ModelAndView("/system/org/menu_model_menu",map);
	}
	/**
	 * 管理菜单页面中的菜单子表数据源
	 * @param modelid
	 * @return
	 */
	@RequestMapping(value=("/{modelid}/grid"),method=RequestMethod.GET)
	public @ResponseBody Object getGridData(@PathVariable long modelid){
		JSONObject json = menuService.findMenuJsonWithoutAuthByModelid(modelid);
		return json==null?"":json;
	}
	/**
	 * 新增或更新菜单时进入的页面
	 * @param modelid
	 * @param menuid
	 * @return
	 */
	@RequestMapping(value=("/{modelid}/grid/{menuid}"),method=RequestMethod.GET)
	public ModelAndView getGridRowData(@PathVariable long modelid,@PathVariable long menuid){
		Menu menu = null;
		List<Integer> orderList =  menuService.findMenuOrderListByModelid(modelid);
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
	/**
	 * 新增菜单
	 * @param modelid
	 * @param menu
	 * @param result
	 * @return
	 */
	@RequestMapping(value=("/{modelid}/grid/{menuid}"),method=RequestMethod.POST)
	public @ResponseBody String add(@PathVariable long modelid,@Valid Menu menu,BindingResult result){
		if (!result.hasErrors()) { 
			menuService.add(modelid,menu);
			return "S";
		}else{
			List<ObjectError> errors = result.getAllErrors();
			StringBuffer sb = new StringBuffer();
			for(ObjectError error : errors){
				sb.append(error.getDefaultMessage()).append("\r");
			}
			return sb.toString();
		}
		
	}
	/**
	 * 更新菜单
	 * @param modelid
	 * @param menu
	 * @param result
	 * @return
	 */
	@RequestMapping(value=("/{modelid}/grid/{menuid}"),method=RequestMethod.PUT)
	public @ResponseBody String update(@PathVariable long modelid,@Valid Menu menu,BindingResult result){
		if (!result.hasErrors()) { 
			menuService.update(modelid,menu);
			return "S";
		}else{
			List<ObjectError> errors = result.getAllErrors();
			StringBuffer sb = new StringBuffer();
			for(ObjectError error : errors){
				sb.append(error.getDefaultMessage()).append("\r");
			}
			return sb.toString();
		}
	}
	/**
	 * 删除一条菜单记录
	 * @param menuid
	 */
	@RequestMapping(value=("/{modelid}/grid/{menuid}"),method=RequestMethod.DELETE)
	public @ResponseBody void delete(@PathVariable long menuid){
		menuService.remove(menuid);
	}
	/**
	 * 批量删除多条菜单记录
	 * @param menuIdArr
	 */
	@RequestMapping(value=("/{modelid}/grid"),method=RequestMethod.DELETE)
	public @ResponseBody void deleteList(long[] menuIdArr){
		menuService.removeList(menuIdArr);
	}
}
