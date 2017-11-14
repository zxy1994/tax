<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <%@include file="/common/header.jsp"%>
    <title>信息发布管理</title>
    <script type="text/javascript">
	  	//全选、全反选
		function doSelectAll(){
			// jquery 1.6 前
			//$("input[name=selectedRow]").attr("checked", $("#selAll").is(":checked"));
			//prop jquery 1.6+建议使用
			$("input[name=selectedRow]").prop("checked", $("#selAll").is(":checked"));		
		}
	  	
		//新增
	   	function doAdd(){
	   		document.forms[0].action = "${basePath}nsfw/info_addUI.action";
	   		document.forms[0].submit();
	   	}
		
	    //编辑
	   	function doEdit(id){
	   		document.forms[0].action = "${basePath}nsfw/info_editUI.action?info.infoId="+id;
	   		document.forms[0].submit();
	   	}
	    
	    //删除
	  	function doDelete(id){
	  		document.forms[0].action = "${basePath}nsfw/info_delete.action?info.infoId=" + id;
	  		document.forms[0].submit();
	  	}
	    
	    //批量删除
	    function doDeleteAll(){
	    	if(window.confirm("确定要删除吗？")){
	    		document.forms[0].action = "${basePath}nsfw/info_batchDelete.action";
		    	document.forms[0].submit();
	    	}
	    }
	    
	    function doChangeStatus(id ,state){
	    	var text1 = $("#span_" + id).text().trim();
	    	var text2 = $("#a_" + id).text().trim();
	    	var newState = state=='1' ? '0':'1';
	    	$.ajax({
	    		url:"${basePath}nsfw/info_changeStatus.action",
	    		type: "POST",
	    		data: {"info.state":newState,"info.infoId":id},
	    		success:function(msg){
	    			if(msg == "ok"){
	    				// 成功，交换状态；同时把href上方法的形参给换了。
	    				$("#span_" + id).text(text2);
	    				$("#a_" + id).text(text1);
	    				$("#a_" + id).attr("href","javascript:doChangeStatus('"+id+"','"+newState+"')");
	    			}else{
	    				alert("操作失败，请稍后再试！");
	    			}
	    		},
	    		error:function(){
	    			alert("操作失败，请稍后再试！");
	    		}
	    	})
	    	
	    }
	    
	    // 条件查询
	    function doSearch() {
	    	document.forms[0].action = "${basePath}nsfw/info_listUI.action?fromType=s";
	    	document.forms[0].submit();
	    }
	    
    </script>
</head>
<body class="rightBody">
<form name="form1" action="" method="post">
    <div class="p_d_1">
        <div class="p_d_1_1">
            <div class="content_info">
                <div class="c_crumbs"><div><b></b><strong>信息发布管理</strong></div> </div>
                <div class="search_art">
                    <li>
                        信息标题：<s:textfield name="info.title" cssClass="s_text" id="infoTitle"  cssStyle="width:160px;"/>
                    </li>
                    <li><input type="button" class="s_button" value="搜 索" onclick="doSearch()"/></li>
                    <li style="float:right;">
                        <input type="button" value="新增" class="s_button" onclick="doAdd()"/>&nbsp;
                        <input type="button" value="删除" class="s_button" onclick="doDeleteAll()"/>&nbsp;
                    </li>
                </div>

                <div class="t_list" style="margin:0px; border:0px none;">
                    <table width="100%" border="0">
                        <tr class="t_tit">
                            <td width="30" align="center"><input type="checkbox" id="selAll" onclick="doSelectAll()" /></td>
                            <td align="center">信息标题</td>
                            <td width="120" align="center">信息分类</td>
                            <td width="120" align="center">创建人</td>
                            <td width="140" align="center">创建时间</td>
                            <td width="80" align="center">状态</td>
                            <td width="120" align="center">操作</td>
                        </tr>
                        <s:iterator value="infoList" status="st">
                            <tr <s:if test="#st.odd"> bgcolor="f8f8f8" </s:if> >
                                <td align="center"><input type="checkbox" name="selectedRow" value="<s:property value='infoId'/>"/></td>
                                <td align="center"><s:property value="title"/></td>
                                <td align="center">
                                	<s:property value="#infoTypeMap[type]"/>	
                                </td>
                                <td align="center"><s:property value="creator"/></td>
                                <td align="center"><s:date name="createTime" format="yyyy-MM-dd HH:mm"/></td>
                                <td align="center">
                                	<span id="span_${infoId}">
                                		<c:choose>
                                			<c:when test="${state==1}">
                                				发布
                                			</c:when> 
                                			<c:otherwise>
                                				停用
                                			</c:otherwise>
                                		</c:choose> 
                                	</span>
                                </td>
                                <td align="center">
                                	<span >
                                		<a id="a_${infoId}" 
                                			href="javascript:doChangeStatus('${infoId}','${state}')">                            
                                		<c:choose>
                                			<c:when test="${state==0}">
                                				发布
                                			</c:when> 
                                			<c:otherwise>
                                				停用
                                			</c:otherwise>
                                		</c:choose> 
                                		</a>
                                	</span>
                                    <a href="javascript:doEdit('<s:property value='infoId'/>')">编辑</a>
                                    <a href="javascript:doDelete('<s:property value='infoId'/>')">删除</a>
                                </td>
                            </tr>
                        </s:iterator>
                    </table>
                </div>
            </div>
        <div class="c_pate" style="margin-top: 5px;">
		<table width="100%" class="pageDown" border="0" cellspacing="0"
			cellpadding="0">
			<tr>
				<td align="right">
                 	总共1条记录，当前第 1 页，共 1 页 &nbsp;&nbsp;
                            <a href="#">上一页</a>&nbsp;&nbsp;<a href="#">下一页</a>
					到&nbsp;<input type="text" style="width: 30px;" onkeypress="if(event.keyCode == 13){doGoPage(this.value);}" min="1"
					max="" value="1" /> &nbsp;&nbsp;
			    </td>
			</tr>
		</table>	
        </div>

        </div>
    </div>
</form>

</body>
</html>