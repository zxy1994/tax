<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%
    		pageContext.setAttribute("basePath", request.getContextPath()+"/") ;
		%>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>easyuiTree</title>
		<link href="${basePath}css/skin1.css" rel="stylesheet" type="text/css" />
		<link rel="shortcut icon" href="${basePath}favicon.ico" type="image/x-icon"/>
		<!-- 导入EasyUi相关资源 -->
		<link rel="stylesheet" type="text/css" href="${basePath}js/jquery-easyui-1.5.2/themes/material/easyui.css">   
		<link rel="stylesheet" type="text/css" href="${basePath}js/jquery-easyui-1.5.2/themes/icon.css">   
		<script type="text/javascript" src="${basePath}js/jquery/jquery-1.10.2.min.js"></script>   
		<script type="text/javascript" src="${basePath}js/jquery-easyui-1.5.2/jquery.easyui.min.js"></script>  
		<script type="text/javascript" src="${basePath}js/jquery-easyui-1.5.2/locale/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript">
			$(function(){
				// 从远程加载树的数据
				$('#dg').datagrid({    
				    url:'${basePath}nsfw/tree_findTreeTableData.action',  
				    toolbar:'#tb',			//工具栏
				    rownumbers:true,		//显示行号
				    fitColumns:true,		//自动调整列宽
				    striped:true,			//斑马线效果
				    fit : true,				//填充父容器
				    columns:[[  
				    	{field:'selectRow',checkbox:true,formatter:function(value,row){return row.id}},
				        {field:'id',title:'ID',width:300},    
				        {field:'title',title:'节点名称',width:300},    
				        {field:'pId',title:'父节点ID',width:300}    
				    ]]    
				});   
			});
			 
		</script>
	</head>
	<body>
		<table id="dg"></table>  
		<!-- 工具栏  -->
		<div id="tb">
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">添 加</a>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">编 辑</a>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">删 除</a>
		</div>
		
	</body>
</html>