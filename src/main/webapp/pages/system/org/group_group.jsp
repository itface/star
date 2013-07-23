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
	<div style="height:650px;overflow:auto">
		<div >
		名称：<form:input path="group.name"/>
		</div>
		<div>角色 ：</div>
		<div >
			<div style='float:left;width:250px;height:280px;overflow:auto;border:1px solid #ccc'><ul id="roleTree"></ul></div>
			<div style='width:250px;height:280px;overflow:auto;border:1px solid #ccc'><ul id="roleResourceTree"></ul></div>
		</div>
		<div style="display:block">用户 ：</div>
		<div >
			<div style='float:left;width:250px;height:280px;overflow:auto;border:1px solid #ccc'><ul id="userTree"></ul></div>
			<div style='float:left;width:50px;height:280px;overflow:auto;position:relative'>
				<div style='left:20px;top:100px;position:absolute'><span style='cursor:pointer' onclick='addUser()'>》</span></div>
			</div>
			<div style='right:50px;width:250px;height:280px;overflow:auto;border:1px solid #ccc' id='checkedUsers'>
				<c:forEach items="${group.users}" var="user">
					<div class="checkedUserClass" user="${user.id}">${user.username}(${user.userid})<span>&nbsp;&nbsp;</span><span style="cursor:pointer;color:red;" title="删除" onclick="deleteUser(this)">ｘ</span></div>
				</c:forEach>
			</div>
		</div>
		<form:hidden path="group.id"/>
		<form:hidden path="group.parentid"/>
		<input type='hidden' id='checkedUserIds' valu="${checkedUserIds}"/>
	</div>
	<div style='right:0px;bottom:5px;height:20px;position:absolute'>
		<input type='button' value='保存' id='cancel' onclick="submit()"/>
		<input type='button' value='取消' id='ok'  onclick="closeWin()"/>
	</div>
</body>
<script>
createRoleTree();
createUserTree();
var openRoleTreeFlag = false;

function closeWin(){
	var api = frameElement.api;
	var  W = api.opener; 
	var dia = W.$.dialog.data('dialog');
	dia.close();
}
function addReloadModelTree(){
	var api = frameElement.api;
	var  W = api.opener; 
	var addReloadTree = W.$.dialog.data('addReloadTree');
	addReloadTree();
}
function editReloadModelTree(){
	var api = frameElement.api;
	var  W = api.opener; 
	var editAndDelReloadTree = W.$.dialog.data('editAndDelReloadTree');
	editAndDelReloadTree();
}
function reload(type){
	var api = frameElement.api;
	var  W = api.opener; 
	var editAndDelReloadTree = W.$.dialog.data('editAndDelReloadTree');
	var addReloadTree = W.$.dialog.data('addReloadTree');
	if('PUT'==type){
		editAndDelReloadTree();
	}else{
		addReloadTree();
	}
}
function submit(){
	var id = $('#id').val();
	var _method='POST';
	if(id>0){
		_method='PUT';
	}else{
		id=0;
	}
	var checkedUserids = getCheckedUseridString();
	var checkedRoleids = getCheckedRole();
	$.ajax({
		url:'${ctx}/system/org/group/'+id,
		//async:false,
		//dataType:'json'
		data:{id:id,name:$('#name').val(),parentid:$('#parentid').val(),openRoleTreeFlag:openRoleTreeFlag,checkedUserids:checkedUserids,checkedRoleids:checkedRoleids,_method:_method},
		type:'POST',
		success:function(data, textStatus, jqXHR){
			if(data=='S'){
				alert('保存成功');
				reload(_method);
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
        url: '${ctx}/system/org/gour/groupRoleTree/-1',   
        onBeforeExpand:function(node,param){  
        	$('#roleTree').tree('options').url = "${ctx}/system/org/gour/groupRoleTree/"+$('#id').val();
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
function getCheckedRole(){
	var nodes = $('#roleTree').tree('getChecked');
	var ids = new Array();
	$(nodes).each(function(i,v){
		ids.push(v.attributes.id);
	});
	return ids.join(',');
}
function createUserTree(){
	$('#userTree').tree({
		checkbox: true,
		multiple:true,
		cascadeCheck:false,   
		onlyLeafCheck:true,
		method:'GET',
        url: '${ctx}/system/org/gour/groupUserTree/-1/0',   
        onBeforeExpand:function(node,param){  
        	$('#userTree').tree('options').url = "${ctx}/system/org/gour/groupUserTree/"+$('#id').val()+"/"+node.attributes.id;
        }          
	});
}
function deleteUser(checkedUserSpan){
	$(checkedUserSpan).parent().remove();
}
function addUser(){
	var nodes = $('#userTree').tree('getChecked');
	var checkedIds = getCheckedUseridObj();
	$(nodes).each(function(i,v){
		var id = v.attributes.id;
		if(!checkedIds[id]){
			insertCheckedUserList(id,v.text);
		}
	});
}
function insertCheckedUserList(id,text){
		$('#checkedUsers').append('<div class="checkedUserClass" user="'+id+'">'+text+'<span>&nbsp;&nbsp;</span><span style="cursor:pointer;color:red;" title="删除" onclick="deleteUser(this)">ｘ</span></div>');
}
function getCheckedUseridString(){
	var ids = new Array();
	$('.checkedUserClass').each(function(i,v){
		ids.push($(v).attr('user'));
	});
	return ids.join(',');
}
function getCheckedUseridObj(){
	var ids = {};
	$('.checkedUserClass').each(function(i,v){
		ids[$(v).attr('user')]=$(v).attr('user');
	});
	return ids;
}
</script>
</html>