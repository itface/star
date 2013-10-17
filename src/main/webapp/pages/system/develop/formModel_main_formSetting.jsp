<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../inc/header.jsp"%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/script/jqgrid/css/jquery.jqgrid.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/script/jqgrid/css/jquery.ui.css'/>">
<link href="<c:url value='/resources/script/uploadify/uploadify.css'/>" type="text/css" rel="stylesheet" />
<script src="<c:url value='/resources/script/jquery-1.7.2.min.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/script/lhgdialog/lhgdialog.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/script/jqgrid/grid.locale-cn.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/script/jqgrid/jquery.jqGrid.src.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/script/jqgrid/jqgrid_extend.js'/>" type="text/javascript"></script>
<script type="text/javascript" src="<c:url value='/resources/script/uploadify/swfobject.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/script/uploadify/jquery.uploadify.v2.1.4.js'/>"></script>
<style>

</style>
</head>
<body> 
	<div>
		<form action="${ctx}/uploadify/upload" method="post" enctype="multipart/form-data">
			<div id="fileQueue"></div>
		   	<input id="uploadify" name="uploadify" type="file" /><br/>
		   	<a href="javascript:jQuery('#uploadify').uploadifyUpload()">upload</a>&nbsp;
		    <a href="javascript:jQuery('#uploadify').uploadifyClearQueue()">cancle</a>
		    <div id="result"></div>
		</form>
	</div>
</body>
<script>
$(document).ready(function() {
	  $('#uploadify').uploadify({
	    'uploader'  : '${ctx}/resources/script/uploadify/uploadify.swf',
	    'script'    : '${ctx}/uploadify/upload',
	    'cancelImg' : '${ctx}/resources/script/uploadify/cancel.png',
	    'folder'    : 'uploads',
	    'fileDataName'   : 'uploadify',
	    'removeCompleted' : false,
		'sizeLimit' :10240000,
		'simUploadLimit':10,
	    'multi'     : true,
	    'auto'      : false			
	  });
});
</script>

</html>