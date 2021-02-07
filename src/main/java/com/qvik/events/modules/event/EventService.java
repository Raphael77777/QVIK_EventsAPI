package com.qvik.events.domain;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.qvik.events.config.EventNotFoundException;
import com.qvik.events.config.StartDateNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {

	private final EventRepository eventRepository;

	public Event findEventByEventId(Long id) {
		return eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException(id));
	}

	public List<Event> findEventsByStartDate(String date) {

		LocalDate startDate = LocalDate.parse(date);
		List<Event> events = eventRepository.findByStartDateOrderByStartDateDesc(startDate);
		if (events.size() == 0) {
			throw new StartDateNotFoundException(date);
		}

		return events;

	}

}
