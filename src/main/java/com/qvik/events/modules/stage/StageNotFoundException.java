package com.qvik.events.config;

public class StageNotFoundException extends RuntimeException {
	
	public StageNotFoundException(Long id) {
		super("Could not find stage " + id);
	}

}
