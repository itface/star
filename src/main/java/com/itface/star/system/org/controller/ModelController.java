package com.itface.star.system.org.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.itface.star.system.org.model.Model;
import com.itface.star.system.org.service.ModelService;

@Controller
@RequestMapping(value="/system/org/model")
public class ModelController {
	@Autowired
	private ModelService modelService;
	
	@RequestMapping(value=("/newPage/{parentid}"),method=RequestMethod.GET)
	public ModelAndView newPage(@PathVariable long parentid){
		Model model = new Model();
		model.setParentmodel(parentid);
		Map<String,Object> map = new HashMap<String,Object>();
		List<Integer> list = modelService.findSonOrderList(parentid);
		model.setDisplayorder(list.size()+1);
		list.add(list.size()+1);
		map.put("model", model);
		map.put("list", list);
		return new ModelAndView("/system/org/menu_model",map);
	}
	@RequestMapping(value=("/{modelid}"),method=RequestMethod.GET)
	public ModelAndView get(@PathVariable long modelid){
		Model model = modelService.find(modelid);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("model", model);
		List<Integer> list = modelService.findOrderList(modelid);
		map.put("list", list);
		return new ModelAndView("/system/org/menu_model",map);
	}
	@RequestMapping(value=("/{modelid}"),method=RequestMethod.POST)
	public @ResponseBody String add(@Valid Model model,BindingResult result){
		if (!result.hasErrors()) { 
			modelService.add(model);
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
	@RequestMapping(value=("/{modelid}"),method=RequestMethod.PUT)
	public @ResponseBody String update(@Valid Model model,BindingResult result){
		if (!result.hasErrors()) { 
			modelService.update(model);
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
	@RequestMapping(value=("/{modelid}"),method=RequestMethod.DELETE)
	public @ResponseBody void delete(@PathVariable long modelid){
		modelService.remove(modelid);
	}
	@RequestMapping(value=("/findSons/{modelid}"),method=RequestMethod.GET)
	public @ResponseBody JSONArray getSons(@PathVariable long modelid){
		return modelService.findSonJson(modelid);
	}
}
