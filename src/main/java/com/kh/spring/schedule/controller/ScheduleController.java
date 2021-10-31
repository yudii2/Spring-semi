package com.kh.spring.schedule.controller;

import java.text.DateFormat;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.spring.schedule.model.dto.Schedule;
import com.kh.spring.schedule.model.service.ScheduleService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("schedule")
public class ScheduleController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final ScheduleService scheduleService;
	
	@GetMapping("calendar")
	public void calendar() {}
	
	@GetMapping("schedule-form")
	public void schduleForm() {}
	
	@GetMapping(value = "calendar-list",produces = "application/json; charset=utf-8")
	@ResponseBody		//return값을 responseBody에 담아 전달
	public String calendarList() throws JsonProcessingException {
		List<Schedule> scheduleList = scheduleService.selectAllSchedule();
		
		//json으로 브라우저 전달
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(DateFormat.getDateInstance(DateFormat.MEDIUM));
		String scheduleJson = mapper.writeValueAsString(scheduleList);
		
		logger.debug(scheduleJson.toString());
		
		return scheduleJson;

	}
	
	@PostMapping("schedule-detail")
	public String scheduleDetail(@RequestBody String json) throws JsonMappingException, JsonProcessingException {
				
		ObjectMapper mapper = new ObjectMapper();
		Schedule scheduleObj = mapper.readValue(json, Schedule.class);
		
		Map<String,Object> schedule = scheduleService.selectScheduleDetail(scheduleObj.getScIdx());
		
		logger.debug(schedule.get("schedule").toString());
		logger.debug(schedule.get("participants").toString());
		
		return "";
	}
	

}
