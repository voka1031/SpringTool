<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="col-xs-12 col-sm-10 col-sm-offset-1">
	<div class="row">
		<div class="col-xs-6 col-sm-6">
			<form METHOD="post" ACTION="<%=request.getContextPath()%>/customer/findOne" id="queryForm">
				<div class="form-group">
					<div class="alert alert-info">
						<label for="id">單一查詢</label>
					</div>
					<div class="col-xs-10 col-sm-10">
						<input type="text" id="id" name="id" class="form-control">
					</div>
					<div class="col-xs-2 col-sm-2">
						<button type="submit" class="btn btn-primary">查詢</button>
					</div>
				</div>
			</form>
		</div>
		<div class="col-xs-6 col-sm-6">
			<div class="alert alert-info">
				<label for="id">下拉選單查詢</label>
			</div>
			<div class="fullScreenBlockDiv">
				<div class="col-xs-6 col-sm-6">
					<select id="genderSelect" class="form-control">
						<option value="0">----</option>
						<option value="1">全部</option>
						<c:forEach var="key" items="${genderMapKey}">
							<option value="${key}">${genderMap[key]}</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-xs-6 col-sm-6">
					<select id="nameSelect" class="form-control" disabled>
						<option value="0">----</option>
					</select>
				</div>
			</div>
		</div>
	</div>
	<%--Location for showing error messages --%>
	<c:if test="${not empty message}">
		<br />
		<div class="errorblock alert alert-danger">${message}</div>
	</c:if>
	<br />
	<div class="fullScreenBlockDiv">
		<div class="col-xs-12 col-sm-12" id="anchorDiv">
			<c:if test="${not empty customer}">
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
						<td>${customer.id}</td>
						<td>${genderMap[customer.sex]}</td>
						<td>${customer.name}</td>
						<td>${customer.tel}</td>
						<td>
							<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/customer/updatePage">
								<input type="submit" value="修改" class="btn btn-primary btn-xs">
								<input type="hidden" name="id" value="${customer.id}">
							</FORM>
						</td>
						<td>
							<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/customer/delete">
								<input type="submit" value="刪除" class="btn btn-danger btn-xs">
								<input type="hidden" name="id" value="${customer.id}">
							</FORM>
						</td>
					</tr>
				</table>
			</c:if>
		</div>
	</div>
</div>
<script src="<c:out value="${pageContext.request.contextPath}"/>/js/jquery-1.12.4.min.js"></script>
<script src="<c:out value="${pageContext.request.contextPath}"/>/js/bootstrap-3.3.7.js"></script>
<script>
	$(function() {
		var contextPath = '<c:out value="${pageContext.request.contextPath}"/>';
		var $genderSelect = $('#genderSelect');
		var $nameSelect = $('#nameSelect');
		var $anchorDiv = $('#anchorDiv');
		$genderSelect.change(function(event) {
			if ($genderSelect.val() != 0) {
				event.preventDefault();
				/*停用*/
				var url = contextPath + '/customer/getByGender';
				$.post(url, {
					gender : $genderSelect.val()
				}, function(data) {
					$nameSelect.empty();
					$nameSelect.append('<option value="0">~~~~</option>');
					$.each(data, function(index, genderObj) {
						var option = $('<option></option>').attr("value", genderObj.id).text(genderObj.name);
						$nameSelect.append(option);
					});
					$nameSelect.prop('disabled', false);
					${message} = '';
					console.log(${message});
					console.log(${not empty message});
				});
			}
		});
		$nameSelect.change(function() {
			if ($nameSelect.val() != 0) {
				$.ajax({
					url : contextPath + '/customer/findOneAjax',
					type : 'POST',
					data : {
						id : $nameSelect.val()
					},
					cache : false,
					success : function(result, status, xhr) {
						$anchorDiv.html(result);
					},
					error : function(xhr, status, error) {
						console.log("ajax err");
					}
				});
			}
		});
		/*表單外submit TEST*/
		$(document).on('click', '#outsideSubmit', function(e) {
			var form = $('#queryForm');
			form.submit();
		});
	});
</script>
