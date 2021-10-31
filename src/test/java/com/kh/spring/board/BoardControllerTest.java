package com.kh.spring.board;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.kh.spring.member.model.dto.Member;

@WebAppConfiguration	//가상으로 만들어지는 web.xml을 사용해 테스트 환경을 구축
@RunWith(SpringJUnit4ClassRunner.class)				//어떤 방식으로 Junit을 실행할 것인지를 지정; 테스트 때 사용할 가상의 applicationContext를 생성하고 관리
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*-context.xml"})	//가상의 applicationContext를 생성할 때 사용할 spring bean 설정파일의 위치를 지정
public class BoardControllerTest {
	
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
	public void uploadBoard() throws Exception {
		
		MockMultipartFile file1 = new MockMultipartFile("files","OFN.txt",null,"OFN01".getBytes());
		MockMultipartFile file2 = new MockMultipartFile("files","OFN.txt",null,"OFN01".getBytes());
		
		Member member = new Member();
		member.setUserId("test12");
		
		mockMvc.perform(multipart("/board/upload")
				.file(file1)
				.file(file2)
				.param("title", "게시글테스트 메서드")
				.param("content", "본문")
				.sessionAttr("authentication", member))
		.andExpect(status().is3xxRedirection())
		.andDo(print());
		
	}
	
	@Test
	public void boardDetail() throws Exception{
		String bdIdx = "100001";
		
		mockMvc.perform(get("/board/board-detail")
				.param("bdIdx", bdIdx))
		.andExpect(status().isOk())
		.andDo(print());
	}
	
	
	
	
}
