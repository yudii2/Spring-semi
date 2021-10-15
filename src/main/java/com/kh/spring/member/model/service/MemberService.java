package com.kh.spring.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.member.model.repository.MemberRepository;

@Service
public class MemberService {

	@Autowired
	private MemberRepository memberRepository;	//인터페이스일 경우 : spring이 프록시객체를 만들어 반환해준다. 그래서 인터페이스이지만 구현체없이 메서드를 호출해 사용하는 것이 가능하다.
												//mapper일 경우 : mapper를 등록할 경우 MemberRepository 프록시 객체로 자동 등록해주지는 않는다.
	public String selectPasswordById() {
		return memberRepository.getPassword("DEV");
	}
}
