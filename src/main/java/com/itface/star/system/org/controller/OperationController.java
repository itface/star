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

import com.itface.star.system.org.model.Operation;
import com.itface.star.system.org.service.OperationService;

@Controller
@RequestMapping(value="/system/org/operation")
public class OperationController {
 

	@Autowired
	private OperationService operationService;
	/**
	 * 操作管理首页
	 * @return
	 */
	@RequestMapping
	public ModelAndView index(){
		return new ModelAndView("/system/org/operation");
	}
	/**
	 * 操作管理功能中管理操作的页面
	 * @param menuid
	 * @return
	 */
	@RequestMapping(value=("/{menuid}"),method=RequestMethod.GET)
	public ModelAndView index(@PathVariable long menuid){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("menuid", menuid);
		return new ModelAndView("/system/org/operation_listGrid",map);
	}
	/**
	 * 操作管理子表数据源
	 * @param menuid
	 * @return
	 */
	@RequestMapping(value=("/{menuid}/grid"),method=RequestMethod.GET)
	public @ResponseBody Object getGridData(@PathVariable long menuid){
		JSONObject json = operationService.findOperationJqgirdJsonByMenuid(menuid);
		return json==null?"":json;
	}
	/**
	 * 新增和修改操作信息进入的页面
	 * @param menuid
	 * @param operationid
	 * @return
	 */
	@RequestMapping(value=("/{menuid}/grid/{operationid}"),method=RequestMethod.GET)
	public ModelAndView getGridRowData(@PathVariable long menuid,@PathVariable long operationid){
		Operation operation = null;
		if(operationid>0){
			operation = operationService.find(operationid);
		}else{
			operation = new Operation();
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("operation", operation);
		map.put("menuid", menuid);
		map.put("actionFlagList", Operation.actionFlagList());
		return new ModelAndView("/system/org/operation_listGrid_row",map);
	}
	/**
	 * 新增操作
	 * @param menuid
	 * @param operation
	 * @param result
	 * @return
	 */
	@RequestMapping(value=("/{menuid}/grid/{operationid}"),method=RequestMethod.POST)
	public @ResponseBody String add(@PathVariable long menuid,@Valid Operation operation,BindingResult result){
		if (!result.hasErrors()) { 
			operationService.add(menuid,operation);
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
	 * 更新操作
	 * @param menuid
	 * @param operation
	 * @param result
	 * @return
	 */
	@RequestMapping(value=("/{menuid}/grid/{operationid}"),method=RequestMethod.PUT)
	public @ResponseBody String update(@PathVariable long menuid,@Valid Operation operation,BindingResult result){
		if (!result.hasErrors()) { 
			operationService.update(menuid,operation);
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
	 * 删除操作记录
	 * @param operationid
	 */
	@RequestMapping(value=("/{menuid}/grid/{operationid}"),method=RequestMethod.DELETE)
	public @ResponseBody void delete(@PathVariable long operationid){
		operationService.remove(operationid);
	}
	/**
	 * 批量删除操作记录
	 * @param operationidArr
	 */
	@RequestMapping(value=("/{menuid}/grid"),method=RequestMethod.DELETE)
	public @ResponseBody void deleteList(long[] operationidArr){
		operationService.removeList(operationidArr);
	}
}
