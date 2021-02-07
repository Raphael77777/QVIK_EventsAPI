package com.qvik.events.config;

public class VenueNotFoundException extends RuntimeException {

	public VenueNotFoundException(Long id) {
		super("Could not find venue " + id);
	}
}
