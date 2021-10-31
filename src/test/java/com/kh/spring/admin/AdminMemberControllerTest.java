package com.kh.spring.admin;

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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*-context.xml"})
public class AdminMemberControllerTest {
	
	//가상의 webapplicationContext
	@Autowired
	WebApplicationContext context;

	//가상환경 테스트 객체
	private MockMvc mockMvc;
	
	//테스트 전 세팅
	@Before
	public void set() {
		this.mockMvc = webAppContextSetup(context).build();
	}
	
	@Test
	public void selectMemberListTest() throws Exception {
		System.out.println(mockMvc);
		mockMvc.perform(get("/admin/member-list"))
		.andExpect(status().isOk())
		.andExpect(forwardedUrl("/WEB-INF/views/index.jsp"))
		.andDo(print());
	}
	
}
