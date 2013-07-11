<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../inc/header.jsp"%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/script/jqgrid/css/jquery.jqgrid.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/script/jqgrid/css/jquery.ui.css'/>">
<script src="<c:url value='/resources/script/jquery-1.7.2.min.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/script/lhgdialog/lhgdialog.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/script/jqgrid/grid.locale-cn.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/script/jqgrid/jquery.jqGrid.src.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/script/jqgrid/jqgrid_extend.js'/>" type="text/javascript"></script>
<style>

</style>
</head>
<body>
	<div >
		<table id="list">
		</table>
	</div>
	<input type="hidden" id="menuid" value="${menuid}" />
</body>
<script>
$(function () {
	var selectedRowId=0;
	$('#list').jqGrid({
	    url: '${ctx}/system/org/operation/${menuid}/grid',
	    ajaxGridOptions: { contentType: 'application/json; charset=utf-8' },
	    datatype: 'json',
	    mtype: 'GET',
	    jsonReader:{
	    	repeatitems:false
	    },
	    ExpandColClick:false,
	    colNames: ['ID','名称','URL','操作类型','菜单ID'],
	    colModel: [
		    { name: 'id', index: 'id', hidden: true, width: 1,sorttype: 'int', key: true,editable:true },
		   	{ name: 'name', index: 'name',sorttype: 'string', width: 200,editable:true},
		    { name: 'url', index: 'url', width: 200,sorttype: 'string',editable:true},
		    { name: 'actionflag', index: 'actionflag',width: 200,editable:true},
		    { name: 'menu_id', index: 'menu_id',hidden: true, width: 200,editable:true}
	    ],
	    gridview: true,
	    //sortname: 'ID',
	    //sortorder: "desc",
	    rownumbers:true,
	    multiselect:true,
	    pager: "false", 
	    //autowidth: true,
	    width:850,
	    height:500,
	    editurl:'',
	    //toppager: true,
	    caption:'操作管理',
	    //height: 'auto'
	    toolbar: [true,"top"],
	    ondblClickRow:function(rowid,iCol,cellcontent,e){
	    	addOperation(rowid);
	    },
	    onCellSelect:function(rowid,iCol,cellcontent,e){
	    	selectedRowId=rowid;
	    }
	});
	$("#t_list").append("<span><input type='button' value='添加' id='addBtn'/><input type='button' value='删除' id='delBtn'/></span>");
	$("#addBtn").click(function(){
		addOperation(0);
	});
	$("#delBtn").click(function(){
		var selectRows = jQuery('#list').jqGrid('getGridParam','selarrrow');
		if(selectRows&&selectRows.length>0){
			var arr = new Array();
			$(selectRows).each(function(i,v){
				 var id= jQuery('#list').jqGrid('getCell',selectRows[i],'id');
				 arr.push(id);
			});
			//禁用深度递归的方式序列化对象
			jQuery.ajaxSettings.traditional = true;
			$.ajax({
				url:'${ctx}/system/org/operation/${menuid}/grid',
				data:{_method:'DELETE',operationidArr:arr},
				type:'POST',
				success:function(data, textStatus, jqXHR){
					alert('删除成功');
					reloadOperationGrid();
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
					//XMLHttpRequest 对象、错误信息、（可选）捕获的异常对
					alert('删除失败');
				}
			});
			delete jQuery.ajaxSettings['traditional'];
		}else{
			alert("请选择要删除的行");
		}
	});
	function addOperation(rowid){
			var menuid = $('#menuid').val();
			var dialog = $.dialog({
				 		id:'addModelDialog',
					    lock: true,
					    min:false,
					    max:false,
					    cancel:false,
					    background: '#FFF', /* 背景色 默认的遮罩背景色为:#DCE2F1浅蓝护眼色 */
					    opacity: 0.5,       /* 透明度 */
					    content: 'url:${ctx}/system/org/operation/'+menuid+'/grid/'+rowid,
					    title:''
			});
			$.dialog.data('dialog',dialog);//：跨框架数据共享写入接口
	}
	function reloadOperationGrid(){
		$('#list').trigger('reloadGrid');
	}
	$.dialog.data('reloadOperationGrid',reloadOperationGrid);
});

</script>
</html>