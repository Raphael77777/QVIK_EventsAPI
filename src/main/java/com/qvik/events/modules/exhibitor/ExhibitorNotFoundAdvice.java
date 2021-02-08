package com.qvik.events.modules.exhibitor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExhibitorNotFoundAdvice {
	@ResponseBody
	@ExceptionHandler(ExhibitorNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String exhibitorNotFoundHandler(ExhibitorNotFoundException ex) {
		return ex.getMessage();
	}
}
