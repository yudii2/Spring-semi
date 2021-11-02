package com.kh.spring.member.model.service;

import java.util.List;

import com.kh.spring.board.model.dto.Board;
import com.kh.spring.common.util.PageDTO;
import com.kh.spring.member.model.dto.Member;
import com.kh.spring.member.validator.JoinForm;

public interface MemberService {
	
	public void insertMember(JoinForm form);

	public Member authenticateUser(Member member);

	public Member selectMemberById(String userId);

	public void authenticateByEmail(JoinForm form, String token);

}
