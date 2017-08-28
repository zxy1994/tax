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
						// self.parent.frames['treeTable']相当于treeTable页面的window,因此可以调用js方法
						self.parent.frames['treeTable'].targetDataGrid(node.id);
					}
				});
				
				
				
			});//end of window onload
			
			// 用于获取选中节点信息的方法;该方法写成window的方法,可跨freamset调用
			function getSelectedInfo(){
				var node = $("#tt").tree("getSelected");
				return node;
			} 
			
			// 用于刷新节点，是window的方法，可跨freamset调用
			function targetTreeReload(node){
				// 如果node.target为undefined说明传的是id
				if(node.target == undefined){
					// 需要通过id找到节点（node此时是id）,然后重新赋值给node
					node = $('#tt').tree('find', node);
				}
				var parentNode = $("#tt").tree("getParent",node.target); 
				// node.target表示该节点的DOM对象
				if(parentNode == null){
					// 为null代表当前节点已经是根节点了
					$("#tt").tree("reload",node.target);
				}else{
					$("#tt").tree("reload",parentNode.target);
				}
			}
		</script>
	</head>
	<body>
		<!-- 树容器 -->
		<ul id="tt"></ul> 
	</body>
</html>