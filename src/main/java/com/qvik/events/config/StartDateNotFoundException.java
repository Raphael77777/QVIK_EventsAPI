package com.qvik.events.config;

public class StartDateNotFoundException extends RuntimeException {
	public StartDateNotFoundException(String date) {
		super("Could not find event starts on " + date);
	}

}
