<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<title>修改</title>
</head>
<link rel="stylesheet" href="<c:out value="${pageContext.request.contextPath}"/>/css/bootstrap-3.3.7.css">
<body>
	<div class="col-xs-12 col-sm-10 col-sm-offset-1">
    	<h4>修改:</h4>
		<form:form action="update" method="POST" modelAttribute="customer">
	<%-- 	<form:errors path="*" cssClass="errorblock" element="div" /> --%>
			<table>
	            <tr>
			        <td>編號:<font color=red><b>*</b></font></td>
			        <td><form:input type="text" path="id" id="id" size="25" value="${customer.id}" cssStyle="border:0px ; font-weight: bold;"/></td>
		        </tr>
				<tr>
					<td>性別:<font color=red><b>*</b></font></td>
					 	<td><form:radiobutton path="sex" id="male" label="男" value="M"/>
	            	    <form:radiobutton path="sex" id="female" label="女" value="F"/></td>
					<td><form:errors path="sex" cssClass="error"/></td>
				</tr>
				<tr>
					<td>姓名:<font color=red><b>*</b></font></td>
					<td><form:input type="text" path="name" id="name" size="25" value="${customer.name}"/></td>
					<td><form:errors path="name" cssClass="error" /></td>
				</tr>
				<tr>
					<td>電話:<font color=red><b>*</b></font></td>
					<td><form:input type="text" path="tel" id="tel" size="25" value="${customer.tel}"/> 
					<td><form:errors path="tel" cssClass="error"/></td>
				</tr>
	
			</table><br>
			<input type="submit" value="送出修改" onClick="return check()">
		</form:form>
		<ul>
		    <li><a href='<%=request.getContextPath()%>/'>回單一查詢</a><br><br></li>
			<li><a href='<%=request.getContextPath()%>/customer/listAllPaging/'>回列表</a></li>
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
			message +="欄位不可空白, ";
		
		if(male.checked==false&&female.checked==false)
			message +="性別未填, ";

		if(number.test(id)==false)
			message +="  id請填入正整數, ";
		
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
