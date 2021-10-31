package com.kh.spring.admin.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kh.spring.admin.model.service.AdminMemberService;
import com.kh.spring.member.model.dto.Member;

@Controller
@RequestMapping("admin")
public class AdminMemberController {
	
	//Logger logger = LoggerFactory.getLogger(this.getClass());	//출력해주는 클래스
	
	//get방식으로 /admin/member-list로 요청이 올 경우 호출될 Controller 메서드 작성
	//DataBase에서 현재 모든 회원의 정보를 조회해서 Controller 메서드에서 출력
	//index.jsp로 요청재지정
	//AdminMemberControllerTest클래스를 생성해 200번 응답코드가 반환되면 테스트 통과하도록 테스트 메서드 작성
	@Autowired
	private AdminMemberService adminMemberService;

	@GetMapping("member-list")
	public void selectMemberList(Model model) {	//Model 객체가 알아서 jsp로 객체를 전달해준다.스프링의 이점을 활용해 모델객체를 주입받아 사용하자.
		List<Member> members = adminMemberService.selectMemberList();
		
		model.addAttribute("members",members);
		
	}
}
