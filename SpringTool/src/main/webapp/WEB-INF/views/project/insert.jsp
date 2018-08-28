<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
<head>
	<title>新增</title>
</head>
<body>
	<h3>新增</h3>
	<form:form action="insert" method="POST" modelAttribute="customer">
		<%-- <form:errors path="*" cssClass="errorblock" element="div" /> --%>
		<table>
			<tr>
				<td>sex:</td>
				<td>
				    <form:radiobutton path="sex" id="male" label="男" value="M"/>
            	    <form:radiobutton path="sex" id="female" label="女" value="F"/>
				</td>
				<td>
					<form:errors path="sex" cssClass="error" />
				</td>
			</tr>
			<tr>
				<td>name:</td>
				<td>
					<form:input type="text" path="name" id="name" size="20" /> 
				</td>
				<td><form:errors path="name" cssClass="error" /></td>
			</tr>
			<tr>
				<td>tel:</td>
				<td><form:input type="text" path="tel" id="tel" size="20" /></td>
				<td><form:errors path="tel" cssClass="error" /></td>
			</tr>
		</table>
		<br>
		<input type="submit" value="送出新增" onClick="return check()">
	</form:form>
	<ul>
		<li>
			<a href='<%=request.getContextPath()%>/'>
				<spring:eval expression="@jspProperties.getProperty('jsp.returnMainPage')" />
			</a>
		</li>
	</ul>


	<script>
	function check(){
		
		var male = document.getElementById('male');
		var female = document.getElementById('female');
		var name = document.getElementById('name').value;
		var tel = document.getElementById('tel').value;
		var number  = /^[0-9]*[1-9][0-9]*$/;
		var word  = /^[\u4e00-\u9fa5A-Za-z_]{2,18}$/;
		var message = "";
		
		if(name.length==0||tel.length==0)
			message +="欄位不可空白, ";
		
		if(male.checked==false&&female.checked==false)
			message +="性別未填, ";
		
		if(word.test(name)==false)
			message +="  姓名請輸入中文、英文、底線符號, ";
		
		if(number.test(tel)==false)
			message +="  tel請填入正整數, ";
		
		if(message.length!=0){
			message +="..請確認";
			alert(message);
			return false;
		}
	}

</script>	
</body>
</html>
