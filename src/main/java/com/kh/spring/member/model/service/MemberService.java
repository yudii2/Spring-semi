package com.kh.spring.member.model.service;

import com.kh.spring.member.model.dto.Member;
import com.kh.spring.member.validator.JoinForm;

public interface MemberService {
	
	public void insertMember(JoinForm form);

	public Member authenticateUser(Member member);

	public Member selectMemberById(String userId);

	public void authenticateByEmail(JoinForm form, String token);
}
