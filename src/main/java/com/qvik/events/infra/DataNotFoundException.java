package com.qvik.events.infra;

public class DataNotFoundException extends RuntimeException  {
	
	public DataNotFoundException() {
		super("Not found exception");
	}
}
