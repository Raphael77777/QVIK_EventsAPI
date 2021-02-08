package com.qvik.events.modules.event;

public class EventNotFoundException extends RuntimeException {
	
	public EventNotFoundException(Long id) {
		super("Could not find event " + id);
	}

}
