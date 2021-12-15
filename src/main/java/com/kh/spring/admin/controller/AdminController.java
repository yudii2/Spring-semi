package com.kh.spring.admin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kh.spring.admin.model.service.AdminMemberService;
import com.kh.spring.schedule.model.service.ScheduleService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("admin")
public class AdminController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//AdminMemberControllerTest클래스를 생성해 200번 응답코드가 반환되면 테스트 통과하도록 테스트 메서드 작성
	private final AdminMemberService adminMemberService;
	private final ScheduleService scheduleService;
	
	@GetMapping("admin")
	public void admin() {}
	
	@GetMapping("new-schedule")
	public void newSchedule(Model model) {
		model.addAttribute("scheduleList", scheduleService.selectNonApprovedSchdule());
	}
	
	@GetMapping("approve-schedule/{scIdx}")
	public String approveSchedule(@PathVariable String scIdx) {
		scheduleService.approveSchedule(scIdx);
		return "redirect:/admin/new-schedule";
	}
	@GetMapping("reject-schedule/{scIdx}")
	public String rejectSchedule(@PathVariable String scIdx) {
		scheduleService.rejectSchedule(scIdx);
		return "redirect:/admin/new-schedule";
	}
	
	

}
