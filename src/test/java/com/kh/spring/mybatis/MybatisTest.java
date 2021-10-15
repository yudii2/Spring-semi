package com.kh.spring.mybatis;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.kh.spring.member.model.dto.Member;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*-context.xml"})	//root-context또는 servlet-context 등 모두를 등록
public class MybatisTest {
	
	String userId = "DEV";
	
	//SqlSessionTemplate 주요 메서드
	//selectOne : 단일행을 반환하는 select문 실행 메서드
	//selectList(추천), selectMap : 다중 행을 반환하는 select문 실행 메서드
	//insert : 메서드 결과, 쿼리에 의해 영향을 받은 row의 수를 반환
	//update : 메서드 결과, 쿼리에 의해 영향을 받은 row의 수를 반환
	//delete : 메서드 결과, 쿼리에 의해 영향을 받은 row의 수를 반환
	// ** procedure 호출은 dml 쿼리 메서드 중 선택
	
	@Autowired
	private SqlSessionTemplate session;
	private final String NAMESPACE = "com.kh.spring.mybatis.mybatisMapper.";
	
	@Test
	public void selectOneTest() {
		session.selectOne(NAMESPACE + "selectPasswordByUserId", userId);
	}
	
	@Test
	public void selectOneAsDTO() {
		Member member = session.selectOne(NAMESPACE + "selectMemberById", userId);
		System.out.println(member);		//user_id와 userId를 동일하다고 인지하지 못해 매핑하지 못함
		
	}
	
	@Test
	public void selectOneAsMap() {
		Map<String, Object> res = session.selectOne(NAMESPACE + "selectRentAndMemberById", userId);
		System.out.println(res);
	}
	
	@Test
	public void selectListAsMap() {
		String userId = "YUDI";
		List<Map<String, Object>> res = session.selectList(NAMESPACE + "selectRentAndMemberById", userId);
		for (Map<String, Object> map : res) {
			System.out.println(map);
		}
	}
	
	@Test
	public void selectListUsingResultMap() {
		List<Map<String,Object>> res = session.selectList(NAMESPACE + "selectRentDataById", userId);
		for (Map<String, Object> map : res) {
			System.out.println(map);
		}
		
	}
	@Test
	public void insertWithDTO() {
		Member member = new Member();
		member.setUserId("mybatis");
		member.setEmail("pclass@mybatis.com");
		member.setPassword("1234");
		member.setTell("010-0000-1111");
		
		session.insert(NAMESPACE + "insertWithDTO", member);
	}

	@Test
	public void insertWithMap() {
		Member member = new Member();
		member.setUserId("mybatis");
		
		Map<String,Object> commandMap = new HashMap<String, Object>();
		commandMap.put("member", member);
		commandMap.put("title", "mybatis 입문 서적 외 2권");
		commandMap.put("rentBookCnt", 3);
		
		session.insert(NAMESPACE + "insertWithMap", commandMap);
		
	}
	@Test
	public void delete() {
		String userId = "mybatis";
		session.delete(NAMESPACE + "delete",userId);
	}
	@Test
	public void update() {
		Member member = new Member();
		member.setUserId("mybatis");
		member.setPassword("abcdefg");
		session.update(NAMESPACE + "update", member);
	}
	
	@Test
	public void procedure() {
		String rbIdx = "100026";
		session.insert(NAMESPACE + "procedure",rbIdx);
	}
	
	//JDBC에서 쿼리를 동적으로 작성하기 어려운 문제를 mybatis에서 처리해줌

   @Test
   public void dynamicQueryIF() {
      //사용자가 도서 검색필터에서 info를 선택하고 검색하면 사용자가 입력한 키워드로 info 컬럼을 검색
      //사용자가 도서 검색필터에서 author를 선택하고 검색하면 사용자가 입력한 키워드로 author 컬럼을 검색
      //사용자 선택 필터 : info
      //사용자 입력 키워드 : 도시
      
   }
   
   @Test
   public void dynamicQueryChoose() {
      //사용자가 도서 검색필터에서 info를 선택하고 검색하면 사용자가 입력한 키워드로 info 컬럼을 검색
      //사용자가 도서 검색필터에서 author를 선택하고 검색하면 사용자가 입력한 키워드로 author 컬럼을 검색
      //사용자가 검색필터를 지정 하지 않을 경우 도서 제목으로 검색
      //사용자 선택 필터 : info
      //사용자 입력 키워드 : 도시
      
   }
   
   @Test
   public void dynamicQuerySetTag() {
      //사용자가 회원수정란에서 수정한 내용을 update하는 쿼리를 작성
      //사용자가 password, email만 수정했다면, update쿼리를
      //[update tb_member set password = 1234, email = 'aa@aa.com' where userId = 'aa']
      
      
   }
   
   @Test
   public void dynamicQueryForeachTagWithList() {
      //만능 insert쿼리 생성
   }

   @Test
   public void dynamicQueryWhereAndForeachTag() {
      //검색조건을 or 연산으로 검색하기
      //대출가능, 제목, 작가
      //사용자가 입력한 키워드
      
   }
   
   @Test
   public void dynamicQueryForeachTag() {
      //만능 insert쿼리 생성
      
      
   }

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//1. 도서명 : 쿠키와 세션,
	//	 작가 	: 김영아
	//	 도서번호 : 시퀀스 사용
	//	 인 도서를 BOOK 테이블에 저장하기
	//	 메서드 이름 : test01
	@Test
	public void test01() {
		String title = "쿠키와 세션";
		String author = "김영아";
		
		Map<String,String> bookInfo = new HashMap<String, String>();
		bookInfo.put("title", title);
		bookInfo.put("author", author);
		session.insert(NAMESPACE + "test01", bookInfo);
	}
	
	//2. 연장횟수가 2회 이상인 모든 대출도서 정보를
	//	 연장횟수 0회로 초기화 해주세요
	//	 메서드 이름 : test02
	@Test
	public void test02() {
		int extensionCnt = 2;
		session.update(NAMESPACE + "test02",extensionCnt);
	}
	
	//3. 2021년 10월 이전 9월 이후에 가입된 회원정보를 삭제
	//	 메서드 이름 : test03
	@Test
	public void test03() {
		session.delete(NAMESPACE + "test03");
	}
	
	//4. 대출 횟수가 가장 많은 3권의 도서를 조회
	//	 메서드 이름 : test04
	@Test
	public void test04() {
		session.selectList(NAMESPACE + "test04");
	}
}
