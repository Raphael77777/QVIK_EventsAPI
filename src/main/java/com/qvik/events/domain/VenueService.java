package com.qvik.events.domain;

import org.springframework.stereotype.Service;

import com.qvik.events.config.VenueNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VenueService {
	
	private final VenueRepository venueRepository;
	
	public Venue findVenueByVenueId(Long id) {
		return venueRepository.findById(id).orElseThrow(() -> new VenueNotFoundException(id));
	}

}
