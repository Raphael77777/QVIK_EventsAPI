package com.qvik.events.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.qvik.events.infra.response.*;
import com.qvik.events.modules.tag.Tag;
import com.qvik.events.modules.tag.TagRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.qvik.events.modules.event.EventService;
import com.qvik.events.modules.exhibitor.Exhibitor;
import com.qvik.events.modules.exhibitor.ExhibitorRepository;
import com.qvik.events.modules.exhibitor.ExhibitorService;
import com.qvik.events.modules.presenter.Presenter;
import com.qvik.events.modules.presenter.PresenterRepository;
import com.qvik.events.modules.presenter.PresenterService;
import com.qvik.events.modules.restaurant.Restaurant;
import com.qvik.events.modules.restaurant.RestaurantRepository;
import com.qvik.events.modules.restaurant.RestaurantService;
import com.qvik.events.modules.stage.Stage;
import com.qvik.events.modules.stage.StageRepository;
import com.qvik.events.modules.stage.StageService;
import com.qvik.events.modules.venue.Venue;
import com.qvik.events.modules.venue.VenueRepository;
import com.qvik.events.modules.venue.VenueService;

import lombok.RequiredArgsConstructor;

/** Controller used for user requests */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class UserController {

	private final ExhibitorRepository exhibitorRepository;
	private final PresenterRepository presenterRepository;
	private final RestaurantRepository restaurantRepository;
	private final StageRepository stageRepository;
	private final VenueRepository venueRepository;
	private final TagRepository tagRepository;

	private final EventService eventService;
	private final VenueService venueService;
	private final StageService stageService;
	private final ExhibitorService exhibitorService;
	private final PresenterService presenterService;
	private final RestaurantService restaurantService;

	/*
	 * Main page shows links to API Docs & API Definition 
	 */
	@GetMapping(path = "/")
	public ResponseMessage home() {
		Map<String, String> links = new HashMap<>();
		links.put("API Docs", "https://qvik.herokuapp.com/api-docs");
		links.put("API Definition", "https://qvik.herokuapp.com/swagger-ui.html");
		return convertToResponseMessage(links);
	}
	
	/*
	 * Event APIs
	 */
	@GetMapping(path = "/events")
	public ResponseMessage events() {
		Map<String, Object> events = eventService.findAllEvents();		
		return convertToResponseMessage(events);
	}
	
	@GetMapping(path = "/ongoing-events")
	public ResponseMessage eventsOngoing(@RequestParam(name = "on") String date) {
		Map<String, Object> events = eventService.findOnGoingEvents(date);
		return convertToResponseMessage(events);
	}
	
	@GetMapping(path = "/events/{eventId}")
	public ResponseMessage eventsInfo(@PathVariable Long eventId) {
		Event_DetailsDTO event = eventService.findEventByEventId(eventId);
		return convertToResponseMessage(event);
	}
	
	@GetMapping(path = "/events/tag")
	public ResponseMessage eventsTag(@RequestParam(name = "tag") String tagName) {
		Map<String, Object> events = eventService.findEventsByTags(tagName);
		return convertToResponseMessage(events);
	}

	@GetMapping(path = "/events/{eventId}/stage")
	public ResponseMessage eventsStage(@PathVariable Long eventId) {
		StagesDTO stagesDTO = eventService.findEventStageByEventId(eventId);
		return convertToResponseMessage(stagesDTO);
	}

	@GetMapping(path = "/events/{eventId}/venue")
	public ResponseMessage eventsVenue(@PathVariable Long eventId) {
		VenuesDTO venuesDTO = eventService.findEventVenueByEventId(eventId);
		return convertToResponseMessage(venuesDTO);
	}

	@GetMapping(path = "/events/{eventId}/exhibitors")
	public ResponseMessage eventsExhibitor(@PathVariable Long eventId) {
		ExhibitorsDTO exhibitorsDTO = eventService.findEventExhibitorsByEventId(eventId);
		return convertToResponseMessage(exhibitorsDTO);
	}

	@GetMapping(path = "/events/{eventId}/presenters")
	public ResponseMessage eventsPresenter(@PathVariable Long eventId) {
		PresentersDTO presentersDTO = eventService.findEventPresentersByEventId(eventId);
		return convertToResponseMessage(presentersDTO);
	}

	@GetMapping(path = "/events/{eventId}/restaurants")
	public ResponseMessage eventsRestaurant(@PathVariable Long eventId) {
		RestaurantsDTO restaurantsDTO = eventService.findEventRestaurantsByEventId(eventId);
		return convertToResponseMessage(restaurantsDTO);
	}

	@GetMapping(path = "/events/{eventId}/tags")
	public ResponseMessage eventsTag(@PathVariable Long eventId) {
		TagsDTO tagsDTO = eventService.findEventTagsByEventId(eventId);
		return convertToResponseMessage(tagsDTO);
	}

	@GetMapping(path = "/events/{eventId}/full-description")
	public ResponseMessage eventsFullDescription(@PathVariable Long eventId) throws JsonProcessingException {
		Event_DetailsDTO event = eventService.findEventByEventId(eventId);
		String desc = event.getFullDescription();
		Map<String, String> response = new HashMap<>();
		response.put("fullDescription", desc);
		return convertToResponseMessage(response);
	}

	/*
	 * Venue APIs
	 */
	@GetMapping(path = "/venues")
	public ResponseMessage venues() {
		List<Venue> venues = venueRepository.findAll();
		return convertToResponseMessage(venues);
	}

	@GetMapping(path = "/venues/{venueId}")
	public ResponseMessage venuesInfo(@PathVariable Long venueId) {
		Venue venue = venueService.findVenueByVenueId(venueId);
		return convertToResponseMessage(venue);
	}

	@GetMapping(path = "/venues/{venueId}/stages")
	public ResponseMessage venuesStage(@PathVariable Long venueId) {
		Venue venue = venueService.findVenueByVenueId(venueId);
		List<Stage> venueStages = venue.getStages();
		return convertToResponseMessage(venueStages);
	}

	@GetMapping(path = "/venues/{venueId}/restaurants")
	public ResponseMessage venusRestaurant(@PathVariable Long venueId) {
		Venue venue = venueService.findVenueByVenueId(venueId);
		List<Restaurant> restaurants = restaurantRepository.findByVenueEquals(venue);
		return convertToResponseMessage(restaurants);
	}

	/*
	 * Stage APIs
	 */
	@GetMapping(path = "/stages")
	public ResponseMessage stage() {
		List<Stage> stages = stageRepository.findAll();
		return convertToResponseMessage(stages);
	}

	@GetMapping(path = "/stages/{stageId}")
	public ResponseMessage stagesInfo(@PathVariable Long stageId) {
		Stage stage = stageService.findStageByStageId(stageId);
		return convertToResponseMessage(stage);
	}

	/*
	 * Exhibitor APIs
	 */
	@GetMapping(path = "/exhibitors")
	public ResponseMessage exhibitor() {
		List<Exhibitor> exhibitors = exhibitorRepository.findAll();
		return convertToResponseMessage(exhibitors);
	}

	@GetMapping(path = "/exhibitors/{exhibitorId}")
	public ResponseMessage exhibitorsInfo(@PathVariable Long exhibitorId) {
		Exhibitor exhibitor = exhibitorService.findExhibitorByExhibitorId(exhibitorId);
		return convertToResponseMessage(exhibitor);
	}

	/*
	 * Presenter APIs
	 */
	@GetMapping(path = "/presenters")
	public ResponseMessage presenter() {
		List<Presenter> presenters = presenterRepository.findAll();
		return convertToResponseMessage(presenters);
	}

	@GetMapping(path = "/presenters/{presenterId}")
	public ResponseMessage presentersInfo(@PathVariable Long presenterId) {
		Presenter presenter = presenterService.findPresenterByPresenterId(presenterId);
		return convertToResponseMessage(presenter);
	}

	/*
	 * Restaurant APIs
	 */
	@GetMapping(path = "/restaurants")
	public ResponseMessage restaurants() {
		Map<String, Object> restaurants = restaurantService.findAllRestaurants();
		return convertToResponseMessage(restaurants);
	}

	@GetMapping(path = "/restaurants/{restaurantId}")
	public ResponseMessage restaurantsInfo(@PathVariable Long restaurantId) {
		Restaurant_DetailsDTO restaurant = restaurantService.findRestaurantByRestaurantId(restaurantId);
		return convertToResponseMessage(restaurant);
	}

	/*
	 * Tag APIs
	 */
	@GetMapping(path = "/tags")
	public ResponseMessage tags() {
		List<Tag> tags = tagRepository.findAll();
		return convertToResponseMessage(tags);
	}

	/*
	 * Convert Data to ResponseMessage.class
	 */
	private ResponseMessage convertToResponseMessage(Object object) {
		ResponseMessage message = new ResponseMessage(HttpStatus.OK);
		message.add(object);
		return message;
	}

	/*
	 * Controller level exception handler
	 */
	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseMessage badRequestErrorHandling(Exception ex) {
		ResponseMessage error = new ResponseMessage(HttpStatus.BAD_REQUEST, ex);
		return error;
	}
}
