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
				
				//  给添加按钮绑定点击事件
				$("#add").click(function(){
					// self.parent.frames['tree']相当于获取到了tree.jsp的window,因此可以调用window的js方法
					var node = self.parent.frames['tree'].getSelectedInfo();
					if(node == null){
						$.messager.alert('错误消息','请先在右侧选择父节点！','error');
					}else{
						var text = node.text;
						var pid = node.id;
						$.messager.confirm('确认消息', '确定要在<font color="red">【'+text+'】</font>下添加子节点吗？', function(r){
							if (r){
							    // 弹出添加页面
								/*创建div  */
								var divDialog = $("<div style='overflow:hidden;' />");
								/*div转化成提示窗口  */
								divDialog.dialog({    
								    title: '添加节点',    
								    width: 400,    
								    height: 200,    
								    closed: false,    
								    cache: false, 
								    content:'<iframe src="${basePath}nsfw/tree_showAddTree.action?id='+pid
								    		+'" frameborder="0" width="100%" height="100%" align="top"/>',
								    modal: true,
								    onClose:function(){
								    	// 关闭后，数据表格重载数据
								    	$('#dg').datagrid("reload");
								    	// 树节点重新数据重新加载
								    	self.parent.frames['tree'].targetTreeReload(node);
								    }
								});    
							}
						});
					}
				}); // end of click
				
				//  给编辑按钮绑定点击事件
				$("#edit").click(function(){
					var rows = $("#dg").datagrid("getChecked");
					if(rows == ""){
						$.messager.alert('错误消息','请选择一行需要编辑的数据！','error');
					}else if(rows.length > 1){
						$.messager.alert('错误消息','编辑时只能选择一行！','error');
					}else{
						var row = rows[0];
						// 弹出编辑页面
						/*创建div  */
						var divDialog = $("<div style='overflow:hidden;' />");
						/*div转化成提示窗口  */
						divDialog.dialog({    
						    title: '编辑节点',    
						    width: 400,    
						    height: 170,    
						    closed: false,    
						    cache: false, 
						    content:'<iframe src="${basePath}nsfw/tree_showUpdateTree.action?id='+row.id
						    		+'" frameborder="0" width="100%" height="100%" align="top"/>',
						    modal: true,
						    onClose:function(){
						    	// 关闭后，数据表格重载数据
						    	$('#dg').datagrid("reload");
						    	// 树节点重新数据重新加载
						    	self.parent.frames['tree'].targetTreeReload(row.pId);
						    }
						});    
					}
				});//end of cilck
				
				//  给编辑按钮绑定点击事件
				$("#delete").click(function(){
					var rows = $("#dg").datagrid("getChecked");
					if(rows == ""){
						$.messager.alert('错误消息','请选择至少选择一行需要删除的数据！','error');
					}else{
						
						// 弹出确认按钮
						$.messager.confirm('确认消息', '确定要删除所选子节点吗？', function(r){
							if(r){
								var arr = new Array();
								$.each(rows,function(index,value){
									arr.push(value.id);
								})
								$.ajax({
									type:"POST",
									url:"${basePath}nsfw/tree_deleteTree.action",
									data:{ids:arr.join()},
									success:function(tip,statusText){
										if(statusText == "success"){
											var msg = (tip == 1)?"删除成功":"删除失败";
											alert(msg);
											if(tip == 1){
												// 成功后，数据表格重载数据
										    	$('#dg').datagrid("reload");
										    	// 树节点重新数据重新加载
										    	self.parent.frames['tree'].targetTreeReload(rows[0].pId);
											}
										}
									},
									error:function(){
										alert('网络异常,操作失败');
									},
									dataType:"json"
								});
							}
						});
					}
				});//end of cilck
				
			});// end of window onload
			
			// 触发数据表格重新加载数据的方法;必须写成window的方法，就可以跨frameset调用
			function targetDataGrid(id){
				$('#dg').datagrid("reload",{id:id});
			}
			
		</script>
	</head>
	<body>
		
		<table id="dg"></table>  
		<!-- 工具栏  -->
		<div id="tb">
			<a href="#" id="add" class="easyui-linkbutton" data-options="iconCls:'icon-add'">添 加</a>
			<a href="#" id="edit" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">编 辑</a>
			<a href="#" id="delete" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">删 除</a>
		</div>
		
	</body>
</html>