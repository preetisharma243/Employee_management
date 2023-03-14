package com.psit.exception;

import org.springframework.web.bind.annotation.RestControllerAdvice;


public class InvalidFileException extends RuntimeException {
	
	public InvalidFileException() {
		// TODO Auto-generated constructor stub
	}
	 
	public InvalidFileException(String msg) {
		super(msg);
	}	

}
