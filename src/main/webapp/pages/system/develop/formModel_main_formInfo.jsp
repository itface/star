<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../inc/header.jsp"%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/script/easyui/css/default/easyui.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/script/easyui/css/icon.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/script/jqgrid/css/jquery.jqgrid.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/script/jqgrid/css/jquery.ui.css'/>">
<script src="<c:url value='/resources/script/jquery-1.7.2.min.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/script/lhgdialog/lhgdialog.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/script/jqgrid/grid.locale-cn.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/script/jqgrid/jquery.jqGrid.src.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/script/jqgrid/jqgrid_extend.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/script/easyui/easyui-lang-zh_CN.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/script/easyui/jquery.easyui.min.js'/>" type="text/javascript"></script>
<style>

</style>
</head>
<body>
	<form id='tableForm'>
		<div>
			<div>
				<input type='button' onclick="postForm()" value="保存"/>
			</div>
			<div>
			名称：<form:input path="form.name"/> 
			</div>
			<div>
			主表：<form:input path="form.mainTableModelId"/> 
			</div>
			<div>
			子表：<form:input path="form.subTableModelIds"/>
			</div>
			<form:hidden path="form.id"/>
			<input type="hidden" id="_method" name="_method" value="${_method}"/>
		</div>
	</form>
</body>
<script>
	init();
	function init(){
		createMainTableTree();
		createSubTableTree();
	}
	function postForm(){
		var path = '${ctx}';
		$.ajax({
			type:'POST',
			url:'${ctx}/system/develop/formmodel/${formid}',
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
	function createMainTableTree(){
		$('#mainTableModelId').combotree({  
		    url: '${ctx}/system/develop/table/checkedTableTree',  
		    method: 'GET',
		    cascadeCheck:true,
		    checkbox:true,
		    multiple:false,
		    editable:false,
		    hasDownArrow:true,
		    separator:',' 
		});
	}
	function createSubTableTree(){
		$('#subTableModelIds').combotree({  
		    url: '${ctx}/system/develop/table/checkedTableTree',
		    method: 'GET',
		    cascadeCheck:true,
		    checkbox:true,
		    multiple:true,
		    editable:false,
		    separator:',',
		    onLoadSuccess:function(){
		    	//初始化combotree时，树还没加载节点，不能判断父子节点，所以必须放在树加载完后触发,如果某一节点有子节点，则不设置该节点的选中状态，因为如果子节点选中了，它也会被选中，如果所有子节点都没有被选中，它也不会被选中
		    	var initResourcesIds = document.getElementById('subTableModelIds').value;
		    	if(initResourcesIds!=null&&initResourcesIds!=''){
		    		var resourcesIdsArr = initResourcesIds.split(',');
		    		if(resourcesIdsArr!=null&&resourcesIdsArr.length>0){
			    		$('#subTableModelIds').combotree('setValues',resourcesIdsArr);
		    		}
		    	}
		    }
		});
	}
</script>
</html>