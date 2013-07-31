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
	${gridHtml} 
</body>
<script>
	var ctx = "${ctx}";
	function createDialog(id,url){
			var dialog = $.dialog({
					    lock: true,
					    min:false,
					    max:false,
					    cancel:true,
					    background: '#FFF', /* 背景色 默认的遮罩背景色为:#DCE2F1浅蓝护眼色 */
					    opacity: 0.5,       /* 透明度 */
					    height:650,
					    width:600,
					    content: 'url:${ctx}/'+url
			});
			$.dialog.data(id,dialog);//：跨框架数据共享写入接口
	}
	function reloadGrid(id){
		$('#'+id).trigger('reloadGrid');
	}
	$.dialog.data('reloadGrid',reloadGrid);
	${gridScript}
</script>

</html>