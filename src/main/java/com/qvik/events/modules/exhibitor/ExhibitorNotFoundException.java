package com.qvik.events.modules.exhibitor;

public class ExhibitorNotFoundException extends RuntimeException{
	
	public ExhibitorNotFoundException(Long id) {
		super("Could not find exhibitor " + id);
	}
}
