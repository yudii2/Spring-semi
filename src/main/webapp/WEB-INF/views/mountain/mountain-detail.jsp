<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file = "/WEB-INF/views/include/head.jsp"%>
<link rel="stylesheet" href = "/resources/css/mountain/mountain-detail.css">
<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyB0BynjFSEnK1evu8mQktPf2KwJjkHcvH0&callback=initMap&region=kr"></script>
</head>
<body>
<%@ include file = "/WEB-INF/views/include/fixed-header.jsp"%>

  <section> 
    <div class="container con_mountain">							
	    <form id="search_mountain" name="search" method = "get"  action="/mountain/detail" onsubmit="return keyword_check()">
			<h1>지역 또는 산 이름 검색</h1>           
			<i class="fas fa-search" style="font-size: 30px; color: white;"></i>
			<input type="text" class="search_bar" name=mName />  
	 		
	    	<div id="loc_bnt_wrap">
	  			<a type="button" class="loc_bnt" href="/mountain/search-gyeonggi" data-div-id='#Gyeonggi'>경기도</a>  
	  			<a type="button" class="loc_bnt" href="/mountain/search-seoul" data-div-id='#Seoul'>서울</a>  
			</div>   
	    </form><br><br><br>
    	   
	    <div id="mountain_detail">
		    <div id="mountain_map"></div>
			<div id="mountain_info">
				<div class="tit_mountain">
					<span class="mountain_name"><i class="fas fa-mountain"></i>${mountainInfo.mName}</span>
					<!-- 요청을 보내 모달창에 데이터 호출 필요...세션에 mountain객체를 저장? -->
					<!-- <form action="mountain/detail/course" id="mountain_trail">
						<select name="mountain_trail" class="mountain_trail">
							<option value="" selected disabled>등산로를 선택하세요</option>
							<option value="1">등산로1</option>
							<option value="2">등산로2</option>
						</select>
					</form>-->
				</div>
				<h2 class="tit_desc">개요
				<c:if test="${fn:length(mountainInfo.mInfo) > 550}">
					<span class="more">more</span>
				</c:if>
				</h2>
				<p class="word">
					${mountainInfo.mInfo}
				</p>
				<div class="desc_mountain">
					<h2><em class="tit_desc">높이 : </em><span>${mountainInfo.mHeight} m</span></h2>
					<h2><em class="tit_desc">위치 : </em><span>${mountainInfo.mLoc}</span></h2>
				</div>
			</div>          
	    </div>
 	</div>      
  </section>
  
  <%-- 
    <!-- Layer Popup -->
    <div class="modal">
      <div class="modal_inner">
        <div class="click_img">맵(등산로)이 들어갈 자리입니다.</div>
        <div class="desc">
          <div class="desc_header">
            <div class="tit_course"><span>북한산 코스1</span></div>
            <button class="close_modal">&times;</button>
          </div>
          <div class="desc_content">
            <div class="wrap_text">
              <h2>등산로 거리</h2><span>${mountain.course[idx].distance}947km</span>
              <h2>난이도</h2><span>상</span>
              <h2>상행시간</h2><span>2시간 22분 소요</span>
              <h2>하행시간</h2><span>1시간 43분 소요</span>
              <h2>총소요시간</h2><span>4시간 5분 예상</span>
              <h2>칼로리 </h2><span> kcal 소모</span>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="overlay"></div> --%>
    
    <script type="text/javascript">
    
	//등산로
 /*   	document.querySelector('.close_modal').addEventListener('click',function() {
   	  document.querySelector('.modal').style.display='none';
   	  document.querySelector('.overlay').style.display='none';
   	});
    
    document.querySelector('.mountain_trail').addEventListener('change',function() {
	    document.querySelector('.modal').style.display='flex';
	    document.querySelector('.overlay').style.display='flex';
    }); */
    	

    

    function initMap() {
        let X = ${mountainInfo.xCoor};
        let Y = ${mountainInfo.yCoor};
        let name = "${mountainInfo.mName}";
        
    	let seoul = { lat: X ,lng: Y };
    	let map = new google.maps.Map( document.getElementById('mountain_map'), {
		    zoom: 12,
		    center: seoul
		  });
		
		new google.maps.Marker({
		  position: seoul,
		  map: map,
		  label: name
		});
    }
    
    //서치바
    function keyword_check(){
    	if(document.search.mName.value === ''){
    		alert('산이름을 입력하세요'); 
    	  	document.search.mName.focus();
    	  	return false; 
    	}else return true;
    };
    
    document.querySelector('.more').addEventListener('click', () => {
    	document.querySelector('.word').className = 'whole';
    })

    
        
  </script>	 

</body>
</html>