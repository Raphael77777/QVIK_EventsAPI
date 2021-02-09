package com.qvik.events.web;

import java.text.ParseException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qvik.events.modules.event.Event;
import com.qvik.events.modules.event.EventRepository;
import com.qvik.events.modules.event.EventService;
import com.qvik.events.modules.exhibitor.EventExhibitorRepository;
import com.qvik.events.modules.exhibitor.Event_Exhibitor;
import com.qvik.events.modules.exhibitor.Exhibitor;
import com.qvik.events.modules.exhibitor.ExhibitorRepository;
import com.qvik.events.modules.exhibitor.ExhibitorService;
import com.qvik.events.modules.presenter.EventPresenterRepository;
import com.qvik.events.modules.presenter.Event_Presenter;
import com.qvik.events.modules.presenter.Presenter;
import com.qvik.events.modules.presenter.PresenterRepository;
import com.qvik.events.modules.presenter.PresenterService;
import com.qvik.events.modules.restaurant.EventRestaurantRepository;
import com.qvik.events.modules.restaurant.Event_Restaurant;
import com.qvik.events.modules.restaurant.Restaurant;
import com.qvik.events.modules.restaurant.RestaurantRepository;
import com.qvik.events.modules.restaurant.RestaurantService;
import com.qvik.events.modules.stage.EventStageRepository;
import com.qvik.events.modules.stage.Event_Stage;
import com.qvik.events.modules.stage.Stage;
import com.qvik.events.modules.stage.StageRepository;
import com.qvik.events.modules.stage.StageService;
import com.qvik.events.modules.venue.EventVenueRepository;
import com.qvik.events.modules.venue.Event_Venue;
import com.qvik.events.modules.venue.Venue;
import com.qvik.events.modules.venue.VenueRepository;
import com.qvik.events.modules.venue.VenueService;

import lombok.RequiredArgsConstructor;

/** Controller used for user requests */
@RestController
@RequiredArgsConstructor
public class UserController {

	private final EventExhibitorRepository eventExhibitorRepository;
	private final EventPresenterRepository eventPresenterRepository;
	private final EventRepository eventRepository;
	private final EventRestaurantRepository eventRestaurantRepository;
	private final EventStageRepository eventStageRepository;
	private final EventVenueRepository eventVenueRepository;
	private final ExhibitorRepository exhibitorRepository;
	private final PresenterRepository presenterRepository;
	private final RestaurantRepository restaurantRepository;
	private final StageRepository stageRepository;
	private final VenueRepository venueRepository;

	private final EventService eventService;
	private final VenueService venueService;
	private final StageService stageService;
	private final ExhibitorService exhibitorService;
	private final PresenterService presenterService;
	private final RestaurantService restaurantService;

	@GetMapping(path = "/")
	public String root() throws JsonProcessingException {
		return "Main Page";
	}

	@GetMapping(path = "/events")
	public List<Event> events() {
		return eventRepository.findAll();
	}
	//TODO : Should renamed path. required=false. if param==null {redirect to /events}
	@GetMapping(path = "/events/start") 
	public List<Event> eventsDate(@RequestParam(name = "date") String date) throws ParseException {
		List<Event> events = eventService.findEventsByStartDate(date);
		return events;
	}

	/*
	 * TODO: CREATE MAPPER for response body -> Need discussion w FrontEnd
	 * 
	 */
	@GetMapping(path = "/events/{eventId}")
	public Event eventsInfo(@PathVariable Long eventId) {
		Event event = eventService.findEventByEventId(eventId);
		return event;
	}

	@GetMapping(path = "/events/{eventId}/stages")
	public List<Event_Stage> eventsStage(@PathVariable Long eventId) {
		Event event = eventService.findEventByEventId(eventId);
		return event.getEvent_stages();
	}

	@GetMapping(path = "/events/{eventId}/venues")
	public List<Event_Venue> eventsVenue(@PathVariable Long eventId) {
		Event event = eventService.findEventByEventId(eventId);
		return event.getEvent_venues();
	}

	@GetMapping(path = "/events/{eventId}/exhibitors")
	public List<Event_Exhibitor> eventsExhibitor(@PathVariable Long eventId) {
		Event event = eventService.findEventByEventId(eventId);
		return event.getEvent_exhibitors();
	}

	@GetMapping(path = "/events/{eventId}/presenters")
	public List<Event_Presenter> eventsPresenter(@PathVariable Long eventId) {
		Event event = eventService.findEventByEventId(eventId);		
		return event.getEvent_presenters();
	}

	@GetMapping(path = "/events/{eventId}/full-description")
	public String eventsFullDescription(@PathVariable Long eventId) throws JsonProcessingException {
		Event event = eventService.findEventByEventId(eventId);
		return new ObjectMapper().writeValueAsString(event.getFullDescription());
	}

	@GetMapping(path = "/events/{eventId}/restaurants")
	public List<Event_Restaurant> eventsRestaurant(@PathVariable Long eventId) {
		Event event = eventService.findEventByEventId(eventId);
		return event.getEvent_restaurants();
	}

	/*
	 * TODO: Separate CONTROLLER
	 */
	@GetMapping(path = "/venues")
	public List<Venue> venues() {
		return venueRepository.findAll();
	}

	@GetMapping(path = "/venues/{venueId}")
	public Venue venuesInfo(@PathVariable Long venueId) {
		Venue venue = venueService.findVenueByVenueId(venueId);
		return venue;
	}

	@GetMapping(path = "/venues/{venueId}/stages")
	public List<Stage> venuesStage(@PathVariable Long venueId) {
		Venue venue = venueService.findVenueByVenueId(venueId);
		return venue.getStages();
	}
	
	@GetMapping(path = "/venues/{venueId}/restaurants")
	public List<Restaurant> venusRestaurant(@PathVariable Long venueId)  {
		Venue venue = venueService.findVenueByVenueId(venueId);
		List<Restaurant> restaurants = restaurantRepository.findByVenueEquals(venue);
		return restaurants;
	}

	@GetMapping(path = "/stages")
	public List<Stage> stage() {
		return stageRepository.findAll();
	}

	@GetMapping(path = "/stages/{stageId}")
	public Stage stagesInfo(@PathVariable Long stageId) {
		Stage stage = stageService.findStageByStaageId(stageId);
		return stage;
	}

	@GetMapping(path = "/exhibitors")
	public List<Exhibitor> exhibitor() {
		return exhibitorRepository.findAll();
	}

	@GetMapping(path = "/exhibitors/{exhibitorId}")
	public Exhibitor exhibitorsInfo(@PathVariable Long exhibitorId) {
		Exhibitor exhibitor = exhibitorService.findExhibitorByExhibitorId(exhibitorId);
		return exhibitor;
	}

	@GetMapping(path = "/presenters")
	public List<Presenter> presenter() {
		return presenterRepository.findAll();
	}

	@GetMapping(path = "/presenters/{presenterId}")
	public Presenter presentersInfo(@PathVariable Long presenterId) {
		Presenter presenter = presenterService.findPresenterByPresenterId(presenterId);
		return presenter;
	}

	@GetMapping(path = "/restaurants")
	public List<Restaurant> restaurants() {
		return restaurantRepository.findAll();
	}

	@GetMapping(path = "/restaurants/{restaurantId}")
	public Restaurant restaurantsInfo(@PathVariable Long restaurantId) {
		Restaurant restaurant = restaurantService.findRestaurantByRestaurantId(restaurantId);
		return restaurant;
	}
}
