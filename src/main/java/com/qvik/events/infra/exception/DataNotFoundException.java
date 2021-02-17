package com.qvik.events.infra.exception;

public class DataNotFoundException extends RuntimeException  {
	
	public DataNotFoundException(String msg) {
		super(msg);
	}
}
