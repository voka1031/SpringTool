<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.project.model.*"%>
<!DOCTYPE html>
<html ng-app="myApp">
<head>
	<title>Test Page</title>
</head>
<link rel="stylesheet" href="<c:out value="${pageContext.request.contextPath}"/>/css/bootstrap-3.3.7.css">
<link rel="stylesheet" href="<spring:theme code='stylesheet'/>" type="text/css" />
<body>
	<div class="col-xs-12 col-sm-10 col-sm-offset-1">
		<h3>功能測試</h3>
		<br />
		<a href='<%=request.getContextPath()%>/'>
			<spring:eval expression="@jspProperties.getProperty('jsp.returnMainPage')" />
		</a>
		<br />
		<div ng-controller="myCtrl"> 
		<h3>{{theTime}}</h3>
		</div>   
	<!-- theme test  -->	
		theme test: <a href="?theme=bright">bright</a> | <a href="?theme=dark">dark</a> | <a href="?theme=a94442">a94442</a>	
		<br/><br/><br/>
	<!-- angularJS test  -->
	
		<div ng-controller="nameCtrl">
			<p>AngularJS test, Input something in the input box:</p>
			<p>Name : <input type="text" ng-model="name" placeholder="Enter name here"></p>
			<h3>Hello {{name}}</h3>
		</div>
		
		<br/>
		<a href="#" id="responseBodyTest">responseBodyTestOnConsole</a>
		<a href='<%=request.getContextPath()%>/getResponseBody?inputVal=murmur..'>responseBodyShowInOtherPage</a>
	    <br/>
	    
	<!-- JSON output test  -->   
		<input type="button" value="jsonTest" id="jsonTest">
	</div>
	<script src="<c:out value="${pageContext.request.contextPath}"/>/js/jquery-1.12.4.min.js"></script>
	<script src="<c:out value="${pageContext.request.contextPath}"/>/js/bootstrap-3.3.7.js"></script>
	<script src="<c:out value="${pageContext.request.contextPath}"/>/js/angular-1.6.4.min.js"></script>
	<script>
	$(function() {
		var files= ${testJsonMap};
		var contextPath = '<c:out value="${pageContext.request.contextPath}"/>';
		
		$('#jsonTest').on('click', function(){
			alert(files.aa+", "+files.bb+", "+files.cc);
		});
		
		$('#responseBodyTest').click(function(event){
			event.preventDefault();
			var url = contextPath + '/getResponseBody?inputVal=murmur..';
			$.get(url, function(data){ //若改用post 請改成 url,{inputVal : murmur..} , func...
				$.each(data, function(index, obj) {
					console.log("obj.inputVal : " + obj.inputVal);
				})
			});
		});
		
		
		
	});
	
	var app = angular.module('myApp', []);
	app.controller('myCtrl', function($scope, $interval) {
	  $scope.theTime = new Date().toLocaleTimeString();
	  $interval(function () {
	      $scope.theTime = new Date().toLocaleTimeString();
	  }, 1000);
	});

	app.controller('nameCtrl', function($scope){
		$scope.name = "John";		
	});
</script>
</body>
</html>