package com.qvik.events.modules.venue;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import com.qvik.events.infra.exception.DataNotFoundException;

@Service
@RequiredArgsConstructor
public class VenueService {
	
	private final VenueRepository venueRepository;

	@Transactional
	public Venue findVenueByVenueId(Long id) {
		return venueRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Presenter not found with ID: " + id));
	}

}
