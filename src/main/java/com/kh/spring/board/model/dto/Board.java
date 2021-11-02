package com.kh.spring.board.model.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class Board {
	
	private String bdIdx;
	private String userId;
	private String nickname;
	private String title;
	private String subject;
	private String content;
	private Date regDate;
	private int viewCnt;
	private String profileSavePath;
	private String profileRenameFileName;

}
