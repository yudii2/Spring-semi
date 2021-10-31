package com.kh.spring.common;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Map;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.kh.spring.common.code.Config;

@WebAppConfiguration	//가상으로 만들어지는 web.xml을 사용해 테스트 환경을 구축
@RunWith(SpringJUnit4ClassRunner.class)				//어떤 방식으로 Junit을 실행할 것인지를 지정; 테스트 때 사용할 가상의 applicationContext를 생성하고 관리
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*-context.xml"})	//가상의 applicationContext를 생성할 때 사용할 spring bean 설정파일의 위치를 지정
public class MailTest {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired	//우리가 bean으로 등록해준 객체 주입
	JavaMailSender mailSender;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Test
	public void sendMail() throws MessagingException {
		
		MimeMessage msg = mailSender.createMimeMessage();
        msg.setFrom(Config.OFFICIAL_MAIL.DESC);
	    msg.setRecipients(Message.RecipientType.TO,"qleen513@gmail.com");
	    msg.setSubject("메일테스트");
	    msg.setSentDate(new Date());
	    msg.setText("<h1>이메일 테스트입니다.</h1>","utf-8","html");
	    
	    mailSender.send(msg);
	}
	
	@Test
	public void resTemplateGetTest() throws URISyntaxException, JsonMappingException, JsonProcessingException {	//restTemplate은 객체 바인딩을 해주기 때문에 json파싱할 필요가 없다.***
		String naverIndex = restTemplate.getForObject("https://www.naver.com", String.class);	//여기서 get은 getter가 아닌 GET url요청을 의미
		
		HttpHeaders kakaoheader = new HttpHeaders();
		kakaoheader.add("Authorization", "KakaoAK 02235aa3e05dc625e3390ddd831ad7de");
		
		RequestEntity request = RequestEntity.get("https://dapi.kakao.com/v2/search/web?query=java")
				.headers(kakaoheader)
				.build();
		
		//Map<String,String> obj = restTemplate.exchange(request, Map.class).getBody();	//exchange가 respoonseEntity를 반환하고, 해당 클래스는 getBody(), getHeader()등으로 원하는 데이터를 받아올 수있다.
		//logger.debug(obj.toString());
		
		String obj = restTemplate.exchange(request, String.class).getBody();	
		ObjectMapper mapper = new ObjectMapper();	//Map을 매핑하지 못함
		JsonNode root = mapper.readTree(obj);
		
		for (JsonNode jsonNode : root.findValues("url")) {	//JsonNode는 iterable이 구현된 객체 -> findValues를 사용하면 depth에 상관없이 알아서 forEach처럼 내부를 탐색하며 속성명으로 값을 찾는다.(gson보다 편함) 
			logger.debug(jsonNode.asText());
		}
		
	}
	
	@Test
	public void restTemplatePostTest() throws JsonMappingException, JsonProcessingException {
		
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		header.add("X-Naver-Client-Id", "wsMhNO6MzI08n5rHAfvg");
		header.add("X-Naver-Client-Secret", "qkq2ZttrzH");
	
		//하나의 키값에 여러개의 value를 지정할 수 있다.spring제공
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("source", "en");
		body.add("target", "ko");
		body.add("text", "Extension of HttpEntity that also exposes the HTTP method and the target URL. For use in the RestTemplate to prepare requests with and in @Controller methods to represent request input.");
		
		RequestEntity<MultiValueMap> request = RequestEntity
				.post("https://openapi.naver.com/v1/papago/n2mt")
				.headers(header)
				.body(body);
		//Map<String,String> obj = restTemplate.exchange(request, Map.class).getBody();	//exchange가 respoonseEntity를 반환하고, 해당 클래스는 getBody(), getHeader()등으로 원하는 데이터를 받아올 수있다.
		//logger.debug(obj.toString());
		MultiValueMap<String, String> head = new LinkedMultiValueMap<String, String>();
		head.add("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
		head.add("X-Naver-Client-Id", "wsMhNO6MzI08n5rHAfvg");
		head.add("X-Naver-Client-Secret", "qkq2ZttrzH");
		restTemplate.headForHeaders("https://openapi.naver.com/v1/papago/n2mt", head);
		
		String obj = restTemplate.exchange(request, String.class).getBody();	
		ObjectMapper mapper = new ObjectMapper();	//Map을 매핑하지 못함
		JsonNode root = mapper.readTree(obj);
		
		//응답값이 하나만 존재한다면
		logger.debug(root.findValue("translatedText").asText());
		
		for (JsonNode jsonNode : root.findValues("translatedText")) {	//JsonNode는 iterable이 구현된 객체 -> findValues를 사용하면 depth에 상관없이 알아서 forEach처럼 내부를 탐색하며 속성명으로 값을 찾는다.(gson보다 편함) 
			logger.debug(jsonNode.asText());							//forEach에서 반환되는 List값이 없으면 돌지 않는다. 따라서 findValue메서드를 사용하면 돌지 않는다.
		}
	}

}
