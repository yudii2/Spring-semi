package com.kh.spring.member.model.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.kh.spring.member.model.dto.Member;
import com.kh.spring.member.validator.JoinForm;

@Mapper
public interface MemberRepository {
	
	@Select("select password from member where user_id = #{userId} and email = #{email}")
	String selectPassword(String userId);

	@Insert("insert into member (user_id,password,nickname,birth,info,email) values(#{userId},#{password},#{nickname},#{birth},#{info}#{email}})")
	void insertMember(JoinForm form);

	@Select("select * from member where user_id = #{userId} and password = #{password}")
	Member authenticateUser(Member member);

	@Select("select * from member where user_id = #{userId}")
	Member selectMemberById(String userId);
}
