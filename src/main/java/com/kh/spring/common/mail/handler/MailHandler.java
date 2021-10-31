package com.kh.spring.common.mail.handler;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller	//요청을 받을 수 있어야 하므로 컨트롤러로 지정
public class MailHandler {		//우리서버의 response를 받아 메일첨부
	
	@PostMapping("mail")
	public String writeMailTemplate(@RequestParam Map<String,String> template) {	//Map은 getter/setter가 없으므로 ModelAttribute를 쓸 수 없다.
																					//대신, RequestParameter를 사용해야 하는데 이때 생략은 불가하다(생략 조건 : 파라미터명과 바인딩할 객체 속성명이 일치하는 경우)	
		return "mail-template/" + template.get("mail-template");
	}


}
