package com.kh.spring.member.validator;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.kh.spring.member.model.repository.MemberRepository;

@Component	//controller,service,dao도 아닌 객체를 스프링bean으로 등록하기 위한 component어노테이션
public class JoinFormValidator implements Validator {
//
//	@Autowired
//	private MemberRepository memberRepository;
//	

	private final MemberRepository memberRepository;	
	
	public JoinFormValidator(MemberRepository memberRepository) {	//Autowired대신 유행하는 방식 ; 생성자 주입을 통해 쓰래드로부터 안전
		super();
		this.memberRepository = memberRepository;
	}

	@Override
	public boolean supports(Class<?> clazz) {	//validate실행 전 target으로 넘길 객체를 미리 전달받아 validating할 수 있는가를 판단해 boolean으로 반환해줌
		return clazz.equals(JoinForm.class);	//전달받은 target이 JoinForm 타입의 인스턴스가 맞니?
	}

	@Override
	public void validate(Object target, Errors errors) {	//데이터 검증
		JoinForm form = (JoinForm) target;
		
		//1. 아이디 존재 유무
		if(memberRepository.selectMemberById(form.getUserId()) != null) {
			errors.rejectValue("userId", "err-userId","이미 존재하는 아이디 입니다.");	//rejectValue() : form의 어떤 field(input name)를 검증할것인지
		}
		
		//2. 비밀번호가 8자 이상 숫자, 영무자, 특수문자 조합인지 확인
		if(!Pattern.matches("(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[^a-zA-Zㄱ-힣0-9]).{8,15}", form.getPassword())) {
			errors.rejectValue("password",  "err-password","비밀번호는 숫자, 영문자, 특수문자 조합의 8-15자 입니다. ");
		}
		
		//3. 전화번호 9-11자 숫자
		if(!Pattern.matches("\\d{9,11}", form.getTell())) {
			errors.rejectValue("tell", "err-tell","전화번호는 9-11자의 숫자입니다. ");
		}
	}

}
