package com.qvik.events.modules.stage;

public class StageNotFoundException extends RuntimeException {
	
	public StageNotFoundException(Long id) {
		super("Could not find stage " + id);
	}

}
