package com.qvik.events.modules.event;

public class StartDateNotFoundException extends RuntimeException {
	public StartDateNotFoundException(String date) {
		super("Could not find event starts on " + date);
	}

}
