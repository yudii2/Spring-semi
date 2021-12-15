package com.kh.spring.common.interceptor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import com.kh.spring.common.code.ErrorCode;
import com.kh.spring.common.code.member.MemberGrade;
import com.kh.spring.common.exception.HandleableException;
import com.kh.spring.member.model.dto.Member;


//controller넘어가기 전 사전 작업 (권한 관리 등) DispatcherServlet 의 권한을 HandlerInterceptor가 가로채 기능수행
public class AuthInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
		
		String[] uriArr = request.getRequestURI().split("/");
		
		HttpSession session = request.getSession();
		Member member = (Member) session.getAttribute("authentication");
		
		if(uriArr.length != 0) {
			switch (uriArr[1]) {
				case "member":
					memberAuthorize(request, response, uriArr);
					break;
//				case "admin":
//					if(member == null || !member.getGrade().equals("ADMIN")) {
//						throw new HandleableException(ErrorCode.UNAUTHORIZED_PAGE);
//					}break;
				case "board":
					boardAuthorize(request, response, uriArr);
					break;
				default:
					break;
			}
		}
		
		return true;
		
	}
		
	private void boardAuthorize(HttpServletRequest httpRequest, HttpServletResponse httpResponse, String[] uriArr) {
			
		Member member = (Member) httpRequest.getSession().getAttribute("authentication");
		
		//비회원일 때 게시글 접근을 금지
		switch (uriArr[2]) {
		case "board-form":
			if(member == null) {
				throw new HandleableException(ErrorCode.UNAUTHORIZED_PAGE);
			}
			break;
		case "upload":
			if(member == null) {
				throw new HandleableException(ErrorCode.UNAUTHORIZED_PAGE);
			}
			break;
		default:
			break;
		}
		
	}


	private void memberAuthorize(HttpServletRequest httpRequest, HttpServletResponse httpResponse, String[] uriArr) throws ServletException, IOException {

		switch (uriArr[2]) {
		case "mypage":
			if(httpRequest.getSession().getAttribute("authentication") == null) {
				throw new HandleableException(ErrorCode.UNAUTHORIZED_PAGE);
			}
		default:
			break;
		}
		
	}


	
}
