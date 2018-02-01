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
		<td>${practiceVO.id}</td>
		<td>${genderMap[practiceVO.sex]}</td>
		<td>${practiceVO.name}</td>
		<td>${practiceVO.tel}</td>
		<td>
			<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/practice/getOne_For_Update">
				<input type="submit" value="修改" class="btn btn-primary btn-xs">
				<input type="hidden" name="id" value="${practiceVO.id}">
			</FORM>
		</td>
		<td>
			<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/practice/delete">
				<input type="submit" value="刪除" class="btn btn-danger btn-xs">
				<input type="hidden" name="id" value="${practiceVO.id}">
			</FORM>
		</td>
	</tr>
</table>
			