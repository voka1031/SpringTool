<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<h2>Error Occured</h2>
<c:if test="${not empty errMsg}">
	<h2>${errMsg}</h2>
</c:if>
