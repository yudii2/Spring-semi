package com.kh.spring.schedule.model.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.kh.spring.schedule.model.dto.Schedule;

@Mapper
public interface ScheduleRepository {

	@Select("select * from schedule")
	List<Schedule> selectAllSchedule();

	@Select("select * from schedule where sc_idx = #{scIdx}")
	Schedule selectSchedule(String scIdx);

	List<Map<String,Object>> selectParticipants(String scIdx);

}
