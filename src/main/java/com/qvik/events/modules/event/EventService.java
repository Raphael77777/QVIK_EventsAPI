package com.qvik.events.modules.event;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.qvik.events.infra.exception.DataNotFoundException;
import com.qvik.events.infra.response.Event_DetailsDTO;
import com.qvik.events.infra.response.Parent_EventDTO;
import com.qvik.events.infra.response.Sub_EventDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EventService {

	private final EventRepository eventRepository;
	private final ModelMapper modelMapper;

	public Map<String, Object> findAllEvents() {
		List<Event> events = eventRepository.findAll();
		return mapEventListToDTOs(events);
	}

	@Transactional
	public Event_DetailsDTO findEventByEventId(Long id) {

		Event event = eventRepository.findEventWithEventVenuesAndEventStagesAndEventPresentersByEventId(id);
		
		if(event == null) {
			throw new DataNotFoundException("Event not found with ID: " + id);
		} 
		Event_DetailsDTO details = modelMapper.map(event, Event_DetailsDTO.class);

		return details;
	}

	public Map<String, Object> findOnGoingEvents(String date) {
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

		return mapEventListToDTOs(ongoingEvents);
	}

	// I DON'T KNOW IF THIS METHOD IS NECESSARY? - 17.02.2021 Tei
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

	/* Map List of Events to DTO */
	public Map<String, Object> mapEventListToDTOs(List<Event> events) {
		Map<String, Object> eventData = new LinkedHashMap<>();
		List<Sub_EventDTO> subevents = new ArrayList<>();
		for (Event e : events) {
			if (e.getSubEvents().size() != 0) { // root event
				Parent_EventDTO parentEvent = modelMapper.map(e, Parent_EventDTO.class);
				eventData.put("parentEvent", parentEvent);
			} else if (e.getParentEvent() != null) { // sub events
				Sub_EventDTO subEvent = modelMapper.map(e, Sub_EventDTO.class);
				subevents.add(subEvent);
			}
			eventData.put("subEvents", subevents);
		}
		return eventData;
	}
}
