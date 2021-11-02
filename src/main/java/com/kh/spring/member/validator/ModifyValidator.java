package com.kh.spring.member.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.kh.spring.member.model.repository.MemberRepository;


@Component
public class ModifyValidator implements Validator{

	private final MemberRepository memberRepository;	
	
	public ModifyValidator(MemberRepository memberRepository) {
		super();
		this.memberRepository = memberRepository;
	}	
	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		ModifyForm form = (ModifyForm) target;
		
		//닉네임 중복확인
		
		//기존 비밀번호 일치여부
		
		//비밀번호 유효성
		
		//비밀번호확인 일치여부
	
	}



}
