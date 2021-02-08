package com.qvik.events.modules.venue;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VenueService {
	
	private final VenueRepository venueRepository;
	
	public Venue findVenueByVenueId(Long id) {
		return venueRepository.findById(id).orElseThrow(() -> new VenueNotFoundException(id));
	}

}
