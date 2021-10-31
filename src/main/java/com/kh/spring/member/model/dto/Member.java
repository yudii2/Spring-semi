package com.kh.spring.member.model.dto;

import java.sql.Date;

import com.kh.spring.common.util.FileDTO;

import lombok.Data;

@Data
public class Member {   
   
	private String userId;
	private String password;
	private Date birth;
	private String info;
	private String nickname;
	private Date joinDate;
	private String email;
	private String grade;
	private int isHost;
	private int isLeave;
	private int postCnt;
	private int replyCnt;
	private String profile;
	

	public void setProfile(FileDTO profile) {
		if(profile.getSavePath() == null) {
			this.profile = null;
		}else {
			this.profile = profile.getSavePath() + profile.getRenameFileName();

		}
	}
   
}