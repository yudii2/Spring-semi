package com.kh.spring.member;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.spring.member.model.dto.Member;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.util.Map;

import javax.servlet.http.Cookie;

@WebAppConfiguration	//가상으로 만들어지는 web.xml을 사용해 테스트 환경을 구축
@RunWith(SpringJUnit4ClassRunner.class)				//어떤 방식으로 Junit을 실행할 것인지를 지정; 테스트 때 사용할 가상의 applicationContext를 생성하고 관리
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*-context.xml"})	//가상의 applicationContext를 생성할 때 사용할 spring bean 설정파일의 위치를 지정
public class MemberControllerTest {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired //의존성 주입
	WebApplicationContext context;
	
	//mock객체 : 가상환경에서 테스트할 때 테스트를 수행하는 객체
	private MockMvc mockMvc;

	// *** junit annotation
	//@Before : 테스트 수행 전 실행될 메서드에 선언
	//@Test	  : 테스트를 수행할 메서드
	//@After  : 테스트 수행 후 실행될 메서드에 선언

	@Before
	public void setup() {
		this.mockMvc = webAppContextSetup(context).build();
	}
	@Test
	public void joinTest() throws Exception {
		mockMvc.perform(post("/member/join")
				.param("userId", "test34")
				.param("password", "1234qwe!@#")
				.param("email", "qleen513@gmail.com")
				.param("tell", "01000001111"))
			.andExpect(status().is3xxRedirection())
			.andDo(print());
	}
	
	@Test
	public void jacksonTest() throws JsonProcessingException {
		Member member = new Member();
		member.setUserId("testJson");
		member.setPassword("123456");
		member.setEmail("aaa@vvv.com");
		
		ObjectMapper mapper = new ObjectMapper();
		//자바 객체 -> json문자열
		String memberJson = mapper.writeValueAsString(member);
		logger.debug(memberJson);
	
		//json문자열 -> 자바객체	*** 외부에서 json으로 받아올 때 사용할 메서드
		Member obj = mapper.readValue(memberJson, Member.class);
		logger.debug(obj.toString());
		
		//json문자열 -> java.util.Map
		Map<String, Object> map = mapper.readValue(memberJson, Map.class);
		logger.debug(map.toString());
	}
	
	@Test
	public void joinWithJson() throws Exception{
		Member member = new Member();
		member.setUserId("testJson");
		member.setPassword("123456");
		member.setEmail("aaa@vvv.com");
		
		ObjectMapper mapper = new ObjectMapper();
		String memberJson = mapper.writeValueAsString(member);
		
		mockMvc.perform(post("/member/join-json")
				.contentType(MediaType.APPLICATION_JSON)		//요청헤더
				.content(memberJson))							//요청바디
			.andExpect(status().isOk())
			.andDo(print());
	}
	
	@Test
	public void loginImpl() throws Exception {
		mockMvc.perform(post("/member/login")
				.param("userId", "USER1")
				.param("password", "abcd1234@@"))
		.andExpect(status().isOk())
		.andDo(print());
		
	}
	
	@Test
	public void mypage() throws Exception{
		
		Member member = new Member();
		member.setUserId("testJson");
		member.setPassword("123456");
		member.setEmail("aaa@vvv.com");
		
		mockMvc.perform(get("/member/mypage")
				.cookie(new Cookie("JSESSIONID","15486515485SESSION인척"))
				.sessionAttr("authentication", member))
		.andExpect(status().isOk())
		.andDo(print());
	}
	
	@Test
	public void idCheckTest() throws Exception{
		//testUser라는 아이디의 사용자가 존재하는지 확인
		String userId = "USER1";
		mockMvc.perform(get("/member/id-check")
				.param("userId", userId))
		.andExpect(status().isOk())
		.andDo(print());
	}
}
