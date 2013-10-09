package com.itface.star.system.develop.table.controller;

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

import com.ckeditor.CKEditorConfig;
import com.itface.star.system.develop.table.model.FormModel;
import com.itface.star.system.develop.table.service.FormModelService;
@Controller
@RequestMapping(value="/system/develop/formmodel")
public class FormModelController {

	@Autowired
	private FormModelService formModelService;

	@RequestMapping
	public ModelAndView index(){
		return new ModelAndView("/system/develop/formModel");
	}

	@RequestMapping(value=("/tree"),method=RequestMethod.GET)
	public @ResponseBody JSONArray getSons(){
		return formModelService.findFormTree();
	}

	@RequestMapping(value=("/{formid}"),method=RequestMethod.GET)
	public ModelAndView newPage(@PathVariable long formid){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("formid", formid);
		return new ModelAndView("/system/develop/formModel_main",map);
	}
	@RequestMapping(value=("/{formid}/formInfo"),method=RequestMethod.GET)
	public ModelAndView get(@PathVariable long formid){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("formid", formid);
		if(formid>0){
			FormModel f = formModelService.find(formid);
			map.put("form", f);
			map.put("_method", "PUT");
		}else{
			map.put("form", new FormModel());
			map.put("_method", "POST");
		}
		return new ModelAndView("/system/develop/formModel_main_formInfo",map);
	}
	@RequestMapping(value=("/{formid}"),method=RequestMethod.POST)
	public  @ResponseBody String addForm(@Valid FormModel formModel,BindingResult result){
		if (!result.hasErrors()) { 
			FormModel t = formModelService.add(formModel);
			return "/system/develop/formmodel/"+t.getId()+"/formInfo";
		}else{
			List<ObjectError> errors = result.getAllErrors();
			StringBuffer sb = new StringBuffer();
			for(ObjectError error : errors){
				sb.append(error.getDefaultMessage()).append("\r");
			}
			return sb.toString();
		}
	}
	@RequestMapping(value=("/{formid}"),method=RequestMethod.PUT)
	public @ResponseBody String updateForm(@Valid FormModel formModel,BindingResult result){
		if (!result.hasErrors()) { 
			FormModel t = formModelService.update(formModel);
			return "/system/develop/formmodel/"+t.getId()+"/formInfo";
		}else{
			List<ObjectError> errors = result.getAllErrors();
			StringBuffer sb = new StringBuffer();
			for(ObjectError error : errors){
				sb.append(error.getDefaultMessage()).append("\r");
			}
			return sb.toString();
		}
	}
	@RequestMapping(value=("/{formid}"),method=RequestMethod.DELETE)
	public @ResponseBody void delete(@PathVariable long formid){
		formModelService.delete(formid);
	}
	@RequestMapping(value=("/{formid}/formSetting"),method=RequestMethod.GET)
	public ModelAndView getFormSettingPage(@PathVariable long formid){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("formid", formid);
		if(formid>0){
			map.put("gridHtml", formModelService.getFormSettingGridHtml(formid));
			map.put("gridScript",formModelService.getFormSettingGridScript(formid));
		}else{
			map.put("gridHtml", "");
			map.put("gridScript", "");
		}
		return new ModelAndView("/system/develop/formModel_main_formSetting",map);
	}
	@RequestMapping(value=("/{formid}/designer"),method=RequestMethod.GET)
	public ModelAndView getDesignerPage(@PathVariable long formid){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("formid", formid);
		String value = formModelService.getFormHtml(formid);
		Map<String, String> attr = new HashMap<String, String>();
		attr.put("rows", "8");
		attr.put("cols", "100");
		CKEditorConfig settings = new CKEditorConfig();
		settings.addConfigValue("width", "1000");
		settings.addConfigValue("toolbar", "Full");
		map.put("attr", attr);
		map.put("settings", settings);
		map.put("value", value);
		return new ModelAndView("/system/develop/formModel_main_designer",map);
	}
}
