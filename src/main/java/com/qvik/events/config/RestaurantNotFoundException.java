package com.qvik.events.config;

public class RestaurantNotFoundException extends RuntimeException  {
	
	public RestaurantNotFoundException(Long id) {
		super("Could not find restaurant " + id);
	}
}
