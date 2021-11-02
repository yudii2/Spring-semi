package com.kh.spring.member.model.service;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.kh.spring.board.model.dto.Board;
import com.kh.spring.common.code.Config;
import com.kh.spring.common.mail.MailSender;
import com.kh.spring.common.util.PageDTO;
import com.kh.spring.member.model.dto.Member;
import com.kh.spring.member.model.repository.MemberRepository;
import com.kh.spring.member.validator.JoinForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor	//필드변수들을 매개변수로 하는 생성자를 lombok이 등록해줌
public class MemberServiceImpl implements MemberService{

	private final RestTemplate template;
	private final MailSender mailSender;
	private final MemberRepository memberRepository;	//인터페이스일 경우 : spring이 프록시객체를 만들어 반환해준다. 그래서 인터페이스이지만 구현체없이 메서드를 호출해 사용하는 것이 가능하다.
														//mapper일 경우 : mapper를 등록할 경우 MemberRepository 프록시 객체로 자동 등록해주지는 않는다.
	private final PasswordEncoder passwordEncoder;

	
	public void insertMember(JoinForm form) {
		form.setPassword(passwordEncoder.encode(form.getPassword()));
		memberRepository.insertMember(form);
	}

	public Member authenticateUser(Member member) {
		Member storedMember = memberRepository.selectMemberById(member.getUserId());
//
//		if(storedMember != null && passwordEncoder.matches(member.getPassword(),storedMember.getPassword())) {
//			return storedMember;
//		}
//		return null;
		return storedMember;
	}

	public Member selectMemberById(String userId) {
		return memberRepository.selectMemberById(userId);
	}

	@Override
	public Member selectMemberByNickname(String nickname) {
		return memberRepository.selectMemberByNickname(nickname);
	}

	public void authenticateByEmail(JoinForm form, String token) {		//우리가 우리서버에 요청할 때 보낼 값들
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("mail-template", "join-auth-email");
		body.add("userId", form.getUserId());
		body.add("persistToken", token);
		
		//RestTemplate의 기본 ContentType이 application/json이다. 다른 contentType일 경우 accept지정 필요
		RequestEntity<MultiValueMap<String, String>> request = 
				RequestEntity.post(Config.DOMAIN.DESC + "/mail")
				.accept(MediaType.APPLICATION_FORM_URLENCODED)
				.body(body);
		
		String htmlText = template.exchange(request, String.class).getBody();
		mailSender.sendEmail(form.getEmail(), "회원가입을 축하합니다.", htmlText);
				
	}



}
