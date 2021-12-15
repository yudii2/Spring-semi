package com.kh.spring.schedule.model.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.kh.spring.schedule.model.dto.Schedule;

@Mapper
public interface ScheduleRepository {

	@Select("select * from schedule")
	List<Schedule> selectAllSchedule();

	@Select("select * from schedule where sc_idx = #{scIdx}")
	Schedule selectSchedule(String scIdx);

	List<Map<String,Object>> selectParticipants(String scIdx);

	@Insert("insert into schedule (sc_idx,user_id,d_day,mountain_name,allowed_num,remain_num, info,openchat,age) "
			+ "values(sc_sc_idx.nextval,#{userId},#{dDay},#{mountainName},#{allowedNum},#{allowedNum},#{info},#{openchat},#{age})")
	void insertSchedule(Schedule schedule);

	List<Schedule> selectNonApprovedSchedule();

	@Update("update schedule set status = 1 where sc_idx = #{scIdx}")
	void updateStatusForApprove(String scIdx);
	@Update("update schedule set status = -1 where sc_idx = #{scIdx}")
	void updateStatusForReject(String scIdx);

}
