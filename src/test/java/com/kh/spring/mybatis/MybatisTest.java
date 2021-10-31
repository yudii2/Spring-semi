package com.kh.spring.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.kh.spring.member.model.dto.Member;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*-context.xml"})	
public class MybatisTest {
	
	//SqlSessionTemplate 주요 메서드
	//selectOne : 단일행을 반환하는 select문 실행 메서드
	//selectList(추천), selectMap : 다중 행을 반환하는 select문 실행 메서드
	//insert : 메서드 결과, 쿼리에 의해 영향을 받은 row의 수를 반환
	//update : 메서드 결과, 쿼리에 의해 영향을 받은 row의 수를 반환
	//delete : 메서드 결과, 쿼리에 의해 영향을 받은 row의 수를 반환
	// ** procedure 호출은 dml 쿼리 메서드 중 선택
	
	@Autowired
	private MybatisRepository mybatisRepository;
	String userId = "USER1";
	
	@Test
	public void selectOneTest() {
		mybatisRepository.selectPasswordByUserId(userId);
	}
	
	@Test
	public void selectOneAsDTO() {
		mybatisRepository.selectMemberById(userId);
	}
	
	@Test
	public void selectOneAsMap() {
		mybatisRepository.selectRentAndMemberById(userId);
	}
	
	@Test
	public void selectListAsMap() {
		
	}
	
	@Test
	public void selectListUsingResultMap() {
		List<Map<String,Object>> res = mybatisRepository.selectRentDataById(userId);
		for (Map<String, Object> map : res) {
			System.out.println(map);
		}
	
	}
	@Test
	public void insertWithDTO() {
		Member member = new Member();
		member.setUserId("interface");
		member.setPassword("1234");
		member.setEmail("interface@mybatis.com");
		
		mybatisRepository.insertWithDTO(member);
	}

	@Test
	public void insertWithMap() {
		Map<String,Object> commandMap = new HashMap<String, Object>();
		
		Member member = new Member();
		member.setUserId("interface");
		commandMap.put("member", member);
		
		commandMap.put("title", "인터페이스란");
		commandMap.put("rentBookCnt", 1);
		
		mybatisRepository.insertWithMap(commandMap);
	}
	@Test
	public void delete() {
		String userId = "interface";
		mybatisRepository.delete(userId);
	}
	@Test
	public void update() {
		Map<String,Object> commandMap = new HashMap<String, Object>();
		
		commandMap.put("userId", "interface");
		commandMap.put("password", "12121");
		
		mybatisRepository.update(commandMap);
	}
	
	@Test
	public void procedure() {
		String rbIdx = "100025";
		mybatisRepository.procedure(rbIdx);
	}
	

   @Test
   public void dynamicQueryIF() {
	   mybatisRepository.dynamicQueryIf(Map.of("searchType", "author", "keyword", "김애란"));
   }
   
   @Test
   public void dynamicQueryChoose() {
	   mybatisRepository.dynamicQueryChoose(Map.of("searchType", "", "keyword", "김애란"));
   }
   
   @Test
   public void dynamicQueryWhereAndForeachTag() {
	   String[] searchTypes = {"rentable","title"};
	   mybatisRepository.dynamicQueryWhereAndForeachTag(Map.of("searchTypes", searchTypes, "keyword", "김애란"));
   }
   
   @Test
   public void dynamicQueryForeachTagWithList() {
	   mybatisRepository.dynamicQueryForeachTagWithList(List.of("DEV","mybatis","interface"));
   }
   
   @Test
   public void dynamicQuerySetTag() {
	   Member member = new Member();
	   member.setUserId(userId);
	   member.setPassword("00009");
	   
	   mybatisRepository.dynamicQuerySetTag(member);
   }   
   
   @Test
   public void dynamicQueryForeachTag() {
	   
   }

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//1. 도서명 : 쿠키와 세션,
	//	 작가 	: 김영아
	//	 도서번호 : 시퀀스 사용
	//	 인 도서를 BOOK 테이블에 저장하기
	//	 메서드 이름 : test01
	@Test
	public void test01() {}
	
	//2. 연장횟수가 2회 이상인 모든 대출도서 정보를
	//	 연장횟수 0회로 초기화 해주세요
	//	 메서드 이름 : test02
	@Test
	public void test02() {}
	
	//3. 2021년 10월 이전 9월 이후에 가입된 회원정보를 삭제
	//	 메서드 이름 : test03
	@Test
	public void test03() {}
	
	//4. 대출 횟수가 가장 많은 3권의 도서를 조회
	//	 메서드 이름 : test04
	@Test
	public void test04() {}
}
