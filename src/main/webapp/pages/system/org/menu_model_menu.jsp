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
	<input type="hidden" id="modelid" value="${modelid}" />
</body>
<script>
$(function () {
	var selectedRowId=0;
	$('#list').jqGrid({
	    url: '${ctx}/system/org/menu/${modelid}/grid',
	    ajaxGridOptions: { contentType: 'application/json; charset=utf-8' },
	    datatype: 'json',
	    mtype: 'GET',
	    jsonReader:{
	    	repeatitems:false
	    },
	    ExpandColClick:false,
	    colNames: ['ID','名称','URL','显示顺序','模块ID'],
	    colModel: [
		    { name: 'id', index: 'id', hidden: true, width: 1,sorttype: 'int', key: true,editable:true },
		   	{ name: 'name', index: 'name',sorttype: 'string', width: 200,editable:true},
		    { name: 'url', index: 'url', width: 200,sorttype: 'string',editable:true},
		    { name: 'displayorder', index: 'displayorder',width: 200,editable:true},
		    { name: 'model_id', index: 'model_id',hidden: true, width: 200,editable:true}
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
	    caption:'菜单管理',
	    //height: 'auto'
	    toolbar: [true,"top"],
	    ondblClickRow:function(rowid,iCol,cellcontent,e){
	    	addMenu(rowid);
	    },
	    onCellSelect:function(rowid,iCol,cellcontent,e){
	    	selectedRowId=rowid;
	    }
	});
	$("#t_list").append("<span><input type='button' value='添加' id='addBtn'/><input type='button' value='删除' id='delBtn'/></span>");
	$("#addBtn").click(function(){
		addMenu(0);
	});
	$("#editBtn").click(function(){
		if(selectedRowId&&selectedRowId>0){
			addMenu(selectedRowId);
		}else{
			alert("请选择要编辑的行");
		}
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
				url:'${ctx}/system/org/menu/${modelid}/grid',
				data:{_method:'DELETE',menuIdArr:arr},
				type:'POST',
				success:function(data, textStatus, jqXHR){
					alert('删除成功');
					reloadMenuGrid();
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
	function addMenu(rowid){
			var modelid = $('#modelid').val();
			var dialog = $.dialog({
				 		id:'addModelDialog',
					    lock: true,
					    min:false,
					    max:false,
					    cancel:false,
					    background: '#FFF', /* 背景色 默认的遮罩背景色为:#DCE2F1浅蓝护眼色 */
					    opacity: 0.5,       /* 透明度 */
					    content: 'url:${ctx}/system/org/menu/'+modelid+'/grid/'+rowid,
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
	function reloadMenuGrid(){
		$('#list').trigger('reloadGrid');
	}
	$.dialog.data('reloadMenuGrid',reloadMenuGrid);
});

</script>
</html>