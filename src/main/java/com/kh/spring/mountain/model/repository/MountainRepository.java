package com.kh.spring.mountain.model.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.kh.spring.common.util.PageDTO;
import com.kh.spring.mountain.model.dto.Mountain;

@Mapper
public interface MountainRepository {
	
	@Select("select count(*) from mountain where m_loc like '서울%'")
	int countSeoulMountain();
	@Select("select count(*) from mountain where m_loc like '경기%'")
	int countGyeonggiMountain();

	@Select("select count(*) from mountain where m_loc like '서울%'")
	List<Mountain> selectAllMountain();
	
	List<Mountain> selectSeoulMountainByPage(@Param("page") PageDTO page);
	List<Mountain> selectGyeonggiMountainByPage(@Param("page") PageDTO page);
	
	@Select("select * from mountain where m_name = #{mName}")
	Mountain selectMountainByName(String mName);




	

}
