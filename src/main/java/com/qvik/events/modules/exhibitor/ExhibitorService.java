package com.qvik.events.modules.exhibitor;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExhibitorService {
	
	private final ExhibitorRepository exhibitorRepository;
	
	public Exhibitor findExhibitorByExhibitorId(Long id) {
		return exhibitorRepository.findById(id).orElseThrow(() -> new ExhibitorNotFoundException(id));
	}

}
