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
			<div><button onclick='addTable()'>添加表</button><button onclick='deleteTable()'>删除表</button></div>
		</div>
	</div>
	<div id='leftTreeDiv'  style='float:left;border-right:1px solid #ccc;border-bottom:1px solid #ccc;overflow:auto;'>
		<ul id="leftTree"></ul>
	</div>
	<div id='rightDiv' style='overflow:auto;border:0px'>
		<iframe id='rightIframe' src='' style='border:0px' border='0px'></iframe>
	</div>
</div>
</body>
<script>
initStyle();
createleftTree();
function initStyle(){
	var height = $(document).height();
	var width = $(document).width();
	var topDivHeight = 40; 
	var topDivWidth = width-1; //1为leftTreeDiv右边框的像素
	
	
	var leftTreeDivWidth = 250;
	var leftTreeDivHeight = height-topDivHeight-3;//3为topDiv底边框和leftTreeDiv底边框像素和一个未知的像素

	
	var rightDivWidth = width-leftTreeDivWidth-2;//2为leftTreeDiv右边框的像素和一个未知道像素
	var rightDivHeight = leftTreeDivHeight;
	
	var rightIframeWidth = rightDivWidth;
	var rightIframeHeight = rightDivHeight-4;

	
	
	$('#topDiv').css('height',topDivHeight+'px');
	$('#topDiv').css('width',topDivWidth+'px');

	$('#leftTreeDiv').css('height',leftTreeDivHeight+'px');
	$('#leftTreeDiv').css('width',leftTreeDivWidth+'px');
	
	$('#rightDiv').css('height',rightDivHeight+'px');
	$('#rightDiv').css('width',rightDivWidth+'px');
	
	$('#rightIframe').css('height',rightIframeHeight+'px');
	$('#rightIframe').css('width',rightIframeWidth+'px');
}

function createleftTree(){
	$('#leftTree').tree({
		checkbox: false,   
		method:'GET',
        url: '${ctx}/system/develop/table/tableTree',    
        onClick:function(node){
			var url = '${ctx}/system/develop/table/'+node.attributes.id;
			$('#rightIframe').attr('src',url);
		}             
	});
}
	
function deleteTable(){
	var node = $('#leftTree').tree('getSelected');
	if(node){
		var id = node.attributes.id;
		$.ajax({
			url:'${ctx}/system/develop/table/'+id,
			type:'POST',
			data:{_method:'DELETE'},
			success:function(data, textStatus, jqXHR){
				alert('删除成功');
				reloadTree();
				$('#rightIframe').attr('src','');
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				//XMLHttpRequest 对象、错误信息、（可选）捕获的异常对
				alert('删除失败');
			}
		});
	}else{
		alert('请选择你要删除的节点');
	}
}
function addTable(){
		var url = '${ctx}/system/develop/table/0';
		$('#rightIframe').attr('src',url);
}
function reloadTree(){
	$('#leftTree').empty();
	createleftTree();
}
</script>
</html>