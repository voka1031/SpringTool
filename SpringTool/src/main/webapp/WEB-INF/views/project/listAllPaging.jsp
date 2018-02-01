<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page import="java.util.*"%>
<%@ page import="com.project.model.*"%>

<!DOCTYPE html>
<html>
<head>
<style>
.pagination {
	display: inline-block;
}

.pagination a {
	color: black;
	float: left;
	padding: 8px 16px;
	text-decoration: none;
}

.pagination a.active {
	background-color: #4CAF50;
	color: white;
	border-radius: 5px;
}

.pagination a:hover:not (.active ) {
	background-color: #ddd;
	border-radius: 5px;
}

#test {
	width: 100%;
	height: 100%;
}

#pagingTable{
    text-align: center;
    margin: 0px auto;
    border: 1px solid #000;
    width: 800px;
    border-radius: 2px;
}

table, th, tr, td{
    border: 1px solid #000;
    border-radius: 2px;
}

#inner {
	margin: 0 auto;
	margin-top: 10px;
	display: inline-block;
}

#wrapper{
	text-align: center;
	display: table; width:100%;
}

#paginationSelector{
	border: 1px solid #ccc;
	border-radius: 6px;
    box-sizing: border-box;
    position:absolute;
	height:30px; 
	margin-top: 13px;
}

</style>
<title>listAllPractice.jsp</title>
</head>
<link rel="stylesheet" href="<c:out value="${pageContext.request.contextPath}"/>/css/bootstrap-3.3.7.css">
<c:if test="${not empty interceptorSearchingTime}"><h3>${interceptorSearchingTime}</h3></c:if>
<body bgcolor='white'>
	<div class="col-xs-12 col-sm-10 col-sm-offset-1">
		<div id="test">
			<table id="pagingTable">
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
								<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/practice/getOne_For_Update">
									<input type="submit" value="修改"> <input type="hidden" name="id" value="${pVO.id}">
								</FORM>
							</td>
							<td>
								<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/practice/delete">
									<input type="submit" value="刪除"> <input type="hidden" name="id" value="${pVO.id}">
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
		<a href='<%=request.getContextPath()%>/practice/'>回首頁</a><br/>
		<a href='<%=request.getContextPath()%>/practice/addPractice'>加入新成員</a>
	</div>
	<script src="<c:out value="${pageContext.request.contextPath}"/>/js/jquery-1.12.4.min.js"></script>
	<script src="<c:out value="${pageContext.request.contextPath}"/>/js/bootstrap-3.3.7.js"></script>
	<script>
		$(function(){
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
			
			for(var i = 0 ; i< visiblePage ; i++)
				divPagingStr += '<a href="#" class="paging">'+(i+1)+'</a>';
				
			$divPaging.append(divPagingStr)
					  .prepend('<a href="#" class="paging">&lt;</a>')
				      .prepend('<a href="#" class="paging">&lt;&lt;</a>')
		    		  .append('<a href="#" class="paging">&gt;</a>')
		    		  .append('<a href="#" class="paging">&gt;&gt;</a>');
			
			$('a:nth-child(3)').addClass('active');
			$('a').css({'width':'15','text-align':'center'});
			
			//下拉式選單選頁
			var $pagingSelector = $('#paginationSelector');
			var selectorStr = "";
			for(var i = 0 ; i<maxPage;i++)
				selectorStr += '<option value="' + (i+1) + '">' + (i+1) + '</option>';
			
			$pagingSelector.append(selectorStr);
			
			//下拉式選單選頁事件
			$(document).on('change','#paginationSelector', function(e){
				ajaxFun(Number($(this).val()));
			});
			
			//分頁按鍵點擊事件
			$(document).on('click', '.paging' ,function(e){
				var $this = $(this);
				var nthPage = 1 ;
				var underClick = Number($this.text());	

				//1. 用.active在那一個 a 來取得現在是第幾頁
				var currentPos = Number($('a.active').text());
				
				//2. 判斷是前頁 、後頁、 數字, 再重新定義 nthPage
				if(Number.isInteger(underClick))
					nthPage =  Number($this.text());
				
				else if($this.text() == '<')
					nthPage = ((currentPos -1) < 1) ? 1 : currentPos -1;
				
				else if($this.text() == '>')
					nthPage = ((currentPos + 1) >maxPage) ? maxPage : currentPos + 1;
				
				else if($this.text() == '<<')
					nthPage = 1;
				
				else if($this.text() == '>>')
					nthPage = maxPage;		
				
				else{}
				
				ajaxFun(nthPage);
			});
			
			function ajaxFun(nthPage){
				$.ajax({
					url : contextPath + '/practice/pagingTable/'+ nthPage,
					type : 'GET',
					cache : false,
					success : function(result, status, xhr){
						//1. 取回資料後更新table
						$pagingTable.html(result);
						
						//2. 最後一組把tr補滿，讓格式不要跑掉
						var afterStr = "";
						if((nthPage==maxPage) && lackNumber!=0)
							for(var i = 0 ;i <lackNumber ; i++)
								afterStr += '<tr><td></td><td></td><td></td><td></td>'
								+'<td><input type="submit" value="修改" disabled/></td><td>'
								+'<input type="submit" value="刪除" disabled/></td></tr>';
						
						$('#pagingTable tr:last-child').after(afterStr);	
						
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
						
						var startVal = 0, 
						      endVal = maxPage;
							  
						if(maxPage < visiblePage){}//a.9頁以下的不限狀況全部顯示 
							
						else if(nthPage <= Math.floor(visiblePage/2))
							endVal = visiblePage;
						
						else if(maxPage - nthPage >= Math.ceil(visiblePage/2)){
							startVal = nthPage - Math.ceil(visiblePage/2); 
							endVal = nthPage + Math.floor(visiblePage/2);
						}else
							startVal = maxPage - visiblePage;
							  
						for(var i = startVal ; i < endVal ; i++)
							$divPaging.append('<a href="#"  class="paging">'+(i+1)+'</a>');

						$divPaging.prepend('<a href="#" class="paging">&lt;</a>')
								  .prepend('<a href="#" class="paging">&lt;&lt;</a>')
							      .append('<a href="#" class="paging">&gt;</a>')
							      .append('<a href="#" class="paging">&gt;&gt;</a>');
							
						//3.3  找到頁碼所對應的 a 再加上active class
						$('a:contains('+ nthPage +')').filter(function() {
							  return $(this).text() == nthPage;
							}).addClass('active');
						
						$('a').css({'width':'15','text-align':'center'});
						
						//4.下拉選單 更改顯示的頁數
						$("#paginationSelector option[value=" + nthPage + "]").prop('selected', true);
						
					},error : function(xhr, status, error){
						//alert("ajax err");
					}
				});	
			}; 
			//ajaxFun() end;
		});
	</script>
</body>
</html>
