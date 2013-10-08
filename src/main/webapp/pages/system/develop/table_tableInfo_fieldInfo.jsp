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
<form id="fieldForm">
	<div >
	字段名：<form:input path="field.name"/>
	</div>
	<div >
	说明 ：<form:input  path="field.text"/>
	</div>
	<div >
	字段类型：
		<form:select path="field.fieldtype" onChange="displayFieldLength()">  
            <form:option value="string">字符</form:option> 
            <form:option value="int">整数</form:option> 
            <form:option value="long">长整数</form:option> 
            <form:option value="double">小数</form:option> 
            <form:option value="date">日期</form:option> 
        </form:select>  
	</div>
	<div >
	字段长度：<form:input  path="field.fieldlength"/>
	</div>
	<div >
	是否必填：
		<form:select path="field.allownull">  
            <form:option value="0">不必填</form:option> 
            <form:option value="1">必填</form:option> 
        </form:select>  
	</div>
	<div>
	外观：
		<form:select path="field.style">  
            <form:option value="text">文本</form:option> 
            <form:option value="textarea">文本域</form:option>
            <form:option value="select">下拉列表</form:option> 
            <form:option value="radio">单选框</form:option> 
            <form:option value="checkbox">复选框</form:option> 
        </form:select>  
	</div>
	<form:hidden path="field.id"/>
	<input type="hidden" id="_method" name="_method" value="${_method}"/>
</form>
	<div style='right:0px;bottom:5px;height:20px;position:absolute'>
		<input type='button' value='保存' id='cancel' onclick="submit()"/>
		<input type='button' value='取消' id='ok'  onclick="closeWin()"/>
	</div>
</body>
<script>
displayFieldLength();
function closeWin(){
	var api = frameElement.api;
	var  W = api.opener; 
	var dia = W.$.dialog.data('dialog');
	dia.close();
}
function reloadParentGrid(){
	var api = frameElement.api;
	var  W = api.opener; 
	var reloadGrid = W.$.dialog.data('reloadGrid');
	reloadGrid();
}
function displayFieldLength(){
		var type = $('#fieldtype').val();
		if('date'==type){
			$('#fieldlength').parent().hide();
		}else{
			$('#fieldlength').parent().show();
		}
}
function submit(){
	var id = $('#id').val();
	if(!(id>0)){
		id=0;
	}
	$.ajax({
		url:'${ctx}/system/develop/table/${tableid}/'+id,
		//async:false,
		//dataType:'json'
		data:$("#fieldForm").serialize(),
		type:'POST',
		success:function(data, textStatus, jqXHR){
			if(data=='S'){
				reloadParentGrid();
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