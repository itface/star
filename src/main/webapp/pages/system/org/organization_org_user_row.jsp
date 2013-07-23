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
<style>

</style>
</head>
<body>
	<div >
	帐号：<form:input path="user.userid"/>
	</div>
	<div >
	名称：<form:input path="user.username"/>
	</div>
	<div >
	显示顺序：<form:select  path="user.displayorder" items="${orderList}"/>
	</div>
	<div >
	状态 ：
		<form:select path="user.status">  
            <form:option value="0">激活</form:option> 
            <form:option value="-1">冻结</form:option> 
        </form:select>  
	</div>
	<div>角色 ：</div>
	<div >
		<div style='float:left;width:250px;height:280px;overflow:auto;border:1px solid #ccc'><ul id="roleTree"></ul></div>
		<div style='position:absolute;right:50px;width:250px;height:280px;overflow:auto;border:1px solid #ccc'><ul id="roleResourceTree"></ul></div>
	</div>
	<form:hidden path="user.id"/>
	<div style='right:0px;bottom:5px;height:20px;position:absolute'>
		<input type='button' value='保存' id='cancel' onclick="submit()"/>
		<input type='button' value='取消' id='ok'  onclick="closeWin()"/>
	</div>
</body>
<script>
createRoleTree();
var openRoleTreeFlag = false;
init();
function init(){
	if($('#id').val()>0){
		$('#userid').attr('readOnly',true);
	}
}
function closeWin(){
	var api = frameElement.api;
	var  W = api.opener; 
	var dia = W.$.dialog.data('dialog');
	dia.close();
}
function reloadGrid(){
	var api = frameElement.api;
	var  W = api.opener; 
	var reloadUserGrid = W.$.dialog.data('reloadUserGrid');
	reloadUserGrid();
}
function submit(){
	var id = $('#id').val();
	var _method='POST';
	if(id>0){
		_method='PUT';
	}else{
		id=0;
	}
	$.ajax({
		url:'${ctx}/system/org/user/${orgid}/grid/'+id,
		//async:false,
		//dataType:'json'
		data:{id:id,displayorder:$('#displayorder').val(),userid:$('#userid').val(),username:$('#username').val(),status:$('#status').val(),openRoleTreeFlag:openRoleTreeFlag,checkedRoleIds:checkedRoleIds(),_method:_method},
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
}
function createRoleResourceTree(roleid){
	$('#roleResourceTree').tree({
		checkbox: false,
		multiple:true,
		cascadeCheck:false,   
		onlyLeafCheck:false,
		method:'GET',
        url: '${ctx}/system/org/resource/subTreeNodesOfModelAndMenuAndOperationByRole/'+roleid+'/0',   
        onBeforeExpand:function(node,param){  
        	var nodetype = node.attributes.nodetype;
        	if(nodetype=='model'){
        		var url = "${ctx}/system/org/resource/subTreeNodesOfModelAndMenuAndOperationByRole/"+roleid+"/" + node.attributes.id;
        		$('#roleResourceTree').tree('options').url = url;
        	}else{
        		$('#roleResourceTree').tree('options').url ="";
        	}
        }             
	});
}
function removeRoleResourceTree(){
	$('#roleResourceTree').empty();
}
function createRoleTree(){
	$('#roleTree').tree({
		checkbox: true,
		multiple:true,
		cascadeCheck:false,   
		onlyLeafCheck:false,
		method:'GET',
        url: '${ctx}/system/org/role/userRoleTree/-1',   
        onBeforeExpand:function(node,param){  
        	$('#roleTree').tree('options').url = "${ctx}/system/org/role/userRoleTree/"+$('#id').val();
        	openRoleTreeFlag=true;
        },
        onClick:function(node){
        	if(node.attributes.nodetype=='role'){
        		removeRoleResourceTree();
        		createRoleResourceTree(node.attributes.id);
        	}
        }             
	});
}
function checkedRoleIds(){
	var checkedIds = '';
	var checkedNodes = $('#roleTree').tree('getChecked');
	$(checkedNodes).each(function(i,v){
		if(v.attributes.nodetype=='role'){
			checkedIds+=v.attributes.id+',';
		}
	});
	if(checkedIds.length>0){
		checkedIds=checkedIds.substring(0,checkedIds.lastIndexOf(','));
	}
	return checkedIds;
}
</script>
</html>