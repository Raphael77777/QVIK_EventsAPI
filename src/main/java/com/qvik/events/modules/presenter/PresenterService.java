package com.qvik.events.modules.presenter;
import org.springframework.stereotype.Service;

import com.qvik.events.infra.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PresenterService {

	private final PresenterRepository presenterRepository;
	
	public Presenter findPresenterByPresenterId(Long id) {
		return presenterRepository.findById(id).orElseThrow(() -> new DataNotFoundException());
	}
}
