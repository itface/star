<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/header.jsp"%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/script/easyui/css/default/easyui.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/script/easyui/css/icon.css'/>">
<script src="<c:url value='/resources/script/jquery-1.7.2.min.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/script/My97DatePicker4.8/WdatePicker.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/script/easyui/easyui-lang-zh_CN.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/script/easyui/jquery.easyui.min.js'/>" type="text/javascript"></script>
<style>

</style>
</head>
<body>
<div id='menuTree'>
</div>
</body>
<script>
/*
$('#menuTree').tree({
	data: [{
		text: '菜单',
		state: 'open',
		children: [{
			text: '模块管理',
			attributes:{  
            	url:'${ctx}/system/org/menu'
        	},
		},{
			text: '菜单管理'
		},{
			text: '菜单操作管理'
		},{
			text: '角色管理'
		},{
			text: '权限组管理'
		},{
			text: '用户管理'
		}]
	}],
	onClick:function(node){
		var url = node.attributes.url;
		$(window.parent.document).find('#iframe_main').attr('src',url);
	}
});
*/
$(function () {
createMenuTree();
function createMenuTree(){
	$.ajaxSetup({cache: false});
	$('#menuTree').tree({
		checkbox: false,   
		method:'GET',
        url: '${ctx}/system/org/resource/subTreeNodesOfModelAndMenuWithAuth/0',   
        onBeforeExpand:function(node,param){  
        	if(node.attributes.nodetype=='model'){
        		$('#menuTree').tree('options').url = "${ctx}/system/org/resource/subTreeNodesOfModelAndMenuWithAuth/" + node.attributes.id;
        	}else{
        		$('#menuTree').tree('options').url ='';
        	}
        },  
        onClick:function(node){
			var url = node.attributes.url;
			if(url&&url!=''){
				if(url.indexOf('/')!=0){
					url="${ctx}/"+url;
				}else{
					url="${ctx}"+url;
				}
				$(window.parent.document).find('#iframe_main').attr('src',url);
			}
		}             
	});
	//$.ajaxSetup({cache: true});
}
});

</script>
</html>