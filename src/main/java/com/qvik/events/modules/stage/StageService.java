package com.qvik.events.modules.stage;

import org.springframework.stereotype.Service;

import com.qvik.events.infra.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StageService {
	
	private final StageRepository stageRepository;
	
	public Stage findStageByStageId(Long id) {
		return stageRepository.findById(id).orElseThrow(() -> new DataNotFoundException());
	}

}
