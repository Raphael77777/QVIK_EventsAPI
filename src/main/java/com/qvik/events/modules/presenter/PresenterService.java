package com.qvik.events.domain;

import org.springframework.stereotype.Service;

import com.qvik.events.config.PresenterNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PresenterService {

	private final PresenterRepository presenterRepository;
	
	public Presenter findPresenterByPresenterId(Long id) {
		return presenterRepository.findById(id).orElseThrow(() -> new PresenterNotFoundException(id));
	}
}
