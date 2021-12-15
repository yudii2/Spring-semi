package com.kh.spring.member;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration	//가상으로 만들어지는 web.xml을 사용해 테스트 환경을 구축
@RunWith(SpringJUnit4ClassRunner.class)				//어떤 방식으로 Junit을 실행할 것인지를 지정; 테스트 때 사용할 가상의 applicationContext를 생성하고 관리
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*-context.xml"})	//가상의 applicationContext를 생성할 때 사용할 spring bean 설정파일의 위치를 지정
public class MemberServiceTest {
	
	@Autowired
	PasswordEncoder passwordEncoder;

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Test
	public void passwordEncoderTest() {
		String password ="123qwe!@#";
		String encodedPassword = passwordEncoder.encode(password);
		logger.debug(encodedPassword);
		encodedPassword = passwordEncoder.encode(password);
		logger.debug(encodedPassword);
		
		logger.debug("mathes : " + passwordEncoder.matches(password, encodedPassword));
	}
	
}
