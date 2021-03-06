<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <%@include file="/common/header.jsp"%>
    <title>信息发布管理</title>
	<script type="text/javascript" charset="utf-8" src="${basePath}js/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${basePath}js/ueditor/ueditor.all.min.js"> </script>
    <script type="text/javascript" charset="utf-8" src="${basePath}js/ueditor/lang/zh-cn/zh-cn.js"></script>
    <script type="text/javascript">
    		$(function(){
    			var width = $("#editorTd").width();//获取td的宽度，用于设置编辑器宽度
        		// 配置ueditor的根路径
        		window.UEDITOR_HOME_URL = "${basePath}js/ueditor/";
        		// 实例化编辑器,'editor'其实就是textarea的id
        		var ue = UE.getEditor("editor",{
        			emotionLocalization : true	// 开启使用本地表情包
        			,elementPathEnabled : false	// 不显示元素路径
        			,initialFrameWidth : width     //初始化编辑器宽度
        	        ,initialFrameHeight : 400  	//初始化编辑器高度
        	        , toolbars: [[
        	            'fullscreen', 'source', '|', 'undo', 'redo', '|',
        	            'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
        	            'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
        	            'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
        	            'directionalityltr', 'directionalityrtl', 'indent', '|',
        	            'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
        	            'link', 'unlink', 'anchor', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
        	            'simpleupload', 'insertimage', 'emotion', 'map', 'insertframe', 'insertcode','pagebreak', 'template', 'background', '|',
        	            'horizontal', 'date', 'time', 'spechars', 'snapscreen', 'wordimage', '|',
        	            'print', 'preview', 'searchreplace', 'drafts', 'help'
        	        ]]
        		});
    		})
    		

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
            <td colspan="3" id="editorTd"><s:textarea id="editor"  name="info.content" cssStyle="width:90%;height:160px;" /></td>
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
            	<s:date name="info.createTime" format="yyyy-MM-dd HH:mm"/>
             	<s:hidden name="info.createTime"/>
            </td>
        </tr>
    </table>
    <!-- 默认是发布状态 -->
    <input type="hidden" name="info.state" value="1">
    <div class="tc mt20">
        <input type="submit" class="btnB2" value="保存" />
        &nbsp;&nbsp;&nbsp;&nbsp;
        <input type="button"  onclick="javascript:history.go(-1)" class="btnB2" value="返回" />
    </div>
    </div></div></div>
</form>
</body>
</html>