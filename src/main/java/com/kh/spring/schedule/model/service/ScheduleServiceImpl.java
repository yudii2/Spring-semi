package com.kh.spring.schedule.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kh.spring.schedule.model.dto.Schedule;
import com.kh.spring.schedule.model.repository.ScheduleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService{
	
	private final ScheduleRepository scheduleRepository;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public List<Schedule> selectAllSchedule() {
		return scheduleRepository.selectAllSchedule();
	}

	@Override
	public Map<String, Object> selectScheduleDetail(String scIdx) {
		List<Map<String,Object>> participants = new ArrayList<Map<String,Object>>();
		
		Schedule scheduleByIdx = scheduleRepository.selectSchedule(scIdx);
		participants = scheduleRepository.selectParticipants(scIdx);
				
		return Map.of("participants", participants, "schedule", scheduleByIdx);
	}

}
