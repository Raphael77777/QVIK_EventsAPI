package com.qvik.events.modules.event;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class StartDateNotFoundAdvice {
	@ResponseBody
	@ExceptionHandler(StartDateNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String startDateNotFoundHandler(StartDateNotFoundException ex) {
		return ex.getMessage();
	}

}
