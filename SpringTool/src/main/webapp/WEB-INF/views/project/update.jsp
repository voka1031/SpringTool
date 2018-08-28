<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<title>�ק�</title>
</head>
<link rel="stylesheet" href="<c:out value="${pageContext.request.contextPath}"/>/css/bootstrap-3.3.7.css">
<body>
	<div class="col-xs-12 col-sm-10 col-sm-offset-1">
    	<h4>�ק�:</h4>
		<form:form action="update" method="POST" modelAttribute="customer">
	<%-- 	<form:errors path="*" cssClass="errorblock" element="div" /> --%>
			<table>
	            <tr>
			        <td>�s��:<font color=red><b>*</b></font></td>
			        <td><form:input type="text" path="id" id="id" size="25" value="${customer.id}" cssStyle="border:0px ; font-weight: bold;"/></td>
		        </tr>
				<tr>
					<td>�ʧO:<font color=red><b>*</b></font></td>
					 	<td><form:radiobutton path="sex" id="male" label="�k" value="M"/>
	            	    <form:radiobutton path="sex" id="female" label="�k" value="F"/></td>
					<td><form:errors path="sex" cssClass="error"/></td>
				</tr>
				<tr>
					<td>�m�W:<font color=red><b>*</b></font></td>
					<td><form:input type="text" path="name" id="name" size="25" value="${customer.name}"/></td>
					<td><form:errors path="name" cssClass="error" /></td>
				</tr>
				<tr>
					<td>�q��:<font color=red><b>*</b></font></td>
					<td><form:input type="text" path="tel" id="tel" size="25" value="${customer.tel}"/> 
					<td><form:errors path="tel" cssClass="error"/></td>
				</tr>
	
			</table><br>
			<input type="submit" value="�e�X�ק�" onClick="return check()">
		</form:form>
		<ul>
		    <li><a href='<%=request.getContextPath()%>/'>�^��@�d��</a><br><br></li>
			<li><a href='<%=request.getContextPath()%>/customer/listAllPaging/'>�^�C��</a></li>
		</ul>
	</div>
<script src="<c:out value="${pageContext.request.contextPath}"/>/js/jquery-1.12.4.min.js"></script>
<script src="<c:out value="${pageContext.request.contextPath}"/>/js/bootstrap-3.3.7.js"></script>
<script>
	function check(){
		
		var id = document.getElementById('id').value;
		var male = document.getElementById('male');
		var female = document.getElementById('female');
		var name = document.getElementById('name').value;
		var tel = document.getElementById('tel').value;
		var number  = /^[0-9]*[1-9][0-9]*$/;
		var word  = /^[\u4e00-\u9fa5A-Za-z_]{2,18}$/;
		var message = "";
		
		if(id.length==0||name.length==0||tel.length==0)
			message +="��줣�i�ť�, ";
		
		if(male.checked==false&&female.checked==false)
			message +="�ʧO����, ";

		if(number.test(id)==false)
			message +="  id�ж�J�����, ";
		
		if(word.test(name)==false)
			message +="  �m�W�п�J����B�^��B���u�Ÿ�, ";
		
		if(number.test(tel)==false)
			message +="  tel�ж�J�����, ";
		
		if(message.length!=0){
			message +="..�нT�{";
			alert(message);
			return false;
		}
	}

</script>	
</body>
</html>
