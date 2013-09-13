<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/inc/header.jsp"%>
<html>
  <head>
  </head>
  <body>
	<form id="loginForm" method="POST" action="${ctx}/login" class="form-horizontal">
		<div>
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
	   	</div>
	</form>
	<script>
		$(document).ready(function() {
			$("#loginForm").validate();
		});
	</script>
  </body>
</html>