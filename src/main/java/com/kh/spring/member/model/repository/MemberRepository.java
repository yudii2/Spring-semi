package com.kh.spring.member.model.repository;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository	//Dao 임을 선언해줌. sqlException을 DataAccessExeption으로 자동 변환해줌
public class MemberRepository {
	
	@Autowired
	private SqlSessionTemplate session;		//root-context에서 등록한 bean(like JDBCTemplate)

	public String getPassword(String userId) {
		return session.selectOne("com.kh.spring.mybatis.mybatisMapper.selectPasswordByUserId",userId);		//두번째 인자인 parameter값과 매퍼에 지정되어있는 #{}안의 인자와 변수명이 같아야 한다.
	}																										//PreparedStatement로 돌아가는 원리

}
