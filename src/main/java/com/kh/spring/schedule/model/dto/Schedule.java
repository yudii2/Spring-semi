package com.kh.spring.schedule.model.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class Schedule {
	
	private String scIdx;
	private String userId;
	private Date dDay;
	private String mountainName;
	private Date regDate;
	private Date expDate;
	private int allowedNum; //모집 인원수
	private int remainNum; // 남은 인원수
	private String info; //모임 상세정보
	private String isDel;
	private int status;
	private String openChat;	
	private int age; //모임 연령대
	private String nickName;
	private String userInfo;
	private String mHeight;
}
