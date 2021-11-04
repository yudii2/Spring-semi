package com.kh.spring.member.controller;

import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.common.code.ErrorCode;
import com.kh.spring.common.exception.HandleableException;
import com.kh.spring.common.util.PageDTO;
import com.kh.spring.common.validator.ValidatorResult;
import com.kh.spring.member.model.dto.Member;
import com.kh.spring.member.model.service.MemberService;
import com.kh.spring.member.validator.JoinForm;
import com.kh.spring.member.validator.JoinFormValidator;



@Controller
@RequestMapping("member")
public class MemberController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	private MemberService memberService;
	private JoinFormValidator joinFormValidator;
	private BoardService boardService;
	
	public MemberController(MemberService memberService, JoinFormValidator joinFormValidator, BoardService boardService) {
		super();
		this.memberService = memberService;
		this.joinFormValidator = joinFormValidator;
		this.boardService = boardService;
	}



	//model의 속성 중 속성명이 joinForm인 속성이 있는 경우에 WebDataBinder의 속성을 초기화
	@InitBinder(value = "joinForm")
	public void initBinder(WebDataBinder webDataBinder) {	//요청파라미터 값들을 바인드해줌
		webDataBinder.addValidators(joinFormValidator);
	}
	
	

	@GetMapping("join")
	public void joinForm(Model model) {	//validator할 때 미리 값을 넣어줘야 한다.(현재는 괜찮지만 타임리프로 변환시 문제발생) ***
		model.addAttribute(new JoinForm()).addAttribute("error", new ValidatorResult().getError());
	}
	
	@PostMapping("join")					
	public String join(@Validated JoinForm form
							,Errors errors	//Errors 객체는 반드시 검증할 객체 바로 뒤에 작성
							,Model model
							,HttpSession session
							,RedirectAttributes redirectAttr
							) {		
		ValidatorResult vr = new ValidatorResult();
		model.addAttribute("error",vr.getError());	
		
		if(errors.hasErrors()) {
			vr.addErrors(errors);	//ValidatorResult로부터 map형태의 에러객체를 전달받아 model 속성으로 저장
			return "member/join";
		}
		
		String token = UUID.randomUUID().toString();
		session.setAttribute("persistToken", token);
		session.setAttribute("persistUser", form);
		
		redirectAttr.addFlashAttribute("message","회원가입 완료를 위한 이메일이 발송되었습니다.");
		
		memberService.authenticateByEmail(form,token);

		return "redirect:/";	//forward처리를 하면 index로 url이 변경되지 않는 문제!
	}
	
	//이메일 발송(토큰전달) 메서드
	@GetMapping("join-impl/{token}")	//{파라미터명}
	public String joinImpl(@PathVariable String token
							,@SessionAttribute(value = "persistToken", required = false) String persistToken		//null이 담겨오면 에러 발생. 이를 방지하기 위해 required=false로 지정
							,@SessionAttribute(value = "persistUser", required = false) JoinForm persistUser
							,HttpSession session
							,RedirectAttributes redirectAttr) {
		if(!token.equals(persistToken)) {
			throw new HandleableException(ErrorCode.AUTHENTICATION_EXPIRED_ERROR);
		}
				
		memberService.insertMember(persistUser);
		
		session.removeAttribute("persistToken");
		session.removeAttribute("persistUser");
		redirectAttr.addFlashAttribute("message","환영합니다~~");		
		
		return "redirect:/member/login";	
	}
	
	@PostMapping("join-json")
	public String joinWithJson(@RequestBody Member member) {	//@RequestBody가 붙지 않으면 내부적으로 @Model 어노테이션으로 인식하여 application/x-www-form-urlEncoded으로 읽기때문에 객체를 바인딩하지 못한다.
		logger.debug("member : {}",member.toString());			//비동기통신을 통해 application/json 양식을 받아야되는 경우에는 @RequestBody을 사용해 스프링이 객체에 바인딩할 수 있도록 한다.
		
		return "index";
	}
	
	//로그인 페이지 이동 메스드
	@GetMapping("login")		//member/login으로 매핑되어있음. 결과적으로 보내줄 url과 동일하므로 메서드는 void로 실행해도 된다.
	public void login() {}
	
	//로그인 실행 메서드
	@PostMapping("login")
	public String loginImpl(Member member, HttpSession session, RedirectAttributes redirectAttr) {	//기존 err를 쿼리스트링으로 붙여 redirect해줬음. 스프링에서는 RedirectAttributes가 요청을 재요청하더라도 값을 저장하고 있다(Model을 상속받는 친구)
		Member certifiedUser = memberService.authenticateUser(member);
		
		if(certifiedUser == null) {
			redirectAttr.addFlashAttribute("message", "아이디나 비밀번호가 일치하지 않습니다");	//FlashAttribute는 일시적으로 띄우고 필요없는 전달사항에 대한 메서드
			return "redirect:/member/login";
		}
		
		session.setAttribute("authentication", certifiedUser);
		
		//sendredirect : 클라이언트에게 요청재요청(요청을 두번발생시키므로 크롬url이 변경됨) vs requestDispatcher.forward : url이 변경되지 않음(요청 재지정)
		return "redirect:/member/mypage";		//컨트롤러에 /member/mypage로 들어온 요청(재요청)에 대해 처리할 메서드가 없으면 오류남
	}
	

	@GetMapping("id-check")
	@ResponseBody				//return값을 responseBody에 담아 전달해 준다.
	public String idCheck(String userId) {
		
		Member member = memberService.selectMemberById(userId);
		if(member == null) {
			return "available";
		}else {
			return "disabled";
		}

	}
	
	@GetMapping("nickname-check")
	@ResponseBody
	public String nicknameCheck(String nickname) {
		Member member = memberService.selectMemberByNickname(nickname);
		
		if(member == null) {
			return "available";
		}else {
			return "disabled";
		}
	}
	
	@GetMapping("kakao-login")
	@ResponseBody
	public String kakaoLogin(String userId) {
		logger.debug(userId);
		Member member = memberService.selectMemberById(userId);
		
		if(member == null || member.getUserId().equals("")) {
			return "kakaoJoin";
		}else {
			return "kakaoLogin";
		}
	}
	
	@GetMapping("kakao-join")
	public void kakaoJoin() {}
	
	@PostMapping("kakao-join")
	public void kakaoJoinImpl(Member member, @RequestParam(name = "kakaoId") String kakaoId) {
		logger.debug(member.toString());
		logger.debug(kakaoId);
		
	}
	
	@PostMapping("modify")
	public void modifyImpl(Member member) {
		
	}
	
	@GetMapping("logout")
	public String logout(HttpSession session) {	
		session.removeAttribute("authentication");
		return "redirect:/";
	}
	
	@GetMapping("mypage")
	public void mypage(Model model
						,@SessionAttribute(value = "authentication") Member member
						,@RequestParam(value = "p", required = false) String currPage) {
		int cntPerPage = 5;
		int bdCnt = boardService.countMyPost(member);
		int rpCnt = boardService.countMyReply(member);
		
		if(currPage == null) {
			currPage = "1";
		}
		
		PageDTO pageDto = new PageDTO(bdCnt, Integer.parseInt(currPage), cntPerPage);
		logger.debug(pageDto.toString());
		
		member.setPostCnt(bdCnt);
		member.setReplyCnt(rpCnt);
		
		model.addAttribute("authentication",member)
			.addAttribute("postByPage", boardService.selectMyPost(member, pageDto))
			.addAttribute("page",pageDto);
		
	}
	
	@GetMapping("modify")
	public void modify() {}
	
	@GetMapping("reply")
	public void reply(Model model
					,@SessionAttribute(value = "authentication") Member member
					,@RequestParam(value = "p", required = false) String currPage) {
				
		int cntPerPage = 5;
		int bdCnt = boardService.countMyPost(member);
		int rpCnt = boardService.countMyReply(member);
		
		if(currPage == null) {
			currPage = "1";
		}
		
		PageDTO pageDto = new PageDTO(bdCnt, Integer.parseInt(currPage), cntPerPage);
		logger.debug(pageDto.toString());
		
		member.setPostCnt(bdCnt);
		member.setReplyCnt(rpCnt);
		
		model.addAttribute("authentication",member)
			.addAttribute("replyByPage", boardService.selectMyReply(member, pageDto))
			.addAttribute("page",pageDto);
		
	}
	
	@GetMapping("my-schedule")
	public void schedule(Model model
						,@SessionAttribute(value = "authentication") Member member
						,@RequestParam(value = "p", required = false) String currPage) {

		int cntPerPage = 8;
		int bdCnt = boardService.countMyPost(member);
		int rpCnt = boardService.countMyReply(member);
		int scCnt = memberService.countMySchedule(member);
		
		if(currPage == null) {
			currPage = "1";
		}
		
		PageDTO pageDto = new PageDTO(scCnt, Integer.parseInt(currPage), cntPerPage);

		logger.debug("스케줄 페이징 :" + pageDto.toString());
		
		member.setPostCnt(bdCnt);
		member.setReplyCnt(rpCnt);

		model.addAttribute("authentication",member)
			.addAttribute("mySchedule", boardService.selectMySchedule(member,pageDto))
			.addAttribute("page",pageDto);		
		
		
	}
	
}
