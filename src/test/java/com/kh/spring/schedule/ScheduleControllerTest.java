package com.kh.spring.schedule;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.spring.member.model.dto.Member;
import com.kh.spring.schedule.model.dto.Schedule;


@WebAppConfiguration	//가상으로 만들어지는 web.xml을 사용해 테스트 환경을 구축
@RunWith(SpringJUnit4ClassRunner.class)				//어떤 방식으로 Junit을 실행할 것인지를 지정; 테스트 때 사용할 가상의 applicationContext를 생성하고 관리
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*-context.xml"})	//가상의 applicationContext를 생성할 때 사용할 spring bean 설정파일의 위치를 지정
public class ScheduleControllerTest {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired //의존성 주입
	WebApplicationContext context;
	
	//mock객체 : 가상환경에서 테스트할 때 테스트를 수행하는 객체
	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = webAppContextSetup(context).build();
	}

	@Test
	public void calendarListTest() throws JsonProcessingException {
		List<Schedule> schedules = new ArrayList<Schedule>();
		Schedule schedule = new Schedule();
		schedule.setUserId("test1");
		schedule.setdDay(new Date(20211110));
		schedule.setMountainName("북한산");
		schedule.setAllowedNum(5);
		schedule.setInfo("테스트중");
		schedule.setOpenChat("www.test.com");
		
		Schedule schedule2 = new Schedule();
		schedule2.setUserId("test2");
		schedule2.setdDay(new Date(20211110));
		schedule2.setMountainName("한산");
		schedule2.setAllowedNum(5);
		schedule2.setInfo("테스트중");
		schedule2.setOpenChat("www.test.com");
		
		schedules.add(schedule);
		schedules.add(schedule2);
		
		ObjectMapper mapper = new ObjectMapper();

		String scheduleJson = mapper.writeValueAsString(schedules);
		logger.debug(scheduleJson);
		
	}
	
	@Test
	public void scheduleDetailTest() {
		Member member = new Member();
		member.setUserId("aaa");
		member.setPassword("1234");
		member.setNickname("nicka");
		
		Schedule schedule = new Schedule();
		schedule.setUserId("test1");
		schedule.setdDay(new Date(20211110));
		schedule.setMountainName("북한산");
		schedule.setAllowedNum(5);
		schedule.setInfo("테스트중");
		schedule.setOpenChat("www.test.com");
		
		List<String> participants = new ArrayList<String>();
		participants.add("1");
		participants.add("2");
		
		Map<String,Object> scheduleObj = new HashMap<String, Object>();
		scheduleObj = Map.of("participants", participants, "schedule", schedule);
		scheduleObj.put("member", member);
		
		logger.debug(scheduleObj.get("member").toString());
		
		
	}

}
