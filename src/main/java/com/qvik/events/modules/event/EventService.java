package com.qvik.events.modules.event;

import com.qvik.events.infra.exception.DataNotFoundException;
import com.qvik.events.infra.response.dto.*;
import com.qvik.events.modules.presenter.Event_Presenter;
import com.qvik.events.modules.restaurant.Event_Restaurant;
import com.qvik.events.modules.restaurant.Restaurant;
import com.qvik.events.modules.restaurant.RestaurantRepository;
import com.qvik.events.modules.tag.Event_Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

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
	public Init_SettingDTO findInitialSetUpData() {
		Event event = eventRepository.findEventByIsMainEventTrue();
		Init_SettingDTO initData = modelMapper.map(event, Init_SettingDTO.class);

		/* ADD TAGS */
		List<String> tags = new ArrayList<>();
		event.getEventTags().forEach(et -> tags.add(et.getTag().getName()));
		initData.setEventTags(tags);

		/* ADD ALL TAGS from both parent and sub events */
		List<String> allTags = new ArrayList<>();
		event.getEventTags().forEach(et -> allTags.add(et.getTag().getName()));
		event.getSubEvents()
				.forEach(subEvent -> subEvent.getEventTags().forEach(et -> allTags.add(et.getTag().getName())));

		List<String> allTagsWithoutDuplicate = removeDuplicates(allTags);
		initData.setAllEventTags(allTagsWithoutDuplicate);

		/* ADD VENUE */
		if (event.getVenue() != null) {
			initData.setVenue(event.getVenue().getName());
		}

		/* ADD RESTAURANT TAGS */
		List<Restaurant> restaurants = restaurantRepository.findAll();
		List<String> restaurantCuisines = new ArrayList<>();
		restaurants.forEach(r -> r.getRestaurantCuisines()
				.forEach(cuisine -> restaurantCuisines.add(cuisine.getCuisine().getName())));

		List<String> allRestaurantCuisinesWithoutDuplicate = removeDuplicates(restaurantCuisines);
		initData.setAllRestaurantCuisines(allRestaurantCuisinesWithoutDuplicate);

		return initData;
	}

	/*
	 * API /events to return all information related to events
	 * 
	 */
	public List<Map<String, Object>> findAllEvents(String groupBy) {
		List<Event> events = eventRepository.findAll();
		return mapEventListToDTOs(events, groupBy);
	}

	/* NEW - Map List of Events to DTO */
	public List<Map<String, Object>> mapEventListToDTOs(List<Event> events, String groupBy) {

		Map<String, Map<String, Object>> mapOfMap = new LinkedHashMap<>();
		List<LocalDate> mapKeys = new ArrayList<>();
		List<String> mapKeysP = new ArrayList<>();
		List<Map<String, Object>> listOfMap = new ArrayList<>();

		for (Event e : events) {
			if (!e.isMainEvent()) { // sub events
				Event_DetailsDTO event_detailsDTO = modelMapper.map(e, Event_DetailsDTO.class);

				/* ADD PRESENTERS */
				List<Event_Presenter> eventPresenters = e.getEventPresenters();
				if (eventPresenters != null || eventPresenters.size() != 0) {
					List<PresenterDTO> presenters = new ArrayList<>();
					eventPresenters
							.forEach(er -> presenters.add(modelMapper.map(er.getPresenter(), PresenterDTO.class)));
					event_detailsDTO.setPresenters(presenters);
				}

				/* ADD INHERITED TAGS */
				Event parentEvent = e.getParentEvent();
				if (parentEvent != null) {
					List<Event_Tag> parentEventTags = parentEvent.getEventTags();
					if (parentEventTags != null || parentEventTags.size() != 0) {
						List<String> inheritedTags = new ArrayList<>();
						parentEventTags.forEach(t -> inheritedTags.add(t.getTag().getName()));
						event_detailsDTO.setInheritedTags(inheritedTags);
					}
				}

				/* ADD EVENT TAGS */
				List<Event_Tag> eventTags = e.getEventTags();
				if (eventTags != null || eventTags.size() != 0) {
					List<String> tags = new ArrayList<>();
					eventTags.forEach(et -> tags.add(et.getTag().getName()));
					event_detailsDTO.setEventTags(tags);
				}

				/* ADD RESTAURANTS */
				List<Event_Restaurant> eventRestaurants = e.getEventRestaurants();
				if (eventRestaurants != null || eventRestaurants.size() != 0) {
					List<RestaurantDTO> restaurants = new ArrayList<>();
					eventRestaurants
							.forEach(er -> restaurants.add(modelMapper.map(er.getRestaurant(), RestaurantDTO.class)));
					event_detailsDTO.setRestaurants(restaurants);
				}

				switch (groupBy) {
				case "DATE":
					/* SUB EVENTS WILL BE GROUPED BY 'DATE' */
					groupByDate(event_detailsDTO, mapOfMap, mapKeys);
					break;
				case "PRESENTER":
					/* SUB EVENTS WILL BE GROUPED BY 'PRESENTER' */
					groupByPresenter(event_detailsDTO, mapOfMap, mapKeysP);
					break;
				case "STAGE":
					/* SUB EVENTS WILL BE GROUPED BY 'STAGE' */
					groupByStage(event_detailsDTO, mapOfMap, mapKeysP);
					break;
				case "NONE":
				default:
					/* SUB EVENTS WILL BE GROUPED BY 'NONE' */
					groupByNone(event_detailsDTO, mapOfMap);
					break;
				}
			}
		}

		switch (groupBy) {
		case "DATE":
			/* Sort sub events by dates in chronological order */
			orderByDate(listOfMap, mapOfMap, mapKeys);
			break;
		case "PRESENTER":
			/* Sort sub events by presenters in chronological order */
			orderByPresenter(listOfMap, mapOfMap, mapKeysP);
			break;
		case "STAGE":
			/* Sort sub events by stages in chronological order */
			orderByStage(listOfMap, mapOfMap, mapKeysP);
			break;
		case "NONE":
		default:
			/* Sort sub events by none */
			orderByNone(listOfMap, mapOfMap);
			break;
		}
		return listOfMap;
	}

	private void groupByDate(Event_DetailsDTO event_detailsDTO, Map<String, Map<String, Object>> mapOfMap, List<LocalDate> mapKeys) {
		String date = event_detailsDTO.getStartDate().toString();
		Map<String, Object> eventsMap = mapOfMap.get(date);
		if (eventsMap == null) {
			eventsMap = new LinkedHashMap<>();
			eventsMap.put("dateAsTitle", date);
			mapKeys.add(event_detailsDTO.getStartDate());

			// Collect list of event_details
			List<Event_DetailsDTO> list = new ArrayList<>();
			list.add(event_detailsDTO);
			Collections.sort(list, new EventsComparator());
			eventsMap.put("data", list);

		} else {
			List<Event_DetailsDTO> subevents = (List<Event_DetailsDTO>) eventsMap.get("data");
			subevents.add(event_detailsDTO);
			eventsMap.put("data", subevents);
		}

		mapOfMap.put(date, eventsMap);
	}

	private void orderByDate(List<Map<String, Object>> listOfMap, Map<String, Map<String, Object>> mapOfMap,
			List<LocalDate> mapKeys) {
		mapKeys.sort(Comparator.naturalOrder());
		for (LocalDate ld : mapKeys) {
			listOfMap.add(mapOfMap.get(ld.toString()));
		}
	}

	private void groupByPresenter(Event_DetailsDTO event_detailsDTO, Map<String, Map<String, Object>> mapOfMap,
			List<String> mapKeysP) {
		List<PresenterDTO> presenters = event_detailsDTO.getPresenters();

		for (PresenterDTO p : presenters) {
			Map<String, Object> eventsMap = mapOfMap.get(p.getName());
			if (eventsMap == null) {
				eventsMap = new LinkedHashMap<>();
				eventsMap.put("presenterAsTitle", p.getName());
				mapKeysP.add(p.getName());

				// Collect list of event_details
				List<Event_DetailsDTO> list = new ArrayList<>();
				list.add(event_detailsDTO);
				eventsMap.put("data", list);

			} else {
				List<Event_DetailsDTO> subevents = (List<Event_DetailsDTO>) eventsMap.get("data");
				subevents.add(event_detailsDTO);
				eventsMap.put("data", subevents);
			}

			mapOfMap.put(p.getName(), eventsMap);
		}
	}

	private void orderByPresenter(List<Map<String, Object>> listOfMap, Map<String, Map<String, Object>> mapOfMap,
			List<String> mapKeysP) {
		mapKeysP.sort(Comparator.naturalOrder());
		for (String s : mapKeysP) {
			listOfMap.add(mapOfMap.get(s));
		}
	}

	private void groupByStage(Event_DetailsDTO event_detailsDTO, Map<String, Map<String, Object>> mapOfMap,
			List<String> mapKeysP) {
		String stage = event_detailsDTO.getStage().getName();
		Map<String, Object> eventsMap = mapOfMap.get(stage);
		if (eventsMap == null) {
			eventsMap = new LinkedHashMap<>();
			eventsMap.put("stageAsTitle", stage);
			mapKeysP.add(stage);

			// Collect list of event_details
			List<Event_DetailsDTO> list = new ArrayList<>();
			list.add(event_detailsDTO);
			eventsMap.put("data", list);

		} else {
			List<Event_DetailsDTO> subevents = (List<Event_DetailsDTO>) eventsMap.get("data");
			subevents.add(event_detailsDTO);
			eventsMap.put("data", subevents);
		}

		mapOfMap.put(stage, eventsMap);
	}

	private void orderByStage(List<Map<String, Object>> listOfMap, Map<String, Map<String, Object>> mapOfMap,
			List<String> mapKeysP) {
		mapKeysP.sort(Comparator.naturalOrder());
		for (String s : mapKeysP) {
			listOfMap.add(mapOfMap.get(s));
		}
	}

	private void groupByNone(Event_DetailsDTO event_detailsDTO, Map<String, Map<String, Object>> mapOfMap) {
		Map<String, Object> eventsMap = mapOfMap.get("None");
		if (eventsMap == null) {
			eventsMap = new LinkedHashMap<>();
			eventsMap.put("NoneAsTitle", "None");

			// Collect list of event_details
			List<Event_DetailsDTO> list = new ArrayList<>();
			list.add(event_detailsDTO);
			eventsMap.put("data", list);

		} else {
			List<Event_DetailsDTO> subevents = (List<Event_DetailsDTO>) eventsMap.get("data");
			subevents.add(event_detailsDTO);
			eventsMap.put("data", subevents);
		}

		mapOfMap.put("None", eventsMap);
	}

	private void orderByNone(List<Map<String, Object>> listOfMap, Map<String, Map<String, Object>> mapOfMap) {
		Map<String, Object> maps = mapOfMap.get("None");
		List<Event_DetailsDTO> subevents = (List<Event_DetailsDTO>) maps.get("data");

		Collections.sort(subevents, new EventsComparator());

		maps.put("data", subevents);
		listOfMap.add(maps);
	}

	private List<String> removeDuplicates(List<String> list) {
		Set<String> set = new LinkedHashSet<>(list);
		list.clear();
		list.addAll(set);
		return list;
	}

	/*
	 * 
	 * NOT IN USE for mobile platform
	 * 
	 */
	public StagesDTO findEventStageByEventId(Long id) {

		Event event = eventRepository.findEventWithStageByEventId(id);

		if (event == null) {
			throw new DataNotFoundException("Event not found with ID: " + id);
		}
		StagesDTO stagesDTO = modelMapper.map(event, StagesDTO.class);

		return stagesDTO;
	}

	public PresentersDTO findEventPresentersByEventId(Long id) {

		Event event = eventRepository.findEventWithEventPresentersByEventId(id);

		if (event == null) {
			throw new DataNotFoundException("Event not found with ID: " + id);
		}
		PresentersDTO presentersDTO = modelMapper.map(event, PresentersDTO.class);

		return presentersDTO;
	}

	public VenuesDTO findEventVenueByEventId(Long id) {

		Event event = eventRepository.findEventWithVenueByEventId(id);

		if (event == null) {
			throw new DataNotFoundException("Event not found with ID: " + id);
		}
		VenuesDTO venuesDTO = modelMapper.map(event, VenuesDTO.class);

		return venuesDTO;
	}

	@Transactional
	public ExhibitorsDTO findEventExhibitorsByEventId(Long id) {

		Event event = eventRepository.findEventWithEventExhibitorsByEventId(id);

		if (event == null) {
			throw new DataNotFoundException("Event not found with ID: " + id);
		}
		ExhibitorsDTO exhibitorsDTO = modelMapper.map(event, ExhibitorsDTO.class);

		return exhibitorsDTO;
	}

	public RestaurantsDTO findEventRestaurantsByEventId(Long id) {

		Event event = eventRepository.findEventWithEventRestaurantsByEventId(id);

		if (event == null) {
			throw new DataNotFoundException("Event not found with ID: " + id);
		}
		RestaurantsDTO restaurantsDTO = modelMapper.map(event, RestaurantsDTO.class);

		return restaurantsDTO;
	}

	public TagsDTO findEventTagsByEventId(Long id) {

		Event event = eventRepository.findEventWithEventTagsByEventId(id);

		if (event == null) {
			throw new DataNotFoundException("Event not found with ID: " + id);
		}
		TagsDTO tagsDTO = modelMapper.map(event, TagsDTO.class);

		return tagsDTO;
	}

}