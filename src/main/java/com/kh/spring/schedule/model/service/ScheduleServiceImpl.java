package com.kh.spring.schedule.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kh.spring.schedule.model.dto.Schedule;
import com.kh.spring.schedule.model.repository.ScheduleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService{
	
	private final ScheduleRepository scheduleRepository;

	@Override
	public List<Schedule> selectAllSchedule() {
		return scheduleRepository.selectAllSchedule();
	}

	@Override
	public Map<String, Object> selectScheduleDetail(Schedule schedule) {
		// TODO Auto-generated method stub
		return null;
	}

}
