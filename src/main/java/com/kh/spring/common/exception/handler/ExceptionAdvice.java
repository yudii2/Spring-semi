package com.kh.spring.common.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.kh.spring.common.exception.HandleableException;	//HandleableException은 RuntimeException을 던지고 있어, 컨트롤러까지 전달된다. 컨트롤러부터 발생한 예외가 확인되면 여기서 처리


@Component
@ControllerAdvice(basePackages = "com.kh.spring")		//컨트롤러들의 Advice(공통관심사)
public class ExceptionAdvice {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//예외가 발생했으므로 응답상태코드를 500번으로 지정, default는 200임.(서블릿은 자동500처리했었음)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(HandleableException.class)	//HandleableException(우리가 예외로 처리한 사례)에 대해서만 handler호출
	public String handleableExceptionProcess(HandleableException e,Model model) {	//발생한 예외객체와 model객체 주입
		model.addAttribute("msg",e.error.MSG);
		model.addAttribute("url",e.error.URL);
		
		return "common/result";
	}
	
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(DataAccessException.class)
	public String dataAccessExceptionProcess(DataAccessException e,Model model) {
		e.printStackTrace();
		model.addAttribute("msg", "데이터베이스 접근 중에 예외가 발생하였습니다.");
		model.addAttribute("url","/");
		
		return "common/result";
	}
}


