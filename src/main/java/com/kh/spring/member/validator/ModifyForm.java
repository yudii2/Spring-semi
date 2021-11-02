package com.kh.spring.member.validator;

import lombok.Data;

@Data
public class ModifyForm {
	
	private String nickname;
	private String info;
	private String password;
	private String newPw;
	private String newPwConfirm;
}
