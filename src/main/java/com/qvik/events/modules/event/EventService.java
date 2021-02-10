package com.qvik.events.modules.event;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {

	private final EventRepository eventRepository;

	public Event findEventByEventId(Long id) {
		return eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException(id));
	}

	public List<Event> findEventsByDates(String startDate, String endDate) {

		LocalDate localStartDate = LocalDate.parse(startDate);
		LocalDate localEndDate;
		List<Event> events = null;
		if (endDate != null) {
			localEndDate = LocalDate.parse(endDate);
			events = eventRepository.findByStartDateAndEndDateOrderByStartDateDesc(localStartDate, localEndDate);
		} else {
			events = eventRepository.findByStartDateOrderByStartDateDesc(localStartDate);
		}
		if (events.size() == 0) {
			throw new DatesNotFoundException();
		}
		return events;
	}

	public List<Event> findOnGoingEvents(String date) {
		LocalDate givenDate = LocalDate.parse(date);
		List<Event> events = eventRepository.findAll();
		List<Event> ongoingEvents = new ArrayList<>();
		for (Event e : events) {
			LocalDate startDate = e.getStartDate();
			LocalDate endDate = e.getEndDate();
			if ((givenDate.isEqual(startDate)||givenDate.isAfter(startDate)) && (givenDate.isEqual(endDate)||givenDate.isBefore(endDate))) {
				ongoingEvents.add(e);
			}
		}

		if (ongoingEvents.size() == 0) {
			throw new DatesNotFoundException();
		}

		return ongoingEvents;
	}

}
