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
		<div >
		资源 ：<ul id="resources"></ul>
		</div>
		<form:hidden path="role.id"/>
	</div>
	<div style='right:0px;bottom:5px;height:20px;position:absolute'>
		<input type='button' value='保存' id='cancel' onclick="submit()"/>
		<input type='button' value='取消' id='ok'  onclick="closeWin()"/>
	</div>
</body>
<script>
createResourcesTree();
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
	var menuIds = new Array();//{1:true,2:false}
	var operationIds = new Array();//{1:[1,2,3],2:[4,5]}
	var menusAndOperations = $('#resources').tree('getAllCheckNodes');
	//把已经加载的叶子节点，包括带operations的节点和不带operations的节点。其中menu节点默认为不选中，operations时都默认为空。
	//通过extend把选中的节点更新到menuIds和operationIds对象中
	if(menusAndOperations){
		$(menusAndOperations).each(function(i,v){
			if(v.attributes.nodetype=='menu'){
				var id = v.attributes.id;
				menuIds.push(id);
			}else if(v.attributes.nodetype=='operation'){
				var id = v.attributes.id;
				operationIds.push(id);
			}
		});
	}
	//$.extend(menuIds,getCheckedMenu());
	//$.extend(operationIds,getCheckedOperation());
	//var jqueryTraditional = jQuery.ajaxSettings.traditional;
	jQuery.ajaxSettings.traditional = true;
	$.ajax({
		url:'${ctx}/system/org/role/grid/'+id,
		//async:false,
		//dataType:'json'
		data:{id:id,rolename:$('#rolename').val(),allMenuIds:menuIds,allOperationIds:operationIds,checkedMenuIds:getCheckedMenu(),checkedOperationIds:getCheckedOperation(),_method:_method},
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

function getCheckedOperation(){
	var checkedNodes = $('#resources').tree('getChecked');
	var indeterminateNodes = $('#resources').tree('getCheckedExt');
	var arr = new Array();
	arr = arr.concat(checkedNodes,indeterminateNodes);
	var operationIds=new Array();//{1:[1,2,3],2:[4,5]}
	$(arr).each(function(i,v){
		if(v.attributes.nodetype=='operation'){
			var id = v.attributes.id;
			operationIds.push(id);
		}
	});
	return operationIds;
}
function getCheckedMenu(){
	var checkedNodes = $('#resources').tree('getChecked');
	//自定义的取半选中的节点
	var indeterminateNodes = $('#resources').tree('getCheckedExt');
	var arr = new Array();
	arr = arr.concat(checkedNodes,indeterminateNodes);
	var menuIds=new Array();//{1:true,2:false}
	$(arr).each(function(i,v){
		if(v.attributes.nodetype=='menu'){
			var id = v.attributes.id;
			menuIds.push(id);
		}
	});
	return menuIds;
}
function createResourcesTree(){
	$('#resources').tree({
		checkbox: true,
		multiple:true,
		cascadeCheck:true,   
		onlyLeafCheck:false,
		method:'GET',
        url: '${ctx}/system/org/resource/subTreeNodesOfModelAndMenuAndOperation/0/0',   
        onBeforeExpand:function(node,param){  
        	var nodetype = node.attributes.nodetype;
        	if(nodetype=='model'){
        		$('#resources').tree('options').url = "${ctx}/system/org/resource/subTreeNodesOfModelAndMenuAndOperation/"+$('#id').val()+"/" + node.attributes.id;
        	}else{
        		$('#resources').tree('options').url ="";
        	}
        },  
		onCheck:function(node,checked){
	    	/*
	    	//取选中还半选中的节点
	    	var checkedNodes = $('#resources').tree('getChecked');
	    	//自定义的取半选中的节点
	    	var indeterminateNodes = $('#resources').tree('getCheckedExt');
	    	//var arr = $('#tempResourcesIds').combotree('getValues');
	    	var arr = new Array();
	    	arr = arr.concat(checkedNodes,indeterminateNodes);
	    	if(arr!=null&&arr.length>0){
	    		for(var i=0;i<arr.length;i++){
	    			var n = arr[i];
	    			if(n.attributes.nodetype=='operation'){
	    				s+=n.id+',';
	    			}
	    		}
	    		s=s.substring(0,s.lastIndexOf(','));
	    	}
	    	alert(s);
	    	*/
	    }               
	});
}
</script>
</html>