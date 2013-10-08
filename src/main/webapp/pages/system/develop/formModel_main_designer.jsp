<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../inc/header.jsp"%>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor" %>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link type="text/css" rel="stylesheet" href="<c:url value='/resources/script/ckeditor/contents.css'/>" mce_href="<c:url value='/resources/script/ckeditor/contents.css'/>" />  
<script src="<c:url value='/resources/script/jquery-1.7.2.min.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/script/ckeditor/ckeditor.js'/>" type="text/javascript"></script>
<style>

</style>
</head>
<body> 
	<form>
		<!-- textarea cols="80" id="editor1" name="editor1" rows="10">
		</textarea-->
		<ckeditor:editor textareaAttributes="${attr}"
				basePath="../../../resources/script/ckeditor/" config="${config}"
				editor="editor1" value="${value}"/>
	</form>
	<!-- ckeditor:replaceAll basePath="../../../resources/script/ckeditor/"/-->
</body>
</html>