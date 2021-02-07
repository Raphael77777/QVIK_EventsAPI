package com.qvik.events.domain;

import java.time.LocalDate;
import java.util.List;

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

	public List<Event> findEventsByStartDate(String date) {
		
		System.out.println("URL: " + date);
		LocalDate startDate = LocalDate.parse(date);
		System.out.println("Parsed: " + startDate);
		List<Event> events = eventRepository.findByStartDateOrderByStartDateDesc(startDate);
		System.out.println("Length: " + events.size());
		System.out.println("----");
		for(Event e : events) {
			
			System.out.println(e.getEvent_id() + " at : " + e.getStartDate());
		}
		return events;
		
	}
	
}

