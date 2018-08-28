<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.project.model.*"%>
<table class="table">
	<tr>
		<th>編號</th>
		<th>性別</th>
		<th>姓名</th>
		<th>電話</th>
		<th>修改</th>
		<th>刪除</th>
	</tr>
	<tr>
		<td>${customer.id}</td>
		<td>${genderMap[customer.sex]}</td>
		<td>${customer.name}</td>
		<td>${customer.tel}</td>
		<td>
			<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/customer/getOneForUpdate">
				<input type="submit" value="修改" class="btn btn-primary btn-xs">
				<input type="hidden" name="id" value="${customer.id}">
			</FORM>
		</td>
		<td>
			<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/customer/delete">
				<input type="submit" value="刪除" class="btn btn-danger btn-xs">
				<input type="hidden" name="id" value="${customer.id}">
			</FORM>
		</td>
	</tr>
</table>
			