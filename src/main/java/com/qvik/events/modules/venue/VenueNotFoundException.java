package com.qvik.events.modules.venue;

public class VenueNotFoundException extends RuntimeException {

	public VenueNotFoundException(Long id) {
		super("Could not find venue " + id);
	}
}
