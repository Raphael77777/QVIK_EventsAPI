package com.qvik.events.infra;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;

@Getter 
@JsonInclude(Include.NON_NULL)
public class ResponseMessage {

	private int status; 
	private String message;
	private Object data;
	private String description;

	public ResponseMessage() {
		super();
	}

	public ResponseMessage(HttpStatus httpStatus) {
		super();
		this.status = httpStatus.value();
		this.message = httpStatus.getReasonPhrase();

	}
	
	public ResponseMessage(HttpStatus httpStatus, Exception e) {
		super();
		this.status = httpStatus.value();
		this.message = e.getMessage();
	}

	public void add(Object object) {
		this.data = object;
	}

}
