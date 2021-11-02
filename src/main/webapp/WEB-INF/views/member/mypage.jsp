<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<link rel="stylesheet" href="/resources/css/member/mypage.css">
<!-- jQuery CDN -->
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script defer type="text/javascript" src="/resources/js/member/profileUpdate.js"></script>
<script defer type="text/javascript" src="/resources/js/member/mypage.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/views/include/fixed-header.jsp" %>
  <section>
    <div class="container con_mypage">
      <div class="wrap_mypage_side_menu">
        <div class="tit_side_menu">마이페이지</div>
        <ul class="mypage_side_menu">
          <li>
          	<a href="/member/modify" class="tit_mypage_gnb">내정보</a>
            <a href="/member/modify" class="tit_sub_gnb">내정보 수정하기</a>
          </li>
          <li>
          	<a href="/member/mypage" class="tit_mypage_gnb">작성글 관리</a>
          	<a href="/member/mypage" class="tit_sub_gnb">내가 쓴 글 보기</a>
          	<a href="/member/my-reply" class="tit_sub_gnb">내가 쓴 댓글 보기</a>
          </li>
          <li><a href="/member/my-schedule" class="tit_mypage_gnb">신청내역 관리</a></li>
        </ul>
      </div>
      <div class="wrap_my_contents">
        <div class="profile">
          <!-- 비동기통신으로 받아오기 필요 -->
          <div class="profile_img">
          <!-- 사용자 아이디와 일치하는 typeIdx가 존재하면 file_info에서 꺼내 출력 -->
          <!-- 존재하지 않으면 기본 프로필이미지 출력 -->
          <c:if test="${not empty authentication and not empty authentication.profile}">
          	<img id="target_img" src="http://localhost:7070/file/${authentication.profile}">
          </c:if>
          <c:if test="${not empty authentication and empty authentication.profile}">
            <img id="target_img" src="/resources/img/user.png">
          </c:if>
          </div>
          <form action="/member/profile-upload" name="profile" method="POST" enctype="multipart/form-data" >
            <input type="file" id="file" name="file" style="display: none;" onchange="changeValue(this)">
            <input type="hidden" name="target_url">	<!-- 보이지않지만 서버로 submit발생 -->
          </form>

          <div class="profile_desc">
            <h1 class="nickname">${authentication.nickname}</h1>
            <h2 class="cnt" >내 게시글 수 <span id="postCnt">${fn:length(myPosts)}</span> 개</h2>
           <%--  <h2 class="cnt">내 댓글 수 <span>${fn:length(myReply)}</span> 개</h2> --%>
            <span class="info">${authentication.info }</span>
          </div>
        </div>
        <ul class="tabs">
          <li id="tab_post"><a href="/member/mypage">내가 쓴 글</a></li>
          <li id="tab_reply"><a href="/member/mypage/reply">내가 쓴 댓글</a></li>
        </ul>
        <div class="my_posts">
          <div class="col_my_posts">
            <span class="title">제목</span>
            <span class="date">작성일</span>
            <span class="views">조회</span>
          </div>
          
      	<form action="/member/delete-post" method="post" name="deletePost">        
          <table>
          <!-- board패키지 접근 필요 -->
          <c:if test="${empty postByPage}">
			<tr class="contents">
              <td></td>
              <td>작성하신 게시글이 존재하지 않습니다.</td>
              <td></td>
              <td></td>
            </tr>          	
          </c:if>
          <c:if test="${not empty postByPage}">
            <c:forEach items="${postByPage}" var="myPost">
	            <tr class="contents" id="myPost">
	              <td><input type="checkbox" class="checkbox" name="chk" value="${myPost.bdIdx}"><span class="idx">${myPost.bdIdx}</span></td>
	              <td><a href="/board/board-detail?p=${param.p }&f=${param.f}&q=${param.q }&bd_idx=${myPost.bdIdx }" id="tit_content">${myPost.title}</a></td>
	              <td>${myPost.regDate}</td>
	              <td>${myPost.viewCnt}</td>
	            </tr>
			</c:forEach>          
          </c:if>         
          </table>
          <div class="btns">
            <div class="select_all">
              <input type="checkbox" id="selectAll">
              <label for="select_all">전체선택</label>
            </div>
            <div class="btn">
              <!-- <button id="btn_edit">수정하기</button> -->
              <button id="btn_del">삭제하기</button>
            </div>
          </div>           
        </form>
        </div>
        
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
      </div>
    </div>
  </section>
</body>
</html>