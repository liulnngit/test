<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
	<title>课程信息显示</title>

	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<!-- 引入bootstrap -->
	<%-- <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
	<!-- 引入JQuery  bootstrap.js-->
	<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script> --%>

	<%--<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">--%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/formatDateUtil.js"></script>
	
</head>
<body>
	<div class="body-content">
		<table border="1px" width="100%">
				<td width="10%">
					表单号
				</td>
				<td width="30%">
					表单名称
				</td>
				<td width="10%">
					版本号
				</td>
				<td width="20%">
					生效开始时间
				</td>
				<td width="20%">
					生效结束时间
				</td>
				<td width="10%">
					操作
				</td>
			</tr>
			<c:forEach var="temple" items="${temples}">
				<tr>
					<td>
						${temple.tableCode} 
					</td>
					<td>
						${temple.tableName}
					</td>
					<td>
						${temple.version}
					</td>
					<td>
						<fmt:formatDate pattern="yyyy-MM-dd"
							value="${temple.startDate}" />
					</td>
					<td>
						<fmt:formatDate pattern="yyyy-MM-dd"
							value="${temple.endDate}" />
					</td>
					<td>
						<%-- <fmt:formatDate pattern="yyyy-MM-dd HH:mm"
							value="${temple.lastPost}" /> --%>
						<a href="${pageContext.request.contextPath }/downloadExcel?tableId=${temple.id}">下载</a>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>


</html>