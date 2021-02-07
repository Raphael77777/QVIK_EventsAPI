package com.qvik.events.config;

public class PresenterNotFoundException extends RuntimeException {
	public PresenterNotFoundException(Long id) {
		super("Could not find presenter " + id);
	}
}
