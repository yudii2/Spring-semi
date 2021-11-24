<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<link rel="stylesheet" href="/resources/css/board/board.css">
</head>
<body>
<%@ include file="/WEB-INF/views/include/fixed-header.jsp" %>

  <section>
    <div class="container">
    
		<div class='section'>
			<div class='wrapper'>
				<div class="sub_title">
					<h2>자유게시판</h2>
					<div class='btn_area'>
						<a href="/board/board-page" class="btn">목록</a>
						<a href="/board/board-form" class="btn" id="btnWrite">글쓰기</a>
					</div>
				</div>
				
				<div class="row">
					<table class="table" style="border: 1px solid #dddddd">
						<thead>
							<tr style="height: 30px">
								<th style="width: 10%">글번호</th>
								<th style="width: 10%">말머리</th>
								<th style="width: 40%">제목</th>
								<th style="width: 20%">닉네임</th>
								<th style="width: 10%">작성일</th>
								<th style="width: 10%">조회수</th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${not empty boardList}">
								<c:forEach items="${boardList}" var="board">
								<tr style="height: 30px; line-height: 30px;">
									<td>${board.bdIdx}</td>
									<td><a href="/board/board-page?f=subject&q=${board.subject }">${board.subject}</a></td>
									<td><a href="/board/board-detail?bdIdx=${board.bdIdx }">${board.title}</a>
										<span>[${board.replyCnt }]</span>
									</td>
									<td><a href="/board/board-page?f=nickname&q=${board.nickname }">${board.nickname}</a></td>
									<td>${board.regDate}</td>
									<td>${board.viewCnt }</td>
								</tr>
								</c:forEach>
							</c:if>
							
							<c:if test="${empty boardList }">
							<tr style="height: 30px; line-height: 30px;">
								<td/>
								<td/>
								<td rowspan="3" style="text-align: center;">
									게시글이 없습니다.
								</td>
							</tr>
							</c:if>
							
						</tbody>
					</table>
				</div>
				
				<div class="footer">
					<div class="total_page_info">
						<span class="color bold">${(empty param.p)? 1 : param.p }</span>/${page.lastPage } pages
					</div>
					<form class="search_bar_wrap">
						<select class='search_subject' name='f'>
							<option ${(param.f == "title")? "selected" : "" } value='title'>제목</option>
							<option ${(param.f == "nickname")? "selected" : "" } value='nickname'>닉네임</option>
							<option ${(param.f == "subject")? "selected" : ""} value='subject'>말머리</option>
						</select>
						<input type="text" name="q" value="${param.q }" placeholder="검색어를 입력하세요."/>
						<button onclick="location.href='?p=${param.p }&f=${param.f}&q=${param.q }'">검색</button>
					</form>
	
					<%@ include file="/WEB-INF/views/include/pagination.jsp" %>										
				</div>
				
			</div>
		</div>
		
	</div>
  </section>


</body>
</html>