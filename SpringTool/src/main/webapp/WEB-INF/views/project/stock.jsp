<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page import="java.util.*"%>
<%@ page import="com.project.model.*"%>

<!DOCTYPE HTML>
<html>
<head>
<link rel="stylesheet" href="<c:out value="${pageContext.request.contextPath}"/>/css/jquery-ui.css">
<meta charset="UTF-8">
</head>
<body>
	<form METHOD="get" ACTION="<%=request.getContextPath()%>/finance/getStock">
		<b>Stock-Trend Query</b>
		StockID:<input type="text" id="securityCode" name="securityCode">
		StartDay:<input type="text" id="startDate" name="startDate">
		EndDay:<input type="text" id="endDate" name="endDate">
		<input type="submit" value="Submit" class="btn btn-info btn-sm">
	</form>
	<div id="chartContainer" style="height: 370px; max-width: 920px; margin: 0px auto;"></div>

</body>
<script src="<c:out value="${pageContext.request.contextPath}"/>/js/jquery-1.12.4.min.js"></script>
<script src="<c:out value="${pageContext.request.contextPath}"/>/js/jquery-ui.js"></script>
<script src="<c:out value="${pageContext.request.contextPath}"/>/js/canvasjs.min.js"></script>
<script>
	$(function() {
		$("#startDate").datepicker({
			changeMonth : true,
			changeYear : true,
			dateFormat : 'yymmdd',
		});
		$("#endDate").datepicker({
			changeMonth : true,
			changeYear : true,
			dateFormat : 'yymmdd'
		});
		$('#startDate').datepicker('setDate', '-6M');
		$('#endDate').datepicker('setDate', '-0d');
	});
</script>
<script>
	window.onload = function() {

		var dps = [ [] ];

		var chart = new CanvasJS.Chart("chartContainer", {
			animationEnabled : true,
			theme : "light2", // "light1", "light2", "dark1", "dark2"
			exportEnabled : true,
			title : {
				text : "Stock Price"
			},
			subtitles : [ {
				text : "All Prices are in TWD"
			} ],
			axisX : {
				labelFormatter : function(e) {
					return CanvasJS.formatDate(e.value, "MMM DD");
				},
			},
			axisY : {
				includeZero : false,
				prefix : "$",
				title : "Price"
			},
			axisY2 : {
				prefix : "$",
				suffix : "",
				title : "",
				tickLength : 0
			},
			toolTip : {
				shared : true
			},
			legend : {
				reversed : true,
				cursor : "pointer",
				itemclick : toggleDataSeries
			},
			data : [ {
				type : "candlestick",
				showInLegend : true,
				name : "Stock Price",
				yValueFormatString : "NT$#,##0.00",
				xValueType : "dateTime",
				dataPoints : dps[0]
			} ]
		});

		var yValue;
		var xValue;

		<c:forEach items="${dataPointsList}" var="dataPoints" varStatus="loop">
		<c:forEach items="${dataPoints}" var="dataPoint">
		yValue = [ parseFloat("${dataPoint.y[0]}"),
				parseFloat("${dataPoint.y[1]}"),
				parseFloat("${dataPoint.y[2]}"),
				parseFloat("${dataPoint.y[3]}") ];
		xValue = parseFloat("${dataPoint.x}");
		dps[parseInt("${loop.index}")].push({
			x : xValue,
			y : yValue,
		});
		</c:forEach>
		</c:forEach>

		chart.render();

		function toggleDataSeries(e) {
			if (typeof (e.dataSeries.visible) === "undefined"
					|| e.dataSeries.visible) {
				e.dataSeries.visible = false;
			} else {
				e.dataSeries.visible = true;
			}
			e.chart.render();
		}

	}
</script>
</html>