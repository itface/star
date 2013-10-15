<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/inc/html5Header.jsp"%>
<html>
  <head>
  	<title>star</title>
  	<meta name="viewport" content="width=device-width, initial-scale=1.0">
  	<meta name="description" content="">
    <meta name="author" content="">
  	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/script/bootstrap/2.3.2/css/bootstrap.min.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/script/bootstrap/2.3.2/css/bootstrap-responsive.min.css'/>">
	<script src="<c:url value='/resources/script/jquery-1.7.2.min.js'/>" type="text/javascript"></script>
  	<script src="<c:url value='/resources/script/bootstrap/2.3.2/js/bootstrap.min.js'/>" type="text/javascript"></script>
  	<style type="text/css">
      body {
        padding-top: 150px;
        padding-bottom: 40px;
        background-color: #f5f5f5;
      }
      .form-signin {
        max-width: 300px;
        padding: 19px 29px 29px;
        margin: 0 auto 20px;
        background-color: #fff;
        border: 1px solid #e5e5e5;
        -webkit-border-radius: 5px;
           -moz-border-radius: 5px;
                border-radius: 5px;
        -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
           -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
                box-shadow: 0 1px 2px rgba(0,0,0,.05);
      }
      .form-signin .form-signin-heading,
      .form-signin .checkbox {
        margin-bottom: 10px;
      }
      .form-signin input[type="text"],
      .form-signin input[type="password"] {
        font-size: 16px;
        height: auto;
        margin-bottom: 15px;
        padding: 7px 9px;
      }
    </style>
  </head>
  <body>
		<div class="container">
	      <form class="form-signin" id="loginForm" method="POST" action="${ctx}/login">
	        <h2 class="form-signin-heading"></h2>
	        <input type="text" id="username" name="username" class="input-block-level input-medium required" placeholder="用户名">
	        <input type="password" id="password" name="password" class="input-block-level input-medium required" placeholder="密码">
	        <label class="checkbox">
	          <input type="checkbox" value="remember-me"> 记住密码
	        </label>
	        <button class="btn btn-large btn-primary" type="submit" style="width:300px">登录</button>
	      </form>
    	</div>
		<!--div>
		<form id="loginForm" method="POST" action="${ctx}/login" class="form-horizontal">
	   		<div style='margin-top:200px;margin-left:200px;'>
	   			<div>${error_info}</div>
	   			<div class="control-group">
					<label for="username" class="control-label">名称:</label>
					<div class="controls">
						<input type="text" id="username" name="username"  value="admin" class="input-medium required"/>
					</div>
				</div>
				<div class="control-group">
					<label for="password" class="control-label">密码:</label>
					<div class="controls">
						<input type="password" id="password" name="password" value="123456" class="input-medium required"/>
					</div>
				</div>
						
				<div class="control-group">
					<div class="controls">
						<label class="checkbox" for="rememberMe"><input type="checkbox" id="rememberMe" name="rememberMe"/> 记住我</label>
						<input id="submit_btn" class="btn btn-primary" type="submit" value="登录"/>
					</div>
				</div>
	   		</div>
	   		</form>
	   	</div-->
	<script>
	/*
		$(document).ready(function() {
			$("#loginForm").validate();
		});
		*/
	</script>
  </body>
</html>