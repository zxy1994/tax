<%@ page language="java" contentType="text/html; charset=UTF-8" 
pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
      <div class="c_pate" style="margin-top: 5px;">
      <c:choose>
      	<c:when test="${pageResult.totalCount > 0 }">
      		<table width="100%" class="pageDown" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="right">
                 	总共${pageResult.totalCount}条记录，
                 	当前第 ${pageResult.pageNo} 页，
                 	共 ${pageResult.totalPage} 页 &nbsp;&nbsp;
                 	<c:if test="${pageResult.pageNo > 1}">
                 		<a href="javascript:doGoPage(${pageResult.pageNo - 1});">上一页</a>&nbsp;&nbsp;
                 	</c:if>
                 	<c:if test="${pageResult.pageNo < pageResult.totalPage}">
                 		<a href="javascript:doGoPage(${pageResult.pageNo + 1});">下一页</a>
                 	</c:if>
					到&nbsp;<input type="text" id="pageNo" name="pageNo" style="width: 30px;" onkeypress="if(event.keyCode == 13){doGoPage(this.value);}" min="1"
					max="${pageResult.totalPage}" value="${pageResult.pageNo}" /> &nbsp;&nbsp;
			    </td>
			</tr>
		</table>	
      	</c:when>
      	<c:otherwise>
      		暂无数据
      	</c:otherwise>
      </c:choose>
      	<script type="text/javascript">
			function doGoPage(pageNo) {
				document.getElementById("pageNo").value = pageNo;
				document.forms[0].action = listUrl;
				document.forms[0].submit();
			}
		</script>
      </div>