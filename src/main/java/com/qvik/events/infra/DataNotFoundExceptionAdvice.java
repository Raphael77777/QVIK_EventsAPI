package com.qvik.events.infra;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class DataNotFoundExceptionAdvice {

	@ResponseBody
	@ExceptionHandler(DataNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	ResponseMessage dataNotFoundHandler(DataNotFoundException ex) {
		ResponseMessage error = new ResponseMessage(HttpStatus.NOT_FOUND, ex);
		return error;
	}

}
