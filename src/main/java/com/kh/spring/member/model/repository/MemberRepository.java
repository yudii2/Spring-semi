package com.kh.spring.member.model.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.kh.spring.board.model.dto.Board;
import com.kh.spring.common.util.PageDTO;
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
	@Select("select * from member where nickname = #{nickname}")
	Member selectMemberByNickname(String nickname);

	/* List<Board> selectMyPost(String userId, PageDTO pageDto);  boardRepository에 존재 */	

	@Select("select count(*) from schedule join participant_list L using(sc_idx) join participant_history H using (pl_idx) where H.user_id = #{userId}")
	int countMySchedule(Member member);

	
}
