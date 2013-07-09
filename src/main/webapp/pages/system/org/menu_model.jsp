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
	模块名称：<form:input path="model.modelName"/><form:errors path="password" />
	</div>
	<div >
	显示顺序：<form:select  path="model.displayOrder" items="${list}"/>
	</div>
	<form:hidden path="model.id"/>
	<form:hidden path="model.parentModel"/>
	<div style='right:0px;bottom:5px;height:20px;position:absolute'>
		<input type='button' value='保存' id='cancel' onclick="submit()"/>
		<input type='button' value='取消' id='ok'  onclick="closeWin()"/>
	</div>
</body>
<script>
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
	$.ajax({
		url:'${ctx}/system/org/model/'+$('#id').val,
		//async:false,
		//dataType:'json'
		data:{id:id,displayOrder:$('#displayOrder').val(),modelName:$('#modelName').val(),parentModel:$('#parentModel').val(),_method:_method},
		type:'POST',
		success:function(data, textStatus, jqXHR){
			reload(_method);
			alert('保存成功');
			closeWin();
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			//XMLHttpRequest 对象、错误信息、（可选）捕获的异常对
			alert('保存失败');
		}
	});
}
</script>
</html>