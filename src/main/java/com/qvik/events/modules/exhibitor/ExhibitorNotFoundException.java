package com.qvik.events.config;

public class ExhibitorNotFoundException extends RuntimeException{
	
	public ExhibitorNotFoundException(Long id) {
		super("Could not find exhibitor " + id);
	}
}
