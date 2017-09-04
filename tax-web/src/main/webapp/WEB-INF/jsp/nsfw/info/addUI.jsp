<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <%@include file="/common/header.jsp"%>
    <title>信息发布管理</title>

    <script>
    	
    </script>
</head>
<body class="rightBody">
<form id="form" name="form" action="${basePath }nsfw/info_add.action" method="post" enctype="multipart/form-data">
    <div class="p_d_1">
        <div class="p_d_1_1">
            <div class="content_info">
    <div class="c_crumbs"><div><b></b><strong>信息发布管理</strong>&nbsp;-&nbsp;新增信息</div></div>
    <div class="tableH2">新增信息</div>
    <table id="baseInfo" width="100%" align="center" class="list" border="0" cellpadding="0" cellspacing="0"  >
        <tr>
            <td class="tdBg" width="200px">信息分类：</td>
            <td><s:select name="info.type" list="#infoTypeMap"/></td>
            <td class="tdBg" width="200px">来源：</td>
            <td><s:textfield name="info.source"/></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">信息标题：</td>
            <td colspan="3"><s:textfield name="info.title" cssStyle="width:90%"/></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">信息内容：</td>
            <td colspan="3"><s:textarea id="editor" name="info.content" cssStyle="width:90%;height:160px;" /></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">备注：</td>
            <td colspan="3"><s:textarea name="info.memo" cols="90" rows="3"/></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">创建人：</td>
            <td>
            	${login_user.name}
            	<input type="hidden" name="info.creator" value="${login_user.name}">
            </td>
            <td class="tdBg" width="200px">创建时间：</td>
            <td>
            	<fmt:formatDate value="${nowDate}" pattern="yyyy-MM-dd HH:ss"/>
            	<!-- 这个隐藏域的时间也要格式化，不然后台接收参数报错 -->
            	<input type="hidden" name="info.createTime" 
            	value="<fmt:formatDate value='${nowDate}' pattern='yyyy-MM-dd HH:ss'/>">
            </td>
        </tr>
    </table>
    
    <div class="tc mt20">
        <input type="submit" class="btnB2" value="保存" />
        &nbsp;&nbsp;&nbsp;&nbsp;
        <input type="button"  onclick="javascript:history.go(-1)" class="btnB2" value="返回" />
    </div>
    </div></div></div>
</form>
</body>
</html>