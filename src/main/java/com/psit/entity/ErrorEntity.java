package com.psit.entity;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ErrorEntity {
	private String message;
	private HttpStatus status;
	private int statusCode;
	private LocalDateTime timeStamp;

}
