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
				editor="editor1"/>
	</form>
	<!-- ckeditor:replaceAll basePath="../../../resources/script/ckeditor/"/-->
</body>
<script type="text/template" id='template'>
	<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/script/easyui/css/icon.css'/>">
	<title>测试生成</title>
	<style>
		.bodyClass{
			background-color:#EEEEEE;
		}
		.outterClass{
			position:absolute;
			width:100%;
			top:0px;
			left:0px;
		}
		.innerClass{
			width:1024px;
			height:100%;
			margin:0 auto;
			background-color:#ffffff;
		}
		.titleClass{
			height:80px;
			width:100%;
			margin-top:20px;
			padding-top:30px;
			line-height:50px;
			text-align:center;
			font-family: 宋体;
			font-weight: bold;
			font-size: 22px;
		}
		.contentClass{
			width:100%;
			font-family: 宋体;
			font-size: 12px;
		}
		.contentTableClass{
			width:100%;
		}
		.contentTableTrClass{
			line-height:35px
		}
		.contentTableTdLabelClass{
			width:15%;
			text-align:right;
		}
		.contentTableTdDataClass{
			width:35%;
		}
	</style>
  </head>
<body class="bodyClass">
	<form>
		<div class="outterClass">
			<div class="innerClass">
				<div class="titleClass">测试标题</div>
				<div class="contentClass">
					<table class='contentTableClass'>
						<tr class='contentTableTrClass'>
							<td class="contentTableTdLabelClass"><label for="name">姓名：</label></td>
							<td class="contentTableTdDataClass"><input type="text" name="name" id="name"/></td>
							<td class="contentTableTdLabelClass"><label>性别：</label></td>
							<td class="contentTableTdDataClass">
								<label for="male">Male</label>
							  <input type="radio" name="sex" id="male" />
							  <label for="female">Female</label>
							  <input type="radio" name="sex" id="female" />
							</td>
						</tr>
						<tr class='contentTableTrClass'>
							<td class="contentTableTdLabelClass"><label for="name">教育程度：</label></td>
							<td class="contentTableTdDataClass">
								<select id='education' name='education'>
									<option value="小学">小学</option>
									<option value="初中">初中</option>
									<option value="高中">高中</option>
									<option value="本科">本科</option>
									<option value="大专">大专</option>
								</select>
							</td>
							<td class="contentTableTdLabelClass"><label>兴趣爱好：</label></td>
							<td class="contentTableTdDataClass">
								<label for="basketball">篮球</label>
								<input type="checkbox" value="篮球" name="intresting" id="basketball">
								<label for="football">足球</label>
								<input type="checkbox" name="intresting" id="football">
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</form>
</body>
</html>
</script>
<script>
//CKEDITOR.instances.content.setData('aaaaaaaaaaaa');
  CKEDITOR.on( 'instanceReady', function( ev ) {

       var editor = ev.editor;

       editor.setData($('#template').html());

    });
</script>
</html>