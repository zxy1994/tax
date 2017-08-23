<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<html>
<head>
    <%@include file="/common/header.jsp"%>
    <title>用户管理</title>
     <script type="text/javascript" src="${basePath}js/My97DatePicker/WdatePicker.js"></script>
     <script type="text/javascript">
     /** 验证账号是否存在 */
		function doVerifyAccount(){
			var account = $("#account").val().trim();
			if(account != ""){
				$.ajax({
					type: "POST",
					url: "${basePath}nsfw/user_verifyAccount.action",
					data: {"user.account":account,"user.id":"${user.id}"},
					success:function(msg,status){
						if(status == "success"){
							if("true" == msg){
								alert("该账号已使用，请重新填写账号！");
								$("#account").focus();
							}
						}
					},
					error:function(){
						alert("网络异常，请刷新页面！");
					}
				});
			}
		}
		
		/** 表单提交 */
		function doSubmit(){
			// 校验用户名
			var userName = $("[name=\"user.name\"]").val().trim();
			if(userName == ""){
				alert("用户名不能为空！");
				$("[name=\"user.name\"]").val("");
				$("[name=\"user.name\"]").focus();
				return false;
			}
			// 校验账号
			var account = $("#account").val().trim();
			if(account == ""){
				alert("账号不能为空！");
				$("#account").val("");
				$("#account").focus();
				return false;
			}else{
				$.ajax({
					type: "POST",
					url: "${basePath}nsfw/user_verifyAccount.action",
					data: {"user.account":account,"user.id":"${user.id}"},
					success:function(msg,status){
						if(status == "success"){
							if("true" == msg){
								alert("该账号已使用，请重新填写账号！");
								$("#account").focus();
							} else {
								// 账号合法后校验密码
				    			var password = $("[name=\"user.password\"]").val().trim();
				    			if(password == ""){
				    				alert("密码不能为空！");
				    				$("[name=\"user.password\"]").val("");
				    				$("[name=\"user.password\"]").focus();
				    				return false;
				    			}else if (!/^\w{6,20}$/.test(password)){
				    				alert("密码只能为6-20位的字母、数字、下划线组合！");
				    				$("[name=\"user.password\"]").focus();
				    				return false;
				    			}
				    			// 提交表单
				    			$("#form").submit();
							}
						}
					},
					error:function(){
						alert("网络异常，请刷新页面！");
					}
				});
			}
		}
     </script>
</head>
<body class="rightBody">
<form id="form" name="form" action="${basePath}nsfw/user_edit.action" method="post" enctype="multipart/form-data">
    <div class="p_d_1">
        <div class="p_d_1_1">
            <div class="content_info">
    <div class="c_crumbs"><div><b></b><strong>用户管理</strong>&nbsp;-&nbsp;编辑用户</div></div>
    <div class="tableH2">编辑用户</div>
    <input type="hidden" name="user.id" value="${user.id}">
    <table id="baseInfo" width="100%" align="center" class="list" border="0" cellpadding="0" cellspacing="0"  >
        <tr>
            <td class="tdBg" width="200px">所属部门：</td>
            <td><s:select name="user.dept" list="#{'部门A':'部门A','部门B':'部门B' }"/></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">头像：</td>
            <td>
            	<!-- 有头像就显示头像，并且把头像地址放隐藏域 -->
            	<c:if test="${not empty user.headImg}">
            		<!-- 显示头像  -->
             		<img src="${basePath}nsfw/user_showHeadImg.action?user.id=${user.id}"
             			 width="100" height="100"/>
             		<input type="hidden" name="user.headImg" value="${user.headImg}" />
            	</c:if>
                <input type="file" name="headImg" accept = "image/*"/>
            </td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">用户名：</td>
            <td><s:textfield name="user.name"/> </td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">帐号：</td>
            <td><s:textfield id="account" name="user.account" onchange="doVerifyAccount()"/></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">密码：</td>
            <td><s:textfield name="user.password"/></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">性别：</td>
            <td><s:radio list="#{'true':'男','false':'女'}" name="user.gender"/></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">角色：</td>
            <td>
            	<c:forEach items="${roleList}" var="role">
            		<label><input type="checkbox" name="roleIds" 
            			value="${role.roleId}"
            			<c:forEach items="${roleIds}" var="roleId">
            				<c:if test="${role.roleId==roleId}">
		            			checked="checked""
		            		</c:if>
            			</c:forEach>
            			/>
            		${role.name}
            		</label>
            	</c:forEach>
            </td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">电子邮箱：</td>
            <td><s:textfield name="user.email"/></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">手机号：</td>
            <td><s:textfield name="user.mobile"/></td>
        </tr>        
        <tr>
            <td class="tdBg" width="200px">生日：</td>
            <td>
				 <input type="text" id="birthday" name="user.birthday" 
				 	value="<fmt:formatDate value='${user.birthday}' pattern='yyyy-MM-dd'/>"
				  	class="Wdate" 
		            onfocus="WdatePicker({
		             	 lang:'zh-cn',
						 skin:'whyGreen',
						 dateFmt:'yyyy-MM-dd',
						 readOnly:true
						 })"
				 />
				 
			</td>
        </tr>
		<tr>
            <td class="tdBg" width="200px">状态：</td>
            <td><s:radio list="#{'1':'有效','0':'无效'}" name="user.state"/></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">备注：</td>
            <td><s:textarea name="user.memo" cols="75" rows="3"/></td>
        </tr>
    </table>
    <div class="tc mt20">
        <input type="button" onclick="doSubmit()" class="btnB2" value="保存" />
        &nbsp;&nbsp;&nbsp;&nbsp;
        <input type="button"  onclick="javascript:history.go(-1)" class="btnB2" value="返回" />
    </div>
    </div></div></div>
</form>
</body>
</html>