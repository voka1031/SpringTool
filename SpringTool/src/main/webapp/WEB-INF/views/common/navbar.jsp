<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.project.model.*"%>
<%@ page import="com.project.constant.Constants"%>

<ul class="nav nav-tabs">
	<li>
		<a href='<%=request.getContextPath()%>/'>
			<spring:eval expression="@jspProperties.getProperty('jsp.returnMainPage')" />
		</a>
	</li>
	<li>
		<a href='<%=request.getContextPath()%>/customer/listAll'>表列所有成員</a>
	</li>
	<li>
		<a href='<%=request.getContextPath()%>/customer/listAllDataTable/'>表列所有成員DataTable</a>
	</li>
	<li>
		<a href='<%=request.getContextPath()%>/customer/insertPage'>加入新成員</a>
	</li>
	<li>
		<a href='<%=request.getContextPath()%>/customer/insertPageJQueryValidate'>加入新成員JQueryValidate</a>
	</li>
	<li>
		<a href='<%=request.getContextPath()%>/test'>功能測試頁</a>
	</li>
	<li>
		<a href='<%=request.getContextPath()%>/uploadPage'>上傳測試頁</a>
	</li>
	<li>
		<a href='<%=request.getContextPath()%>/finance/stock'>STOCK</a>
	</li>
	<li>
		<a href='#' id="outsideSubmit">表單外確認鍵</a>
	</li>
</ul>
<br />