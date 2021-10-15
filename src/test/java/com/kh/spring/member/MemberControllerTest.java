package com.kh.spring.member;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@WebAppConfiguration	//가상으로 만들어지는 web.xml을 사용해 테스트 환경을 구축
@RunWith(SpringJUnit4ClassRunner.class)				//어떤 방식으로 Junit을 실행할 것인지를 지정; 테스트 때 사용할 가상의 applicationContext를 생성하고 관리
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*-context.xml"})	//가상의 applicationContext를 생성할 때 사용할 spring bean 설정파일의 위치를 지정
public class MemberControllerTest {

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
	public void searchPassword() throws Exception{
		mockMvc.perform(get("/search-pw"))
			.andExpect(status().isOk())
			.andDo(print());
	}
	
	@After
	public void afterTest() {
		System.out.println("테스트 종료");
	}
}
