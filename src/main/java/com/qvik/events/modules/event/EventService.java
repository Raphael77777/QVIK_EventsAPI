package com.qvik.events.modules.event;

import com.qvik.events.infra.exception.DataNotFoundException;
import com.qvik.events.infra.response.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EventService {

	private final EventRepository eventRepository;
	private final ModelMapper modelMapper;

	@Transactional
	public Map<String, Object> findAllEvents() {
		List<Event> events = eventRepository.findAll();
		return mapEventListToDTOs(events);
	}

	@Transactional
	public Event_DetailsDTO findEventByEventId(Long id) {

		Event event = eventRepository.findEventWithVenueAndStageAndEventPresentersByEventId(id);
		
		if(event == null) {
			throw new DataNotFoundException("Event not found with ID: " + id);
		}

		Event_DetailsDTO details = null;

		if (event.getSubEvents().size() != 0) { 				// root event
			details = modelMapper.map(event, Event_DetailsWithVenueDTO.class);
		} else if (event.getParentEvent() != null) { 			// sub events
			details = modelMapper.map(event, Event_DetailsWithStageDTO.class);
		}

		return details;
	}

	@Transactional
	public StagesDTO findEventStageByEventId(Long id) {

		Event event = eventRepository.findEventWithStageByEventId(id);

		if(event == null) {
			throw new DataNotFoundException("Event not found with ID: " + id);
		}
		StagesDTO stagesDTO = modelMapper.map(event, StagesDTO.class);

		return stagesDTO;
	}

	@Transactional
	public PresentersDTO findEventPresentersByEventId(Long id) {

		Event event = eventRepository.findEventWithEventPresentersByEventId(id);

		if(event == null) {
			throw new DataNotFoundException("Event not found with ID: " + id);
		}
		PresentersDTO presentersDTO = modelMapper.map(event, PresentersDTO.class);

		return presentersDTO;
	}

	@Transactional
	public VenuesDTO findEventVenueByEventId(Long id) {

		Event event = eventRepository.findEventWithVenueByEventId(id);

		if(event == null) {
			throw new DataNotFoundException("Event not found with ID: " + id);
		}
		VenuesDTO venuesDTO = modelMapper.map(event, VenuesDTO.class);

		return venuesDTO;
	}

	@Transactional
	public ExhibitorsDTO findEventExhibitorsByEventId(Long id) {

		Event event = eventRepository.findEventWithEventExhibitorsByEventId(id);

		if(event == null) {
			throw new DataNotFoundException("Event not found with ID: " + id);
		}
		ExhibitorsDTO exhibitorsDTO = modelMapper.map(event, ExhibitorsDTO.class);

		return exhibitorsDTO;
	}

	@Transactional
	public RestaurantsDTO findEventRestaurantsByEventId(Long id) {

		Event event = eventRepository.findEventWithEventRestaurantsByEventId(id);

		if(event == null) {
			throw new DataNotFoundException("Event not found with ID: " + id);
		}
		RestaurantsDTO restaurantsDTO = modelMapper.map(event, RestaurantsDTO.class);

		return restaurantsDTO;
	}

	@Transactional
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
	@Transactional
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
