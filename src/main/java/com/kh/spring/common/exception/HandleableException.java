package com.kh.spring.common.exception;

import com.kh.spring.common.code.ErrorCode;

public class HandleableException extends RuntimeException{	//RuntimeException은 catch 등으로 처리해주지 않으면 JVM까지 전달된다. 이를 ExceptionAdvice에서 처리해주고 있음

	private static final long serialVersionUID = 7847451612288681161L;
	
	public ErrorCode error;
	
	//log를 남기지 않고 사용자에게 알림메시지만 전달하는 용도의 생성자
	public HandleableException(ErrorCode error) {
		this.error = error;
		//stackTrace를 비워준다.
		this.setStackTrace(new StackTraceElement[0]);
	}
	
	public HandleableException(ErrorCode error,Exception e) {
		this.error = error;
		e.printStackTrace();
		this.setStackTrace(new StackTraceElement[0]);
	}

}
