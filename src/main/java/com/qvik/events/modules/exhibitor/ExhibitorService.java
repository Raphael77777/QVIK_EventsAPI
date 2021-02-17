package com.qvik.events.modules.exhibitor;

import org.springframework.stereotype.Service;

import com.qvik.events.infra.exception.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExhibitorService {
	
	private final ExhibitorRepository exhibitorRepository;
	
	public Exhibitor findExhibitorByExhibitorId(Long id) {
		return exhibitorRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Exhibitor not found with ID: " + id));
	}

}
