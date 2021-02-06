package com.qvik.events.domain;

import org.springframework.stereotype.Service;

import com.qvik.events.config.ExhibitorNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExhibitorService {
	
	private final ExhibitorRepository exhibitorRepository;
	
	public Exhibitor findExhibitorByExhibitorId(Long id) {
		return exhibitorRepository.findById(id).orElseThrow(() -> new ExhibitorNotFoundException(id));
	}

}
