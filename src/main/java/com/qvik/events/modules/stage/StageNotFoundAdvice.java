package com.qvik.events.modules.stage;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class StageNotFoundAdvice {
	@ResponseBody
	@ExceptionHandler(StageNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String stageNotFoundHandler(StageNotFoundException ex) {
		return ex.getMessage();
	}

}
