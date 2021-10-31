package com.kh.spring.member.validator;

public class JoinForm {	//스프링의 data binding을 위한 dto
							//스프링 이전에는 오류값을 담아주기 위해 session객체를 만들어 전달해줘야 했지만 스프링은 bean에만 등록해두면 알아서 적용해줌
	private String userId;
	private String password;
	private String tell;
	private String email;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTell() {
		return tell;
	}
	public void setTell(String tell) {
		this.tell = tell;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "JoinForm [userId=" + userId + ", password=" + password + ", tell=" + tell + ", email=" + email + "]";
	}
	
	
	
}
