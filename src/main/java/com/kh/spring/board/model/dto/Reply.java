package com.kh.spring.board.model.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class Reply {
	
	private String rpIdx;
	private String bdIdx;
	private String userId;
	private String content;
	private String codeIdx;
	private Date regDate;
	private String profileSavePath;
	private String profileRenameFileName;
	private String nickname;
}
