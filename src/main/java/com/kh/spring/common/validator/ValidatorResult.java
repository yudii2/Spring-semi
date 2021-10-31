package com.kh.spring.common.validator;

import java.util.HashMap;
import java.util.Map;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

public class ValidatorResult {			//공통모듈로 만들어 컨트롤러 코드를 줄이기 위함

	private Map<String,Object> error;	
	
	public ValidatorResult() {
		error = new HashMap<>();
	}

	public void addErrors(Errors errors) {
		for (FieldError fieldError : errors.getFieldErrors()) {					//jstl을 사용할 때 맵의 key값으로 value를 조회할 수 있으므로 key에 field변수명을 담은 map을 만들어 컨트롤러에 전달
			error.put(fieldError.getField(), fieldError.getDefaultMessage());	//value값으로 에러메시지를 저장
		}
	}
	
	public Map<String,Object> getError(){
		return error;
	}
}
