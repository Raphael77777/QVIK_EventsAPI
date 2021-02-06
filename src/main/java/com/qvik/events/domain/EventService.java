package com.qvik.events.domain;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.qvik.events.config.EventNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {

	private final EventRepository eventRepository;
	
	public Event findEventByEventId(Long id) {
		return eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException(id));
	}
	
}

