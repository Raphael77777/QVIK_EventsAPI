package com.qvik.events.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class PresenterNotFoundAdvice {
	@ResponseBody
	@ExceptionHandler(PresenterNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String presenterNotFoundHandler(PresenterNotFoundException ex) {
		return ex.getMessage();
	}
}
