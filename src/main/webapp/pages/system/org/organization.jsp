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
			<div><button onclick='addOrg()'>添加组织</button><button onclick='editOrg()'>编辑组织</button><button onclick='deleteOrg()'>删除组织</button></div>
		</div>
	</div>
	<div id='orgTreeDiv'  style='float:left;border-right:1px solid #ccc;border-bottom:1px solid #ccc;overflow:auto;'>
		<ul id="orgTree"></ul>
	</div>
	<div id='userDiv' style='overflow:auto;border:0px'>
		<iframe id='userIframe' src='' style='border:0px' border='0px'></iframe>
	</div>
</div>
</body>
<script>
initStyle();
createorgTree();
function initStyle(){
	var height = $(document).height();
	var width = $(document).width();
	var topDivHeight = 40; 
	var topDivWidth = width-1; //1为orgTreeDiv右边框的像素
	
	
	var orgTreeDivWidth = 250;
	var orgTreeDivHeight = height-topDivHeight-3;//3为topDiv底边框和orgTreeDiv底边框像素和一个未知的像素

	
	var userDivWidth = width-orgTreeDivWidth-2;//2为orgTreeDiv右边框的像素和一个未知道像素
	var userDivHeight = orgTreeDivHeight;
	
	var userIframeWidth = userDivWidth;
	var userIframeHeight = userDivHeight-4;

	
	
	$('#topDiv').css('height',topDivHeight+'px');
	$('#topDiv').css('width',topDivWidth+'px');

	$('#orgTreeDiv').css('height',orgTreeDivHeight+'px');
	$('#orgTreeDiv').css('width',orgTreeDivWidth+'px');
	
	$('#userDiv').css('height',userDivHeight+'px');
	$('#userDiv').css('width',userDivWidth+'px');
	
	$('#userIframe').css('height',userIframeHeight+'px');
	$('#userIframe').css('width',userIframeWidth+'px');
}

function createorgTree(){
	$('#orgTree').tree({
		checkbox: false,   
		method:'GET',
        url: '${ctx}/system/org/organization/findSons/0',   
        onBeforeExpand:function(node,param){  
        	$('#orgTree').tree('options').url = "${ctx}/system/org/organization/findSons/" + node.attributes.id;
        },  
        onClick:function(node){
			var url = '${ctx}/system/org/user/'+node.attributes.id;
			$('#userIframe').attr('src',url);
		}             
	});
}
	
	function deleteOrg(){
		var node = $('#orgTree').tree('getSelected');
		if(node){
			var id = node.attributes.id;
			$.ajax({
				url:'${ctx}/system/org/organization/'+id,
				type:'POST',
				data:{_method:'DELETE'},
				success:function(data, textStatus, jqXHR){
					alert('删除成功');
					editAndDelReloadTree();
					$('#userIframe').attr('src','');
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
	function addOrg(){
			var node = $('#orgTree').tree('getSelected');
			var nodeid = 0;
			if(node){
				nodeid=node.attributes.id;
			}
			var dialog = $.dialog({
					 		id:'addOrgDialog',
						    lock: true,
						    min:false,
						    max:false,
						    cancel:false,
						    background: '#FFF', /* 背景色 默认的遮罩背景色为:#DCE2F1浅蓝护眼色 */
						    opacity: 0.5,       /* 透明度 */
						    content: 'url:${ctx}/system/org/organization/newPage/'+nodeid,
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
	function editOrg(){
			var node = $('#orgTree').tree('getSelected');
			var nodeid = 0;
			if(node){
				nodeid=node.attributes.id;
			}else{
				alert('请选择您要编辑的节点');
				return false;
			}
			var dialog = $.dialog({
					 		id:'addOrgDialog',
						    lock: true,
						    min:false,
						    max:false,
						    cancel:false,
						    background: '#FFF', /* 背景色 默认的遮罩背景色为:#DCE2F1浅蓝护眼色 */
						    opacity: 0.5,       /* 透明度 */
						    content: 'url:${ctx}/system/org/organization/'+nodeid,
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
		var node = $('#orgTree').tree('getSelected');
		if(node){
			$('#orgTree').tree('reload',node.target);
		}else{
			$('#orgTree').empty();
			createorgTree();
		}
	}
	function editAndDelReloadTree(){
		var node = $('#orgTree').tree('getSelected');
		if(node){
			var parent = $('#orgTree').tree('getParent',node.target);
			if(parent){
				$('#orgTree').tree('reload',parent.target);
			}else{
				$('#orgTree').empty();
				createorgTree();
			}
			
		}
	}
	$.dialog.data('addReloadTree',addReloadTree);
	$.dialog.data('editAndDelReloadTree',editAndDelReloadTree);
</script>
</html>