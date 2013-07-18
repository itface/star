<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../inc/header.jsp"%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/script/easyui/css/default/easyui.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/script/easyui/css/icon.css'/>">
<script src="<c:url value='/resources/script/jquery-1.7.2.min.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/script/lhgdialog/lhgdialog.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/script/My97DatePicker4.8/WdatePicker.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/script/easyui/easyui-lang-zh_CN.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/script/easyui/jquery.easyui.min.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/script/easyui/easyui_extend.js'/>" type="text/javascript"></script>
<style>

</style>
</head>
<body>
	<div style="height:350px;overflow:auto">
		<div >
		名称：<form:input path="role.rolename"/>
		</div>
		<div>资源 ：</div>
		<div >
			<div style='float:left;width:250px;height:280px;overflow:auto;border:1px solid #ccc'><ul id="allResources"></ul></div>
			<div style='position:absolute;right:50px;width:250px;height:280px;overflow:auto;border:1px solid #ccc'><ul id="roleResources"></ul></div>
		</div>
		<form:hidden path="role.id"/>
	</div>
	<div style='right:0px;bottom:5px;height:20px;position:absolute'>
		<input type='button' value='保存' id='cancel' onclick="submit()"/>
		<input type='button' value='取消' id='ok'  onclick="closeWin()"/>
	</div>
</body>
<script>
createAllResourcesTree();
createRoleResourcesTree();
function closeWin(){
	var api = frameElement.api;
	var  W = api.opener; 
	var dia = W.$.dialog.data('dialog');
	dia.close();
}
function reloadGrid(){
	var api = frameElement.api;
	var  W = api.opener; 
	var reloadRoleGrid = W.$.dialog.data('reloadRoleGrid');
	reloadRoleGrid();
}

function submit(){
	var id = $('#id').val();
	var _method='POST';
	if(id>0){
		_method='PUT';
	}else{
		id=0;
	}
	var allMenuIds = new Array();
	var allOperationIds = new Array();
	var allModelIds = new Array();
	var allModelIdsObj = {};
	var checkedMenuIds = new Array();
	var checkedOperationIds = new Array();
	var checkedModelIds = new Array();
	var checkedModelIdsObj = {};
	getChecked(checkedOperationIds,checkedMenuIds,checkedModelIdsObj);
	var menusAndOperations = $('#allResources').tree('getAllCheckNodes');
	//把已经加载的叶子节点，包括带operations的节点和不带operations的节点。其中menu节点默认为不选中，operations时都默认为空。
	//通过extend把选中的节点更新到menuIds和operationIds对象中
	if(menusAndOperations){
		$(menusAndOperations).each(function(i,v){
			if(v.attributes.nodetype=='menu'){
				var id = v.attributes.id;
				allMenuIds.push(id);
				var model = $('#allResources').tree('getParent',v.target);
				getModelIds(allModelIdsObj,model);
			}else if(v.attributes.nodetype=='operation'){
				var id = v.attributes.id;
				allOperationIds.push(id);
				var parent = $('#allResources').tree('getParent',v.target);
				var model = $('#allResources').tree('getParent',parent.target);
				getModelIds(allModelIdsObj,model);
			}
		});
	}
	for(var i in allModelIdsObj) {
		allModelIds.push(allModelIdsObj[i]);
	}
	for(var i in checkedModelIdsObj) {
		checkedModelIds.push(checkedModelIdsObj[i]);
	}
	var jqueryTraditional = jQuery.ajaxSettings.traditional;
	jQuery.ajaxSettings.traditional = true;
	$.ajax({
		url:'${ctx}/system/org/role/grid/'+id,
		//async:false,
		//dataType:'json'
		data:{id:id,rolename:$('#rolename').val(),allmodelIds:allModelIds,allMenuIds:allMenuIds,allOperationIds:allOperationIds,modelIds:checkedModelIds,checkedMenuIds:checkedMenuIds,checkedOperationIds:checkedOperationIds,_method:_method},
		type:'POST',
		success:function(data, textStatus, jqXHR){
			if(data=='S'){
				reloadGrid();
				alert('保存成功');
				closeWin();
			}else{
				alert(data);
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			//XMLHttpRequest 对象、错误信息、（可选）捕获的异常对
			alert('保存失败');
		}
	});
	jQuery.ajaxSettings.traditional=jqueryTraditional;
}

function getChecked(operationIds,menuIds,modelIdsObj){
	var checkedNodes = $('#allResources').tree('getChecked');
	//var indeterminateNodes = $('#allResources').tree('getCheckedExt');
	//var arr = new Array();
	//arr = arr.concat(checkedNodes,indeterminateNodes);
	//var operationIds=new Array();//{1:[1,2,3],2:[4,5]}
	$(checkedNodes).each(function(i,v){
		if(v.attributes.nodetype=='operation'){
			var id = v.attributes.id;
			operationIds.push(id);
			var parent = $('#allResources').tree('getParent',v.target);
			menuIds.push(parent.attributes.id);
			var model = $('#allResources').tree('getParent',parent.target);
			getModelIds(modelIdsObj,model);
		}else if(v.attributes.nodetype=='menu'){
			var id = v.attributes.id;
			menuIds.push(id);
			var model = $('#allResources').tree('getParent',v.target);
			getModelIds(modelIdsObj,model);
		}
	});
}
function getModelIds(modelIdsObj,model){
	if(model){
		modelIdsObj[model.attributes.id]=model.attributes.id;
		var parent = $('#allResources').tree('getParent',model.target);
		if(parent){
			getModelIds(modelIdsObj,parent);
		}
	}
}
function createAllResourcesTree(){
	$('#allResources').tree({
		checkbox: true,
		multiple:true,
		cascadeCheck:false,   
		onlyLeafCheck:true,
		method:'GET',
        url: '${ctx}/system/org/resource/subTreeNodesOfModelAndMenuAndOperation/'+$('#id').val()+'/0',   
        onBeforeExpand:function(node,param){  
        	var nodetype = node.attributes.nodetype;
        	if(nodetype=='model'){
        		$('#allResources').tree('options').url = "${ctx}/system/org/resource/subTreeNodesOfModelAndMenuAndOperation/"+$('#id').val()+"/" + node.attributes.id;
        	}else{
        		$('#allResources').tree('options').url ="";
        	}
        }             
	});
}
function createRoleResourcesTree(){
	$('#roleResources').tree({
		checkbox: false,
		multiple:true,
		cascadeCheck:false,   
		onlyLeafCheck:false,
		method:'GET',
        url: '${ctx}/system/org/resource/subTreeNodesOfModelAndMenuAndOperationByRole/'+$('#id').val()+'/0',   
        onBeforeExpand:function(node,param){  
        	var nodetype = node.attributes.nodetype;
        	if(nodetype=='model'){
        		$('#roleResources').tree('options').url = "${ctx}/system/org/resource/subTreeNodesOfModelAndMenuAndOperationByRole/"+$('#id').val()+"/" + node.attributes.id;
        	}else{
        		$('#roleResources').tree('options').url ="";
        	}
        }             
	});
}
</script>
</html>