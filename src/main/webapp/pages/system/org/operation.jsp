<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../inc/header.jsp"%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/script/easyui/css/default/easyui.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/script/easyui/css/icon.css'/>">
<script src="<c:url value='/resources/script/jquery-1.7.2.min.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/script/My97DatePicker4.8/WdatePicker.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/script/lhgdialog/lhgdialog.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/script/easyui/easyui-lang-zh_CN.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/script/easyui/jquery.easyui.min.js'/>" type="text/javascript"></script>
<style>

</style>
</head>
<body>
<div id='outterDiv' width='100%' height='100%' style='overflow:auto;position:absolute;margin:0px;padding:0px;left:0px;top:0px'>
	<div id='topDiv' style='border-bottom:1px solid #ccc;'>
		<div  style='padding-top: 10px;'>
		</div>
	</div>
	<div id='menuTreeDiv'  style='float:left;border-right:1px solid #ccc;border-bottom:1px solid #ccc;overflow:auto;'>
		<ul id="menuTree"></ul>
	</div>
	<div id='operationDiv' style='overflow:auto;border:0px'>
		<iframe id='operationIframe' src='' style='border:0px' border='0px'></iframe>
	</div>
</div>
</body>
<script>
initStyle();
createMenuTree();
function initStyle(){
	var height = $(document).height();
	var width = $(document).width();
	var topDivHeight = 40; 
	var topDivWidth = width-1;
	
	
	var menuTreeDivWidth = 250;
	var menuTreeDivHeight = height-topDivHeight-3;

	
	var operationDivWidth = width-menuTreeDivWidth-2;
	var operationDivHeight = menuTreeDivHeight;
	
	var operationIframeWidth = operationDivWidth;
	var operationIframeHeight = operationDivHeight-4;

	
	
	$('#topDiv').css('height',topDivHeight+'px');
	$('#topDiv').css('width',topDivWidth+'px');

	$('#menuTreeDiv').css('height',menuTreeDivHeight+'px');
	$('#menuTreeDiv').css('width',menuTreeDivWidth+'px');
	
	$('#operationDiv').css('height',operationDivHeight+'px');
	$('#operationDiv').css('width',operationDivWidth+'px');
	
	$('#operationIframe').css('height',operationIframeHeight+'px');
	$('#operationIframe').css('width',operationIframeWidth+'px');
}

function createMenuTree(){
	$('#menuTree').tree({
		checkbox: false,   
		method:'GET',
        url: '${ctx}/system/org/resource/subTreeNodesOfModelAndMenuWithoutAuth/0',  
        onBeforeExpand:function(node,param){  
        	$('#menuTree').tree('options').url = "${ctx}/system/org/resource/subTreeNodesOfModelAndMenuWithoutAuth/" + node.attributes.id;
        },  
        onClick:function(node){
			var nodetype = node.attributes.nodetype;
			if('menu'==nodetype){
				var url = '${ctx}/system/org/operation/'+node.attributes.id;
				$('#operationIframe').attr('src',url);
			}
		}             
	});
}
</script>
</html>