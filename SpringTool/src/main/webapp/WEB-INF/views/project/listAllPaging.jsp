<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:if test="${not empty interceptorSearchingTime}">
	<h3>${interceptorSearchingTime}</h3>
</c:if>
<div class="col-xs-12 col-sm-10 col-sm-offset-1">
	<div id="test">
		<table id="pagingTable" class="table table-hover">
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
		</table>
		<div id="wrapper">
			<div id="inner" class="pagination"></div>
			<select id="paginationSelector" class="pagingSelector"></select>
		</div>
	</div>
</div>
<script src="<c:out value="${pageContext.request.contextPath}"/>/js/jquery-1.12.4.min.js"></script>
<script src="<c:out value="${pageContext.request.contextPath}"/>/js/bootstrap-3.3.7.js"></script>
<script>
	$(function() {
		var contextPath = '<c:out value="${pageContext.request.contextPath}"/>';
		//後端計算後所得的最大頁數
		var maxPage = Number('${maxPage}');
		//分頁可見範圍
		var visiblePage = (maxPage > 9) ? 9 : maxPage;
		var lackNumber = Number('${lackNumber}');
		var $pagingTable = $('#pagingTable');
		//初始化 動態建立分頁button
		var $divPaging = $('div.pagination');
		var divPagingStr = "";
		for (var i = 0; i < visiblePage; i++)
			divPagingStr += '<a href="#" class="paging">' + (i + 1) + '</a>';
		$divPaging.append(divPagingStr).prepend('<a href="#" class="paging">&lt;</a>').prepend('<a href="#" class="paging">&lt;&lt;</a>').append(
				'<a href="#" class="paging">&gt;</a>').append('<a href="#" class="paging">&gt;&gt;</a>');
		$('a:nth-child(3)').addClass('active');
		$('a.paging').css({
			'width' : '15',
			'text-align' : 'center'
		});
		//下拉式選單選頁
		var $pagingSelector = $('#paginationSelector');
		var selectorStr = "";
		for (var i = 0; i < maxPage; i++)
			selectorStr += '<option value="' + (i + 1) + '">' + (i + 1) + '</option>';
		$pagingSelector.append(selectorStr);
		//下拉式選單選頁事件
		$(document).on('change', '#paginationSelector', function(e) {
			ajaxFun(Number($(this).val()));
		});
		//分頁按鍵點擊事件
		$(document).on('click', '.paging', function(e) {
			var $this = $(this);
			var nthPage = 1;
			var underClick = Number($this.text());
			//1. 用.active在那一個 a 來取得現在是第幾頁
			var currentPos = Number($('a.active').text());
			//2. 判斷是前頁 、後頁、 數字, 再重新定義 nthPage
			if (Number.isInteger(underClick))
				nthPage = Number($this.text());
			else if ($this.text() == '<')
				nthPage = ((currentPos - 1) < 1) ? 1 : currentPos - 1;
			else if ($this.text() == '>')
				nthPage = ((currentPos + 1) > maxPage) ? maxPage : currentPos + 1;
			else if ($this.text() == '<<')
				nthPage = 1;
			else if ($this.text() == '>>')
				nthPage = maxPage;
			else {
			}
			ajaxFun(nthPage);
		});
		function ajaxFun(nthPage) {
			$.ajax({
				url : contextPath + '/customer/pagingTable/' + nthPage,
				type : 'GET',
				cache : false,
				success : function(result, status, xhr) {
					console.log(result)
					//1. 取回資料後更新table
					$pagingTable.html(result);
					//2. 最後一組把tr補滿，讓格式不要跑掉
					var afterStr = "";
					var blankTag = '<tr><td></td><td></td><td></td><td></td>' + '<td><button type="submit" class="btn btn-info" disabled>修改</button></td>'
							+ '<td><button type="submit" class="btn btn-danger" disabled>刪除</button></tr>';
					if ((nthPage == maxPage) && lackNumber != 0)
						for (var i = 0; i < lackNumber; i++)
							afterStr += blankTag;
					$('#pagingTable tbody tr:last-child').after(afterStr);
					//3. 分頁鈕更新
					//3.1 清空 divPaging
					$divPaging.empty();
					//3.2 建立button 定義那些頁數的button要出現  visiblePage 以9為例
					//  頁鈕數值: 1 ~ maxPage
					//b.10頁以上的
					//b1. 頁鈕數值: 1~9
					//b2. maxPage - nthPage新頁數的狀況:
					//b2a. >=4 (ex: 6 7 8 9 10): (10 - 6) >=4
					//     頁鈕數值: nthPage-4 ~ nthPage+4
					//b2b. <4
					//     頁鈕數值: maxPage - 9 ~ maxPage 
					var startVal = 0, endVal = maxPage;
					if (maxPage < visiblePage) {
					}//a.9頁以下的不限狀況全部顯示 
					else if (nthPage <= Math.floor(visiblePage / 2))
						endVal = visiblePage;
					else if (maxPage - nthPage >= Math.ceil(visiblePage / 2)) {
						startVal = nthPage - Math.ceil(visiblePage / 2);
						endVal = nthPage + Math.floor(visiblePage / 2);
					} else
						startVal = maxPage - visiblePage;
					for (var i = startVal; i < endVal; i++)
						$divPaging.append('<a href="#"  class="paging">' + (i + 1) + '</a>');
					$divPaging.prepend('<a href="#" class="paging">&lt;</a>').prepend('<a href="#" class="paging">&lt;&lt;</a>').append('<a href="#" class="paging">&gt;</a>')
							.append('<a href="#" class="paging">&gt;&gt;</a>');
					//3.3  找到頁碼所對應的 a 再加上active class
					$('a:contains(' + nthPage + ')').filter(function() {
						return $(this).text() == nthPage;
					}).addClass('active');
					$('a.paging').css({
						'width' : '15',
						'text-align' : 'center'
					});
					//4.下拉選單 更改顯示的頁數
					$("#paginationSelector option[value=" + nthPage + "]").prop('selected', true);
				},
				error : function(xhr, status, error) {
					//alert("ajax err");
				}
			});
		}
		;
		//ajaxFun() end;
	});
</script>
