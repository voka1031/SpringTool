<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<body>
	<h2>Error Page</h2>

	<c:if test="${not empty errMsg}">
		<h2>${errMsg}</h2>
	</c:if>
	<a href='<%=request.getContextPath()%>/practice/'>回首頁</a><br><br>
</body>
</html>