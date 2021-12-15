package com.kh.spring.schedule.model.service;

import java.util.List;
import java.util.Map;

import com.kh.spring.member.model.dto.Member;
import com.kh.spring.schedule.model.dto.Schedule;

public interface ScheduleService {

	List<Schedule> selectAllSchedule();

	Map<String, Object> selectScheduleDetail(String scIdx, Member member);

	void insertSchedule(Schedule schedule);

	List<Schedule> selectNonApprovedSchdule();

	void approveSchedule(String scIdx);

	void rejectSchedule(String scIdx);

}
