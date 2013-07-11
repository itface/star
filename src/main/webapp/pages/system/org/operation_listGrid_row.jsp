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
	名称：<form:input path="operation.name"/>
	</div>
	<div >
	URL ：<form:input  path="operation.url"/>
	</div>
	<div >
	操作类型：<form:select  path="operation.actionflag" items="${actionFlagList}"/>
	</div>
	<form:hidden path="operation.id"/>
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
function reloadMenuGrid(){
	var api = frameElement.api;
	var  W = api.opener; 
	var reloadOperationGrid = W.$.dialog.data('reloadOperationGrid');
	reloadOperationGrid();
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
		url:'${ctx}/system/org/operation/${menuid}/grid/'+id,
		//async:false,
		//dataType:'json'
		data:{id:id,actionflag:$('#actionflag').val(),url:$('#url').val(),name:$('#name').val(),_method:_method},
		type:'POST',
		success:function(data, textStatus, jqXHR){
			if(data=='S'){
				reloadMenuGrid();
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
</script>
</html>