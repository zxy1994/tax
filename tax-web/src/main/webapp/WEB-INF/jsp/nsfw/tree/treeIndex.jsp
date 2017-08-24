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
		
	</head>
		<frameset cols="20%,80%">
		 	 <frame src="${basePath}nsfw/tree_showTree.action" name="tree">
		 	 <frame src="${basePath}nsfw/tree_showTreeTable.action" name="treeTable">
		</frameset>
</html>