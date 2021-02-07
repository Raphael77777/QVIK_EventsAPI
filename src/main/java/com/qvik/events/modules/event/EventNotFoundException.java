package com.qvik.events.config;

public class EventNotFoundException extends RuntimeException {
	
	public EventNotFoundException(Long id) {
		super("Could not find event " + id);
	}

}
