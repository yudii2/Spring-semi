package com.kh.spring.index;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kh.spring.schedule.model.dto.Schedule;
import com.kh.spring.schedule.model.service.ScheduleService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class IndexController {

	// @Controller : 해당 클래스를 applicationContext의 bean으로 등록해준다.
	//				Controller와 관련된 annotation을 사용할 수 있게 해준다.
	// @RequestMapping : 어떤 요청 URL과 Controller의 메서드를 매핑할지 지정해줌
	//				클래스 위에 선언할 경우, 해당 클래스의 모든 메서드가 지정된 경로를 상위경로로 가짐
	//				RequestMAapping("/member")로 지정해둘 경우 /member/* 뒤에오는 url을 지정할 때 member 생략 가능
	// @GetMapping : 어떤 GET방식의 요청 URL과 Controller의 메서드를 매핑할지 지정
	// @PostMapping : 어떤 POST방식의 요청 URL과 Controller의 메서드를 매핑할지 지정
	// @RequestParam : 요청 파라미터를 컨트롤러 메서드의 매개변수에 bind
	//				단, Content-type이 application/x-www-form-urlEncoded인 경우에만!
	//				내부적으로 파라미터 요청을 위해서 FormHttpMessageConverter가 동작
	//				@RequestParam속성
	//				- name : 요청파라미터명, 매개변수명과 요청파라미터명이 같은 경우 생략 가능
	//				- required : 요청파라미터 필수 여부, default=true;
	//				- defaultValue : 요청파라미터가 전달되지 않은 경우 지정할 기본 값
	// @RequestBody : 요청 body를 읽어서 자바의 객체에 바인드
	//				application/x-www-form-urlEncoded을 지원하지 않는다.
	//				application/json일 때 사용해보자
	// @ModelAttribute : 요청파라미터를 setter를 사용해서 객체에 바인드, Model객체에 바인드된 객체를 자동으로 저장해줌
	// @RequestHeader : 요청 헤더를 컨트롤러의 매개변수에 바인드
	// @SessionAttribute : 원하는 session의 속성값을 매개변수에 바인드
	// @CookieVariable : 원하는 cookie의 값을 매개변수에 바인드
	// @PathVariable : url 템플릿에 담긴 파라미터값을 매개변수에 바인드
	// @RespoonseBody : 메서드가 반환하는 값을 응답 body에 작성
	
	// Servlet객체도 컨트롤러의 매개변수로 선언해 주입받을 수 있다.
	// (HttpServletRequest, HttpServletResponse, HttpSession)
	
	private final ScheduleService scheduleService;
		
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("schedules", scheduleService.selectAllSchedule());
		return "index";
	}
	
	
	
}
