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
	<form id='tableForm'>
		<div>
			<div>
				<input type='button' onclick="postForm()" value="保存"/>
				<input type='button' onclick="genSource()" value="生成代码"/>
			</div>
			<div>
			表名：<form:input path="table.name"/>
			<form:errors   path = "table.name"/>   
			</div>
			<div>
			说明：<form:input path="table.text"/>
			<form:errors   path = "table.name"/>   
			</div>
			<table id="list">
			</table>
			<form:hidden path="table.id"/>
			<input type="hidden" id="_method" name="_method" value="${_method}"/>
		</div>
	</form>
</body>
<script>
	var selectedRowId=0;
	init();
	function init(){
		disableTablename();
	}
	function disableTablename(){
		if($('#id').val()>0){
			$('#name').attr('readOnly',true);
		}
	}
	function postForm(){
		var path = '${ctx}';
		$.ajax({
			type:'POST',
			url:'${ctx}/system/develop/table/${tableid}',
			data:$("#tableForm").serialize(),//序列化表单里所有的内容
			success: function(data){
				if(data!=null&&data.indexOf('/')==0){
					alert('保存成功');
					location.href=path+data;
				}else{
					alert(data);
				}
			},
			error:function(){
				alert('保存失败');
			}
		 });
	}
	$('#list').jqGrid({
	    url: '${ctx}/system/develop/table/'+$("#id").val()+'/fieldGrid',
	    ajaxGridOptions: { contentType: 'application/json; charset=utf-8' },
	    datatype: 'json',
	    mtype: 'GET',
	    jsonReader:{
	    	repeatitems:false
	    },
	    ExpandColClick:false,
	    colNames: ['ID','名称','说明','字段类型','字段长度'],
	    colModel: [
		    { name: 'id', index: 'id', hidden: true, width: 1,sorttype: 'int', key: true,editable:true },
		   	{ name: 'name', index: 'name',sorttype: 'string', width: 200,editable:true},
		    { name: 'text', index: 'text', width: 200,sorttype: 'string',editable:true},
		    { name: 'fieldtype', index: 'fieldtype',width: 200,editable:true},
		    { name: 'fieldlength', index: 'fieldlength',width: 200,editable:true}
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
	    caption:'字段管理',
	    //height: 'auto'
	    toolbar: [true,"top"],
	    ondblClickRow:function(rowid,iCol,cellcontent,e){
	    	addField(rowid);
	    },
	    onCellSelect:function(rowid,iCol,cellcontent,e){
	    	selectedRowId=rowid;
	    }
	});
	$("#t_list").append("<span><input type='button' value='添加' id='addBtn'/><input type='button' value='删除' id='delBtn'/></span>");
	$("#addBtn").click(function(){
		addField(0);
	});
	$("#delBtn").click(function(){
		var selectRows = jQuery('#list').jqGrid('getGridParam','selarrrow');
		if(selectRows&&selectRows.length>0){
			var arr = new Array();
			$(selectRows).each(function(i,v){
				 var id= jQuery('#list').jqGrid('getCell',selectRows[i],'id');
				 arr.push(id);
			});
			var ids = arr.join(',');
			$.ajax({
				url:'${ctx}/system/develop/table/'+$("#id").val()+'/fieldGrid',
				data:{_method:'DELETE',ids:ids},
				type:'POST',
				success:function(data, textStatus, jqXHR){
					alert('删除成功');
					reloadGrid();
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
					//XMLHttpRequest 对象、错误信息、（可选）捕获的异常对
					alert('删除失败');
				}
			});
		}else{
			alert("请选择要删除的行");
		}
	});
	function addField(rowid){
			var tableid = $("#id").val();
			if(!(tableid>0)){
				alert('请选保存主表');
				return;
			}
			var dialog = $.dialog({
					    lock: true,
					    min:false,
					    max:false,
					    cancel:false,
					    background: '#FFF', /* 背景色 默认的遮罩背景色为:#DCE2F1浅蓝护眼色 */
					    opacity: 0.5,       /* 透明度 */
					    height:650,
					    width:600,
					    content: 'url:${ctx}/system/develop/table/'+tableid+'/'+rowid,
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
	function reloadGrid(){
		$('#list').trigger('reloadGrid');
	}
	$.dialog.data('reloadGrid',reloadGrid);
	
	function genSource(){
			var tableid = $("#id").val();
			if(!(tableid>0)){
				alert('请选保存主表');
				return;
			}
			var srcDialog = $.dialog({
					    lock: true,
					    min:false,
					    max:false,
					    cancel:true,
					    background: '#FFF', /* 背景色 默认的遮罩背景色为:#DCE2F1浅蓝护眼色 */
					    opacity: 0.5,       /* 透明度 */
					    height:650,
					    width:800,
					    content: 'url:${ctx}/system/develop/table/'+tableid+'/getSource',
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
			$.dialog.data('srcDialog',srcDialog);//：跨框架数据共享写入接口
	}

</script>
</html>