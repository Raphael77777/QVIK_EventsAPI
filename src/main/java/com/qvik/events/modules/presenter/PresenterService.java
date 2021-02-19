package com.qvik.events.modules.presenter;
import org.springframework.stereotype.Service;

import com.qvik.events.infra.exception.DataNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PresenterService {

	private final PresenterRepository presenterRepository;

	@Transactional
	public Presenter findPresenterByPresenterId(Long id) {
		return presenterRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Presenter not found with ID: " + id));
	}
}
