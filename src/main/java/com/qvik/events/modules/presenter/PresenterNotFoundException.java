package com.qvik.events.modules.presenter;

public class PresenterNotFoundException extends RuntimeException {
	public PresenterNotFoundException(Long id) {
		super("Could not find presenter " + id);
	}
}
