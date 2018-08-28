<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page import="java.util.*"%>
<%@ page import="com.project.model.*"%>
<tr>
	<th>編號</th>
	<th>性別</th>
	<th>姓名</th>
	<th>電話</th>
	<th>修改</th>
	<th>刪除</th>
</tr>
<c:forEach var="pVO" items="${list}">
	<tr>
		<td>${pVO.id}</td>
		<td>${genderMap[pVO.sex]}</td>
		<td>${pVO.name}</td>
		<td>${pVO.tel}</td>
		<td>
			<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/customer/getOneForUpdate">
				<input type="submit" value="修改">
				<input type="hidden" name="id" value="${pVO.id}">
			</FORM>
		</td>
		<td>
			<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/customer/delete">
				<input type="submit" value="刪除">
				<input type="hidden" name="id" value="${pVO.id}">
			</FORM>
		</td>
	</tr>
</c:forEach>