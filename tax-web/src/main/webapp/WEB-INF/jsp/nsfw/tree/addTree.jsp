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
				$("#submitId").click(function(){
					var title = $("#title").val().trim();
					if(title == ""){
						alert("节点名称不能为空！")
						$("#title").val("").focus();
					}else{
						//异步提交
						var formData = $("#addForm").serialize();
						$.ajax({
							type: "POST",
							url: "${basePath}nsfw/tree_addTree.action",
							data:formData,
							success: function(tip){
							     if(tip == 1){
							    	 $("#tip").text("添加成功");
							    	 $("#title").val("");
							    	 //3秒后清除提示
							    	 setTimeout(function(){
							    		 $("#tip").text("")
							    	 },3000);
							     }else{
							    	 $("#tip").text("添加失败");
							    	 //3秒后清除提示
							    	 setTimeout(function(){
							    		 $("#tip").text("")
							    	 },3000);
							     }
							   },
							error:function(){
								$("#tip").text("添加失败");
						    	 //3秒后清除提示
						    	 setTimeout(function(){
						    		 $("#tip").text("")
						    	 },3000);
							},
							dataType: "json"
						});// end
					}
				})// end of click
				
				$("#resetId").click(function(){
					 $("#tip").text("");
			    	 $("#title").val("");
			    	 $("#title").focus();
				});
				
			});//end of window onload
			 
		</script>
	</head>
	<body>
		<form id="addForm" action="#" method="post">
			<input type="hidden" name="orgTree.pId" value="${orgTree.id}">
			<table  cellpadding="10px" align="center"  style="font-size: 14px;border-collapse:separate; border-spacing:10px 10px;">
			<tr>
				<td>当前父节点：</td>
				<td>
					<span><font color="red">【${orgTree.title}】</font></span>
				</td>
			</tr>
			<tr>
				<td>节点名称：</td>
				<td><input id="title" type="text" name="orgTree.title"  style="width: 200px; height: 16px"  /></td>
			</tr>
			
			<tr>
				<td colspan="2" align="center">
					<a id="submitId" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">添加</a>&nbsp;
					&nbsp;&nbsp;
					<a id="resetId" class="easyui-linkbutton" data-options="iconCls:'icon-no'">重置</a>&nbsp;
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<br/>
					<font id="tip" color="red" size="3"></font>
				</td>
			</tr>
		</table>  
		</form>
	</body>
</html>