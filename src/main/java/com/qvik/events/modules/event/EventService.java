package com.qvik.events.modules.event;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qvik.events.infra.exception.DataNotFoundException;
import com.qvik.events.infra.response.Event_DetailsDTO;
import com.qvik.events.infra.response.ExhibitorsDTO;
import com.qvik.events.infra.response.Init_SettingDTO;
import com.qvik.events.infra.response.ParentEvent_DetailsDTO;
import com.qvik.events.infra.response.Parent_EventDTO;
import com.qvik.events.infra.response.PresenterDTO;
import com.qvik.events.infra.response.PresentersDTO;
import com.qvik.events.infra.response.RestaurantDTO;
import com.qvik.events.infra.response.RestaurantsDTO;
import com.qvik.events.infra.response.StagesDTO;
import com.qvik.events.infra.response.SubEvent_DetailsDTO;
import com.qvik.events.infra.response.Sub_EventDTO;
import com.qvik.events.infra.response.TagsDTO;
import com.qvik.events.infra.response.VenuesDTO;
import com.qvik.events.modules.presenter.Event_Presenter;
import com.qvik.events.modules.restaurant.Event_Restaurant;
import com.qvik.events.modules.restaurant.Restaurant;
import com.qvik.events.modules.restaurant.RestaurantRepository;
import com.qvik.events.modules.tag.Event_Tag;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EventService {

	private final EventRepository eventRepository;
	private final RestaurantRepository restaurantRepository;
	private final ModelMapper modelMapper;

	/*
	 * Data to be used for header setup  
	 */
	@Transactional
	public Init_SettingDTO findInitialSetUpData() {
		Event event = eventRepository.findEventByParentEvent(null);
		Init_SettingDTO initData = modelMapper.map(event, Init_SettingDTO.class);
		
		initData.setEventImage(event.getImage());
		initData.setRestaurantImage("https://qvik-event-platform.s3-eu-west-1.amazonaws.com/restaurant_cover.jpg");
		

		/* ADD TAGS */
		List <String> tags = new ArrayList<>();
		event.getEventTags().forEach(et -> tags.add(et.getTag().getName()));
		initData.setEventTags(tags);

		/* ADD ALL TAGS from both parent and sub events*/
		List <String> allTags = new ArrayList<>();
		event.getEventTags().forEach(et -> allTags.add(et.getTag().getName()));
		event.getSubEvents().forEach(subEvent -> subEvent.getEventTags().forEach(et -> allTags.add(et.getTag().getName())));
		
		List<String> allTagsWithoutDuplicate = removeDuplicates(allTags);
		initData.setAllEventTags(allTagsWithoutDuplicate);

		/* ADD VENUE */
		initData.setVenue(event.getVenue().getName());
		
		/* ADD RESTAURANT TAGS*/
		List<Restaurant> restaurants = restaurantRepository.findAll();
		List<String> restaurantTags = new ArrayList<>();
		restaurants.forEach(r -> r.getRestaurantTags().forEach(tag -> restaurantTags.add(tag.getTag().getName())));
		
		List<String> allRestaurantTagsWithoutDeuplicate = removeDuplicates(restaurantTags);
		initData.setAllRestaurantTags(allRestaurantTagsWithoutDeuplicate);
		
		
		return initData;
	}
	
	
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

		Event_DetailsDTO details = null;

		if (event.getSubEvents().size() != 0){ // parent event
			details = modelMapper.map(event, ParentEvent_DetailsDTO.class);
			List<String> subTags = new ArrayList<>();
			for (Event e : event.getSubEvents()){
				List<Event_Tag> subEventTags  = e.getEventTags();
				subEventTags.forEach( t -> subTags.add(t.getTag().getName()));
			}
			List<Event_Tag> eventTags  = event.getEventTags();
			eventTags.forEach( t -> subTags.add(t.getTag().getName()));
			((ParentEvent_DetailsDTO) details).setAllTags(removeDuplicates(subTags));
		}else if (event.getParentEvent() != null){ // subEvent
			details = modelMapper.map(event, SubEvent_DetailsDTO.class);
			Event parentEvent = event.getParentEvent();
			List<Event_Tag> parentEventTags  = parentEvent.getEventTags();
			List<String> inheritedTags = new ArrayList<>();
			parentEventTags.forEach( t -> inheritedTags.add(t.getTag().getName()));
			((SubEvent_DetailsDTO) details).setInheritedTags(inheritedTags);

			/* ADD RESTAURANTS */
			List<RestaurantDTO> restaurants = new ArrayList<>();
			List<Event_Restaurant> eventRestaurants = event.getEventRestaurants();
			eventRestaurants.forEach( er -> restaurants.add(modelMapper.map(er.getRestaurant(), RestaurantDTO.class)));
			((SubEvent_DetailsDTO) details).setRestaurants(restaurants);
		}

		if (details == null){
			details = modelMapper.map(event, Event_DetailsDTO.class);
		}

		/* ADD PRESENTERS */
		List<PresenterDTO> presenters = new ArrayList<>();
		List<Event_Presenter> eventPresesnters = event.getEventPresenters();
		eventPresesnters.forEach( ep -> presenters.add(modelMapper.map(ep.getPresenter(), PresenterDTO.class)));
		details.setPresenters(presenters);

		/* ADD TAGS */
		List<String> tags = new ArrayList<>();
		List<Event_Tag> eventTags  = event.getEventTags();
		eventTags.forEach( t -> tags.add(t.getTag().getName()));
		details.setEventTags(tags);

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
				parentEvent.setEventTags(tags);

				/* ADD ALL TAGS */
				List <String> allTags = new ArrayList<>();
				allTags.addAll(tags);
				parentEvent.setAllEventTags(allTags);

				/* ADD VENUE */
				parentEvent.setVenue(e.getVenue().getName());
				
			} else if (e.getParentEvent() != null) { // sub events
				Sub_EventDTO subEvent = modelMapper.map(e, Sub_EventDTO.class);

				/* ADD TAGS */
				List <String> tags = new ArrayList<>();
				e.getEventTags().forEach(et -> tags.add(et.getTag().getName()));
				subEvent.setEventTags(tags);
				parentEvent.getAllEventTags().addAll(tags);

				/* ADD INHERITED TAGS */
				List<Event_Tag> parentEventTags  = e.getParentEvent().getEventTags();
				List<String> inheritedTags = new ArrayList<>();
				parentEventTags.forEach( t -> inheritedTags.add(t.getTag().getName()));
				subEvent.setInheritedTags(inheritedTags);

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
					subeventsMap.put("data", subevents);

				}else {
					List<Sub_EventDTO> subevents = (List<Sub_EventDTO>) subeventsMap.get("data");
					subevents.add(subEvent);
					subeventsMap.put("data", subevents);
				}

				subMapOfMap.put(date, subeventsMap);
			}
		}

		List<String> allTags = parentEvent.getAllEventTags();
		parentEvent.setAllEventTags(removeDuplicates(allTags));

		eventData.put("parentEvent", parentEvent);

		/* Sort sub events by dates in chronological order */
		subMapOfMapKey.sort(Comparator.naturalOrder());
		for (LocalDate ld : subMapOfMapKey){
			subListOfMap.add(subMapOfMap.get(ld.toString()));
		}
		eventData.put("subEvents", subListOfMap);

		return eventData;
	}

	private List<String> removeDuplicates (List<String> list){
		Set<String> set = new LinkedHashSet<>(list);
		list.clear();
		list.addAll(set);
		return list;
	}

	
}