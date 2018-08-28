<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<body>
	<a href='<%=request.getContextPath()%>/'>
		<spring:eval expression="@jspProperties.getProperty('jsp.returnMainPage')" />
	</a>
	<h2>Error Occured</h2>
	<c:if test="${not empty errMsg}">
		<h2>${errMsg}</h2>
	</c:if>
	<br>
	<br>
</body>
</html>