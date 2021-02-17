package com.qvik.events.modules.event;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.qvik.events.infra.exception.DataNotFoundException;
import com.qvik.events.infra.response.Event_DetailsDTO;
import com.qvik.events.infra.response.Parent_EventDTO;
import com.qvik.events.infra.response.Sub_EventDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {

	private final EventRepository eventRepository;
	private final ModelMapper modelMapper;

	/*
	 * TO DO: TEST & REFACTORING
	 */
	public Map<String, Object> findAllEvents() {
		List<Event> events = eventRepository.findAll();
		Map<String, Object> eventData = new LinkedHashMap<>();
		List<Sub_EventDTO> subevents = new ArrayList<>();
		for (Event e : events) {
			if (e.getSubEvents().size() != 0) { // root event

				Parent_EventDTO parentEvent = modelMapper.map(e, Parent_EventDTO.class);

				eventData.put("Parent Event", parentEvent);

			} else if (e.getParentEvent() != null) { // sub events

				Sub_EventDTO subEvent = modelMapper.map(e, Sub_EventDTO.class);
				subevents.add(subEvent);

			}
			eventData.put("Sub events", subevents);

		}

		return eventData;
	}

	/*
	 * TO DO: TEST & REFACTORING
	 */
	public Event_DetailsDTO findEventByEventId(Long id) {
		Optional<Event> event = eventRepository.findById(id);
		Event_DetailsDTO details = null;

		if (event.isPresent()) {
			details = modelMapper.map(event.get(), Event_DetailsDTO.class);

		}
		event.orElseThrow(() -> new DataNotFoundException("Event not found with ID: " + id));

		return details;
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
			throw new DataNotFoundException("Data not found with given date(s)");
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
			if ((givenDate.isEqual(startDate) || givenDate.isAfter(startDate))
					&& (givenDate.isEqual(endDate) || givenDate.isBefore(endDate))) {
				ongoingEvents.add(e);
			}
		}

		if (ongoingEvents.size() == 0) {
			throw new DataNotFoundException("Event not found with given date: " + date);
		}

		return ongoingEvents;
	}

}
