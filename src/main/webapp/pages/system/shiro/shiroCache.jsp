<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../inc/header.jsp"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="<c:url value='/resources/script/jquery-1.7.2.min.js'/>" type="text/javascript"></script>
<style>

</style>
</head>
<body>
<div>
<shiro:hasPermission name="/shiro/cachae/refreshCache:read"> 
<button onclick='refreshCache()'>刷新权限缓存</button>
</shiro:hasPermission>
</div>
</body>
<script>
function refreshCache(){
	$.ajax({
		url:'${ctx}/shiro/cache/refreshCache',
		type:'GET',
		success:function(){
			alert('刷新成功');
		},
		error:function(){
			alert('刷新失败');
		}
	});
}
</script>
</html>