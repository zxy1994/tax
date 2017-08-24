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
				$('#tt').tree({    
				    url:'${basePath}nsfw/tree_findByPId.action',
				    lines:true
				});  
				// 点击事件
				$('#tt').tree({
					onClick: function(node){
						//alert(node.text);  // 在用户点击的时候提示
						//self.location.reload;
						var treeTable = self.parent.frames['treeTable'].document.getElementById("dg");
						$(treeTable).datagrid({
							  url:'${basePath}nsfw/tree_findTreeTableData.action?id=' + node.id,  
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
					}
				});
			});
			 
		</script>
	</head>
	<body>
		<ul id="tt"></ul> 
	</body>
</html>