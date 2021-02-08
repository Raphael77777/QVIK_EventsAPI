package com.qvik.events.modules.restaurant;

public class RestaurantNotFoundException extends RuntimeException  {
	
	public RestaurantNotFoundException(Long id) {
		super("Could not find restaurant " + id);
	}
}
