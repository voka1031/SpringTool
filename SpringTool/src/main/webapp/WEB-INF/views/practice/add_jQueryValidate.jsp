<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
<head>
<title>新增</title>
</head>
<body>
	<h3>新增</h3>
	<form action="insert" method="POST" id="idForm" modelAttribute="practiceVO">
			<p>
				<label for="male">
					<input type="radio" name="sex" id="male" value="M"/>男
				</label>
				<label for="female">
					<input type="radio" name="sex" id="female" value="F"/>女
				</label>
				<label for="sex" class="error"></label>
			</p>
			<p><label for ="name">Name</label><input type="text" name="name" id="name" size="20"></p>
			<p><label for ="tel">Tel</label><input type="text" name="tel" id="tel" size="20"></p>
			<p><input type="submit" value="送出新增"></p>
	</form>
	<ul>
		<li><a href='<%=request.getContextPath()%>/practice/'>回首頁</a><br>
		<br></li>
	</ul>


	<script src="<c:out value="${pageContext.request.contextPath}"/>/js/jquery-1.12.4.min.js"></script>
	<script src="<c:out value="${pageContext.request.contextPath}"/>/js/jquery.validate.min.js"></script>
	<script>
		$().ready(function() {
			$("#idForm").validate({
				rules : {
					sex : "required",
					name : {
						required : true,
						minlength : 2
					},
					tel : {
						required : true,
						minlength : 5
					}
				},
				messages : {
					sex : "Please select your gender",
					name : {
						required : "Please enter your name",
						minlength : "the min name length is 2"
					},
					tel : {
						required : "Please enter your tel",
						minlength : "the min tel length is 5"
					}
				}
			});
		});
	</script>
</body>
</html>
