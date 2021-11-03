<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<body>
  	<div class="arrows" >
        <c:if test="${page.currPage > 1}">		<!-- 5,9,, 이상 -->
	      <a href="?p=${page.currPage-1}"><i class="fas fa-chevron-left leftArrow"></i></a>
	    </c:if>
	    <c:if test="${page.currPage <= 1}">	<!-- 현재 1페이지 -->
	      <span onclick="alert('이전 페이지가 존재하지 않습니다.')"><i class="fas fa-chevron-left leftArrow" ></i></span>
       	</c:if>
       	
		<ul class="pageNum">	<!-- 페이지 넘버링 -->
			<c:forEach var="p" begin="${page.startNum }" end="${page.endNum }">
				<c:if test="${p == page.currPage }">
					<li><a class="num point">${p}</a></li>	
				</c:if>
				<c:if test="${p != page.currPage }">
					<li><a href="?p=${p}" class="num">${p}</a></li>	
				</c:if>					
			</c:forEach> 		
		</ul>

		<c:if test="${page.currPage < page.lastPage && page.lastPage > 1}">	<!-- 총 페이지 수가 5를 넘으면 i=[5-9] -->
			<a href="?p=${page.currPage + 1}"><i class="fas fa-chevron-right rightArrow" ></i></a>	<!-- rightArrow 클릭시 5번page 이동 -->
		</c:if>
			<c:if test="${page.currPage >= page.lastPage}">
			<span onclick="alert('더이상 게시글이 존재하지 않습니다.')"><i class="fas fa-chevron-right rightArrow" ></i></span>
		</c:if>	  
	</div> 
</body>
</html>