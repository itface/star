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
			<div><button onclick='addModel()'>添加模块</button><button onclick='editModel()'>编辑模块</button><button onclick='deleteModel()'>删除模块</button></div>
		</div>
	</div>
	<div id='modelTreeDiv'  style='float:left;border-right:1px solid #ccc;border-bottom:1px solid #ccc;overflow:auto;'>
		<ul id="modelTree"></ul>
	</div>
	<div id='menuDiv' style='overflow:auto;border:0px'>
		<iframe id='menuIframe' src='' style='border:0px' border='0px'></iframe>
	</div>
</div>
</body>
<script>
initStyle();
createModelTree();
function initStyle(){
	var height = $(document).height();
	var width = $(document).width();
	var topDivHeight = 40; 
	var topDivWidth = width-1; //1为modelTreeDiv右边框的像素
	
	
	var modelTreeDivWidth = 250;
	var modelTreeDivHeight = height-topDivHeight-3;//3为topDiv底边框和modelTreeDiv底边框像素和一个未知的像素

	
	var menuDivWidth = width-modelTreeDivWidth-2;//2为modelTreeDiv右边框的像素和一个未知道像素
	var menuDivHeight = modelTreeDivHeight;
	
	var menuIframeWidth = menuDivWidth;
	var menuIframeHeight = menuDivHeight-4;

	
	
	$('#topDiv').css('height',topDivHeight+'px');
	$('#topDiv').css('width',topDivWidth+'px');

	$('#modelTreeDiv').css('height',modelTreeDivHeight+'px');
	$('#modelTreeDiv').css('width',modelTreeDivWidth+'px');
	
	$('#menuDiv').css('height',menuDivHeight+'px');
	$('#menuDiv').css('width',menuDivWidth+'px');
	
	$('#menuIframe').css('height',menuIframeHeight+'px');
	$('#menuIframe').css('width',menuIframeWidth+'px');
}

function createModelTree(){
	$('#modelTree').tree({
		checkbox: false,   
		method:'GET',
        url: '${ctx}/system/org/model/findSons/0',   
        onBeforeExpand:function(node,param){  
        	$('#modelTree').tree('options').url = "${ctx}/system/org/model/findSons/" + node.id;
        },  
        onClick:function(node){
			var url = '${ctx}/system/org/menu/page/'+node.id;
			$('#menuIframe').attr('src',url);
		}             
	});
}
	
	function deleteModel(){
		var node = $('#modelTree').tree('getSelected');
		if(node){
			var id = node.id;
			$.ajax({
				url:'${ctx}/system/org/model/'+id,
				type:'POST',
				data:{_method:'DELETE'},
				success:function(data, textStatus, jqXHR){
					alert('删除成功');
					editAndDelReloadTree();
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
	function addModel(){
			var node = $('#modelTree').tree('getSelected');
			var nodeid = 0;
			if(node){
				nodeid=node.id;
			}
			var dialog = $.dialog({
					 		id:'addModelDialog',
						    lock: true,
						    min:false,
						    max:false,
						    cancel:false,
						    background: '#FFF', /* 背景色 默认的遮罩背景色为:#DCE2F1浅蓝护眼色 */
						    opacity: 0.5,       /* 透明度 */
						    content: 'url:${ctx}/system/org/model/newPage/'+nodeid,
						    title:''
						    /*
						    //icon: 'error.gif',
						    init:function(){
						    	
						    }
						    ok: function () {
						        //$.dialog({content: '再来一个锁屏', lock: true, parent:this});
						        return false;
						    },
						    
						    //cancel: true
						    */
			});
			$.dialog.data('dialog',dialog);//：跨框架数据共享写入接口
	}
	function editModel(){
			var node = $('#modelTree').tree('getSelected');
			var nodeid = 0;
			if(node){
				nodeid=node.id;
			}else{
				alert('请选择您要编辑的节点');
				return false;
			}
			var dialog = $.dialog({
					 		id:'addModelDialog',
						    lock: true,
						    min:false,
						    max:false,
						    cancel:false,
						    background: '#FFF', /* 背景色 默认的遮罩背景色为:#DCE2F1浅蓝护眼色 */
						    opacity: 0.5,       /* 透明度 */
						    content: 'url:${ctx}/system/org/model/'+nodeid,
						    title:''
						    /*
						    //icon: 'error.gif',
						    init:function(){
						    	
						    }
						    ok: function () {
						        //$.dialog({content: '再来一个锁屏', lock: true, parent:this});
						        return false;
						    },
						    
						    //cancel: true
						    */
			});
			$.dialog.data('dialog',dialog);//：跨框架数据共享写入接口
	}
	function addReloadTree(){
		var node = $('#modelTree').tree('getSelected');
		if(node){
			$('#modelTree').tree('reload',node.target);
		}else{
			$('#modelTree').empty();
			createModelTree();
		}
	}
	function editAndDelReloadTree(){
		var node = $('#modelTree').tree('getSelected');
		if(node){
			var parent = $('#modelTree').tree('getParent',node.target);
			if(parent){
				$('#modelTree').tree('reload',parent.target);
			}else{
				$('#modelTree').empty();
				createModelTree();
			}
			
		}
	}
	$.dialog.data('addReloadTree',addReloadTree);
	$.dialog.data('editAndDelReloadTree',editAndDelReloadTree);
</script>
</html>