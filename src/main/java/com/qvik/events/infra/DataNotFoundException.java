package com.qvik.events.infra;

public class DataNotFoundException extends RuntimeException  {
	
	public DataNotFoundException(String msg) {
		super(msg);
	}
}
