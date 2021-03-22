package com.qvik.events.modules.venue;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qvik.events.infra.exception.DataNotFoundException;
import com.qvik.events.infra.response.dto.Venue_DetailsDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class VenueService {

	private final VenueRepository venueRepository;
	private final ModelMapper modelMapper;

	public List<Venue_DetailsDTO> findAllVenues() {
		// Variables
		Map<String, Object> finalAllVenues = new LinkedHashMap<>();// for return object
		List<Venue_DetailsDTO> venueDTOList = new ArrayList<>(); // for DTO list

		// Find All Venues from the DB
		List<Venue> venues = venueRepository.findAll();
		// Iterate All Restaurants list
		for (Venue v : venues) {
			Venue_DetailsDTO dto = modelMapper.map(v, Venue_DetailsDTO.class);
			venueDTOList.add(dto);
		}

		return venueDTOList;

	}

	public Venue findVenueByVenueId(Long id) {

		return venueRepository.findById(id)
				.orElseThrow(() -> new DataNotFoundException("Presenter not found with ID: " + id));
	}

}
