<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/header.jsp"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style>
body{
  margin:0px;
}
.outter{
	
}
.top{
	position:absolute;
	width:100%;
	height:5%;
	background-color:#148498;
}
.center{
	position:absolute;
	top:5%;
	width:100%;
	height:90%;
}
.center_left{
	width:15%;
	float:left;
	height:100%;
	overflow:auto
}
.center_line{
	width:0.3%;
	height:100%;
	float:left;
	background-color:#148498;
}
.center_main{
	height:100%;
	width:84.4%;
	height:100%;
	float:left;

}
.footer{
	position:absolute;
	width:100%;
	bottom:0;
	height:5%;
	background-color:#148498;
}
.exit{
	position:absolute;
	right:0;
	padding-right:50px;
	padding-top:5px;
	font-size:18px;
	
}
</style>
</head>
<body>
<div id='front_index_outter' class='outter'>
	<div id="front_index_top" class='top'>
		<div id="front_index_exit" class='exit'>
			<a href="${ctx}/logout"><shiro:principal/>退出</a>
		</div>
	</div>
	<div id="front_index_center" class='center'>
		<div id="front_index_center_left" class='center_left'>
			<iframe src="${ctx}/main/left" width='97%' height='97%'></iframe>
		</div>
		<div id="front_index_center_line" class='center_line'></div>
		<div id="front_index_center_main" class='center_main'>
			<iframe id="iframe_main" src="" width="100%" height="100%" border=0></iframe>
		</div>
	</div>
	<div id="front_index_bottom" class='footer'></div>
</div>
</body>
<script>
</script>
</html>