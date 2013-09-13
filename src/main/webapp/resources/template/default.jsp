<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<title><sitemesh:title/></title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />

<link type="image/x-icon" href="${ctx}/resources/images/favicon.ico" rel="shortcut icon">
<link href="${ctx}/resources/script/bootstrap/2.3.2/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/resources/script/jquery-validation/1.11.1/validate.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/resources/styles/default.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/resources/script/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/resources/script/bootstrap/2.3.2/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${ctx}/resources/script/jquery-validation/1.11.1/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctx}/resources/script/jquery-validation/1.11.1/messages_bs_zh.js" type="text/javascript"></script>


<sitemesh:head/>
</head>

<body>
	<div class="container">
		<div>
			<%@ include file="/resources/template/header.jsp"%>
		</div>
		<div id="content">
			<sitemesh:body/>
		</div>
		<div>
			<%@ include file="/resources/template/footer.jsp"%>
		</div>
	</div>
</body>
</html>