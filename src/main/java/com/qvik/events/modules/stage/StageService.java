package com.qvik.events.domain;

import org.springframework.stereotype.Service;

import com.qvik.events.config.StageNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StageService {
	
	private final StageRepository stageRepository;
	
	public Stage findStageByStaageId(Long id) {
		return stageRepository.findById(id).orElseThrow(() -> new StageNotFoundException(id));
	}

}
