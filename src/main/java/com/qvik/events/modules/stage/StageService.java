package com.qvik.events.modules.stage;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StageService {
	
	private final StageRepository stageRepository;
	
	public Stage findStageByStaageId(Long id) {
		return stageRepository.findById(id).orElseThrow(() -> new StageNotFoundException(id));
	}

}
