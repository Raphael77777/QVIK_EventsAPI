package com.qvik.events.infra;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.rest.webmvc.support.ExceptionMessage;
import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ResponseMessage {

	private int code;
	private String message;
	private Object data;

	public ResponseMessage() {
		super();
	}

	public ResponseMessage(HttpStatus httpStatus) {
		super();
		this.code = httpStatus.value();
		this.message = httpStatus.getReasonPhrase();

	}

	public void add(Object object) {
		this.data = object;
	}

}
