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

import com.itface.star.system.develop.table.model.FieldModel;
import com.itface.star.system.develop.table.model.TableModel;
import com.itface.star.system.develop.table.service.FieldModelService;
import com.itface.star.system.develop.table.service.ModelSourceService;
import com.itface.star.system.develop.table.service.TableModelService;
@Controller
@RequestMapping(value="/system/develop/table")
public class TableController {

	@Autowired
	private TableModelService tableModelService;
	@Autowired
	private FieldModelService fieldModelService;
	@Autowired
	private ModelSourceService modelSourceService;

	@RequestMapping
	public ModelAndView index(){
		return new ModelAndView("/system/develop/table");
	}

	@RequestMapping(value=("/tableTree"),method=RequestMethod.GET)
	public @ResponseBody JSONArray getSons(){
		return tableModelService.findTableTree();
	}

	@RequestMapping(value=("/{tableid}"),method=RequestMethod.GET)
	public ModelAndView newPage(@PathVariable long tableid){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("tableid", tableid);
		if(tableid>0){
			TableModel t = tableModelService.findTableById(tableid);
			map.put("table", t);
			map.put("_method", "PUT");
		}else{
			map.put("table", new TableModel());
			map.put("_method", "POST");
		}
		return new ModelAndView("/system/develop/table_tableInfo",map);
	}
	@RequestMapping(value=("/{tableid}/getSource"),method=RequestMethod.GET)
	public ModelAndView sourcePage(@PathVariable long tableid){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("source", modelSourceService.genSource(tableid));
		return new ModelAndView("/system/develop/table_tableInfo_source",map);
	}
	@RequestMapping(value=("/{tableid}/{fieldid}"),method=RequestMethod.GET)
	public ModelAndView get(@PathVariable long tableid,@PathVariable long fieldid){
		Map<String,Object> map = new HashMap<String,Object>();
		if(fieldid>0){
			FieldModel f = fieldModelService.findFieldById(fieldid);
			map.put("field", f);
			map.put("_method", "PUT");
		}else{
			map.put("field", new FieldModel());
			map.put("_method", "POST");
		}
		return new ModelAndView("/system/develop/table_tableInfo_fieldInfo",map);
	}

	@RequestMapping(value=("/{tableid}"),method=RequestMethod.POST)
	public  @ResponseBody String addTable(@Valid TableModel tableModel,BindingResult result){
		if (!result.hasErrors()) { 
			TableModel t = tableModelService.addTable(tableModel);
			return "/system/develop/table/"+t.getId();
		}else{
			List<ObjectError> errors = result.getAllErrors();
			StringBuffer sb = new StringBuffer();
			for(ObjectError error : errors){
				sb.append(error.getDefaultMessage()).append("\r");
			}
			return sb.toString();
		}
	}
	@RequestMapping(value=("/{tableid}"),method=RequestMethod.PUT)
	public @ResponseBody String updateTable(@Valid TableModel tableModel,BindingResult result){
		if (!result.hasErrors()) { 
			TableModel t = tableModelService.updateTable(tableModel);
			return "/system/develop/table/"+t.getId();
		}else{
			List<ObjectError> errors = result.getAllErrors();
			StringBuffer sb = new StringBuffer();
			for(ObjectError error : errors){
				sb.append(error.getDefaultMessage()).append("\r");
			}
			return sb.toString();
		}
	}
	@RequestMapping(value=("/{tableid}/{fieldid}"),method=RequestMethod.POST)
	public @ResponseBody String addField(@PathVariable long tableid,@Valid FieldModel fieldModel,BindingResult result){
		if (!result.hasErrors()) { 
			fieldModelService.addField(fieldModel,tableid);
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
	@RequestMapping(value=("/{tableid}/{fieldid}"),method=RequestMethod.PUT)
	public @ResponseBody String updateField(@PathVariable long tableid,@Valid FieldModel fieldModel,BindingResult result){
		if (!result.hasErrors()) { 
			fieldModelService.updateField(fieldModel,tableid);
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

	@RequestMapping(value=("/{tableid}"),method=RequestMethod.DELETE)
	public @ResponseBody void delete(@PathVariable long tableid){
		tableModelService.deleteTable(tableid);
	}
	@RequestMapping(value=("/{tableid}/fieldGrid"),method=RequestMethod.GET)
	public @ResponseBody Object getGridData(@PathVariable long tableid){
		return fieldModelService.findGridDataOfField(tableid);
	}
	@RequestMapping(value=("/{tableid}/fieldGrid"),method=RequestMethod.DELETE)
	public @ResponseBody void deleteList(String ids){
		fieldModelService.deleteField(ids);
	}
}
