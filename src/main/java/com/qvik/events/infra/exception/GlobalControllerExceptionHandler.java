package com.qvik.events.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.qvik.events.infra.response.ResponseMessage;

@EnableWebMvc
@ControllerAdvice
public class GlobalControllerExceptionHandler {
	/*
	 * 404 NOT FOUND exception handler using DispatcherServlet 
	 */
	@ResponseBody
	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ResponseMessage requestHandlingNoHandlerFound(final NoHandlerFoundException ex) {
		ResponseMessage error = new ResponseMessage(HttpStatus.NOT_FOUND, ex);
		return error;
	}


}
