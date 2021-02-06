package com.qvik.events.domain;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {

	private final EventRepository eventRepository;

	public Optional<Event> findEventByEventId(Long eventId) {

		Optional<Event> foundEvent = eventRepository.findById(eventId);
		if (!foundEvent.isPresent()) {

			throw new IllegalArgumentException(); // TODO: EXCEPTION HANDLER
		}
		return foundEvent;
	}
}
