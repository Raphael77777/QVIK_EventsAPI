package com.qvik.events.modules.event;

import java.time.LocalDate;
import java.util.*;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qvik.events.infra.exception.DataNotFoundException;
import com.qvik.events.infra.response.Event_DetailsDTO;
import com.qvik.events.infra.response.Event_DetailsWithStageDTO;
import com.qvik.events.infra.response.Event_DetailsWithVenueDTO;
import com.qvik.events.infra.response.ExhibitorsDTO;
import com.qvik.events.infra.response.Parent_EventDTO;
import com.qvik.events.infra.response.PresentersDTO;
import com.qvik.events.infra.response.RestaurantsDTO;
import com.qvik.events.infra.response.StagesDTO;
import com.qvik.events.infra.response.Sub_EventDTO;
import com.qvik.events.infra.response.TagsDTO;
import com.qvik.events.infra.response.VenuesDTO;
import com.qvik.events.modules.presenter.Event_Presenter;
import com.qvik.events.modules.tag.Event_Tag;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
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

		Event event = eventRepository.findEventWithEventPresentersByEventId(id);
		
		if(event == null) {
			throw new DataNotFoundException("Event not found with ID: " + id);
		}

		Event_DetailsDTO details = modelMapper.map(event, Event_DetailsDTO.class);
		List<String> presenters = new ArrayList<>();
		List<Event_Presenter> eventPresenters  = event.getEventPresenters();
		eventPresenters.forEach( p -> presenters.add(p.getPresenter().getName()));
		details.setPresenters(presenters);
	
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
	public TagsDTO findEventTagsByEventId(Long id) {

		Event event = eventRepository.findEventWithEventTagsByEventId(id);

		if(event == null) {
			throw new DataNotFoundException("Event not found with ID: " + id);
		}
		TagsDTO tagsDTO = modelMapper.map(event, TagsDTO.class);

		return tagsDTO;
	}

	
	public Map<String, Object> findEventsByTags(String tagName) {

		List<Event> events = eventRepository.findAll();
		List<Event> eventsWithTag = new ArrayList<>();

		for (Event e : events){
			Event event = eventRepository.findEventWithEventTagsByEventId(e.getEventId());
			List<Event_Tag> event_tags = event.getEventTags();

			for (Event_Tag et : event_tags){
				if (et.getTag().getName().equals(tagName)){

					if (e.getSubEvents().size() != 0){ // root event
						return mapEventListToDTOs(events);
					}else if (e.getParentEvent() != null) { // sub events
						eventsWithTag.add(e);
					}
				}
			}
		}

		if(eventsWithTag.size() == 0) {
			throw new DataNotFoundException("Events not found with tag: " + tagName);
		}

		return mapEventListToDTOs(eventsWithTag);
	}

	/*THIS METHOD WILL NOT BE IN USE -> TO BE DELETED LATER 27.02.2027 - TEI*/
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

	/* Map List of Events to DTO */
	public Map<String, Object> mapEventListToDTOs(List<Event> events) {
		Map<String, Object> eventData = new LinkedHashMap<>();

		Map<String, Map<String, Object>> subMapOfMap = new LinkedHashMap<>();;
		List<LocalDate> subMapOfMapKey = new ArrayList<>();
		List<Map<String, Object>> subListOfMap = new ArrayList<>();

		Parent_EventDTO parentEvent = null;
		
		for (Event e : events) {
			if (e.getSubEvents().size() != 0) { // parent event
				parentEvent = modelMapper.map(e, Parent_EventDTO.class);

				/* ADD TAGS */
				List <String> tags = new ArrayList<>();
				e.getEventTags().forEach(et -> tags.add(et.getTag().getName()));
				parentEvent.setTags(tags);

				/* ADD VENUE */
				parentEvent.setVenue(e.getVenue().getName());
				
			} else if (e.getParentEvent() != null) { // sub events
				Sub_EventDTO subEvent = modelMapper.map(e, Sub_EventDTO.class);

				/* ADD TAGS */
				List <String> tags = new ArrayList<>();
				e.getEventTags().forEach(et -> tags.add(et.getTag().getName()));
				subEvent.setTags(tags);
				parentEvent.getTags().addAll(tags);

				/* ADD PRESENTERS */
				List <String> presenters = new ArrayList<>();
				e.getEventPresenters().forEach(et -> presenters.add(et.getPresenter().getName()));
				subEvent.setPresenters(presenters);

				/* ADD STAGE */
				subEvent.setStage(e.getStage().getName());

				/* SUB EVENTS WILL PRESENTED BY 'DATE' */
				String date = subEvent.getStartDate().toString();
				Map<String, Object> subeventsMap = subMapOfMap.get(date);
				if (subeventsMap == null){
					subeventsMap = new LinkedHashMap<>();
					subeventsMap.put("dateAsTitle", date);
					subMapOfMapKey.add(subEvent.getStartDate());

					List<Sub_EventDTO> subevents = new ArrayList<>();
					subevents.add(subEvent);
					subeventsMap.put("events", subevents);

				}else {
					List<Sub_EventDTO> subevents = (List<Sub_EventDTO>) subeventsMap.get("events");
					subevents.add(subEvent);
					subeventsMap.put("events", subevents);
				}

				subMapOfMap.put(date, subeventsMap);
			}
		}

		eventData.put("parentEvent", parentEvent);

		/* Sort sub events by dates in chronological order */
		subMapOfMapKey.sort(Comparator.naturalOrder());
		for (LocalDate ld : subMapOfMapKey){
			subListOfMap.add(subMapOfMap.get(ld.toString()));
		}
		eventData.put("subEvents", subListOfMap);

		return eventData;
	}
}