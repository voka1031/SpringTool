<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<thead>
	<tr>
		<th>編號</th>
		<th>性別</th>
		<th>姓名</th>
		<th>電話</th>
		<th>修改</th>
		<th>刪除</th>
	</tr>
</thead>
<tbody>
	<c:forEach var="pVO" items="${list}">
		<tr>
			<td>${pVO.id}</td>
			<td>${genderMap[pVO.sex]}</td>
			<td>${pVO.name}</td>
			<td>${pVO.tel}</td>
			<td>
				<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/customer/updatePage">
					<button type="submit" class="btn btn-info">修改</button>
					<input type="hidden" name="id" value="${pVO.id}">
				</FORM>
			</td>
			<td>
				<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/customer/delete">
					<button type="submit" class="btn btn-danger">刪除</button>
					<input type="hidden" name="id" value="${pVO.id}">
				</FORM>
			</td>
		</tr>
	</c:forEach>
</tbody>