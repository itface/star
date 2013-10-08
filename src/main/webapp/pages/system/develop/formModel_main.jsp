<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../inc/header.jsp"%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/script/easyui/css/default/easyui.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/script/easyui/css/icon.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/script/jqgrid/css/jquery.jqgrid.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/script/jqgrid/css/jquery.ui.css'/>">
<script src="<c:url value='/resources/script/jquery-1.7.2.min.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/script/lhgdialog/lhgdialog.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/script/jqgrid/grid.locale-cn.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/script/jqgrid/jquery.jqGrid.src.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/script/jqgrid/jqgrid_extend.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/script/easyui/easyui-lang-zh_CN.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/script/easyui/jquery.easyui.min.js'/>" type="text/javascript"></script>
<style>

</style>
</head>
<body  style="margin:0">
	<div id="mainTabPanel" class="easyui-tabs">
	</div>
</body>
<script>
$(function(){
	initTabs();
	$('#mainTabPanel').tabs({
		border:false,
		height:$(document).height(),
		onSelect:function(title,index){
			var obj = {
				'表单模型':'${ctx}/system/develop/formmodel/${formid}/formInfo',
				'外观设置':'${ctx}/system/develop/formmodel/${formid}/formSetting',
				'表单设计':'${ctx}/system/develop/formmodel/${formid}/designer',
				'代码生成':'${ctx}/system/develop/formmodel'
			};
			var tab = $('#mainTabPanel').tabs('getSelected');
			var src = $(tab).find('iframe').attr('src');
			if(src==null||src==''){
				$(tab).find('iframe').attr('src',obj[title]);
			}
		}
	});
	function initTabs(){
		addTab('表单模型',true);
		addTab('外观设置',false);
		addTab('表单设计',false);
		addTab('代码生成',false);
	}
	function addTab(title, selected){
		if ($('#mainTabPanel').tabs('exists', title)){
			$('#mainTabPanel').tabs('select', title);
		} else {
			//var clientWidth = document.body.clientWidth;
			//var clientHeight = document.body.offsetHeight;
			var windowHeight = $(window).height()-38;//减去顶部的距离和padding的距离
			var content = '<iframe scrolling="auto" frameborder="0"  src="" style="width:100%;height:100%"></iframe>';
			$('#mainTabPanel').tabs('add',{
				title:title,
				content:content,
				width:'100%',
				height:'99%',
				closable:false,
				selected:selected
			});
		}
	}
});
</script>
</html>