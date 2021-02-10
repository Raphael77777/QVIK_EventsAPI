package com.qvik.events.modules.event;

public class DatesNotFoundException extends RuntimeException {
	public DatesNotFoundException() {		
			super("Could not find event with given date(s)" );	
		
	}

}
