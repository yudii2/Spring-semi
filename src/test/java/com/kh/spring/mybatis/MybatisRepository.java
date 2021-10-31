package com.kh.spring.mybatis;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.kh.spring.member.model.dto.Member;

@Mapper
public interface MybatisRepository {	//인터페이스의 타입(com.kh.spring.mybatis)과 com.kh.spring.mybatis.mapper.mybatisMapper가 만들어내는 매퍼와 동일한 namespace를 가지고 있어야 동일 이름을 가진 메서드를 호출시 충돌을 최소화할 수 있다.
										//두개를 합친 하나의 프록시 객체를 만들어서 의존성 주입을 할 수 있다.
	
	@Select("select password from member where user_id = #{userId}")
	String selectPasswordByUserId(String userId);
	
	@Select("select * from member where user_id = #{userId}")
	Member selectMemberById(String userId);
	
	//interface의 프록시객체를 사용하기 위해서는 매퍼.xml에 등록되어 있는 메서드를 선언해 주어야 한다.
	Map<String,Object> selectRentAndMemberById(String userId);	//mybatisMapper.xml에 선언된 메서드(쿼리가 길어 어노테이션 쿼리 비효율)

	List<Map<String,Object>> selectRentDataById(String userId);	//컬럼명이 property로 변경되지 않음
	
	@Insert("insert into member (user_id,password,tell,email) values(#{userId},#{password},#{tell},#{email})")
	int insertWithDTO(Member member);
	
	@Insert("insert into rent_master(rm_idx,user_id,title,rent_book_cnt) "
			+ "values(sc_rm_idx.nextval,#{member.userId},#{title},#{rentBookCnt})")
	int insertWithMap(Map<String,Object> obj);
	
	@Delete("delete from rent_master where user_id = #{userId}")
	int delete(String userId);
	
	@Update("update member set password = #{password} where user_id = #{userId}")
	int update(Map<String,Object> obj);
	
	@Update("{call sp_rent_extend(#{rbIdx, mode=IN})}")
	void procedure(String rbIdx);
	
	List<Map<String,Object>> dynamicQueryIf(Map<String,Object> commandMap);
	
	List<Map<String,Object>> dynamicQueryChoose(Map<String,Object> commandMap);
	
	List<Map<String,Object>> dynamicQueryWhereAndForeachTag(Map<String,Object> commandMap);
	
	List<Map<String,Object>> dynamicQueryForeachTagWithList(List<String> userList);
	
	int dynamicQuerySetTag(Member member);
}
