<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
<head>
<title>FileUpload</title>
<link rel="stylesheet" href="<c:out value="${pageContext.request.contextPath}"/>/css/bootstrap-3.3.7.css">
</head>
<body>
	<div>
		<form:form>
			<h2>檔案上傳</h2>
			<p>
				<input type="file" name="file" id="uploadfile"/>
			</p>
			<p>
				<textarea id="fileInfo" rows="5" cols="70"></textarea>
			</p>
			<p>
				<button type="button" class="btn btn-primary" id="updateBtn">上傳</button>
			</p>
		</form:form>
	</div>
	<a href='<%=request.getContextPath()%>/'>
		<spring:eval expression="@jspProperties.getProperty('jsp.returnMainPage')" />
	</a>
	<script src="<c:out value="${pageContext.request.contextPath}"/>/js/jquery-1.12.4.min.js"></script>
	<script src="<c:out value="${pageContext.request.contextPath}"/>/js/bootstrap-3.3.7.js"></script>
	<script>
		$(function() {
			var contextPath = '<c:out value="${pageContext.request.contextPath}"/>';
			
			$("#uploadfile").on("change", function(event){
				var message = '';
				message += 'File Name: '+this.files[0].name+'\n';
				message += 'File Type: '+this.files[0].type+'\n';
				message += 'File Size: '+this.files[0].size+' byte(s)\n';
				message += 'Last modified: '+this.files[0].lastModifiedDate+'\n';
				$('#fileInfo').html(message);
			});
	
			$("#updateBtn").on("click", function(event) {
				event.preventDefault();
				var form = $('form')[0];
				var formData = new FormData(form);
				$.ajax({
					url : contextPath + '/upload',
					type : 'POST',
					data : formData,
					contentType : false,
					processData : false,
					cache : false,
					success : function(result, status, xhr) {
						alert('上傳成功！');
					},error : function(xhr, status, error) {
						alert('上傳失敗！');
					}
				});
			});
		});
	</script>
</body>
</html>