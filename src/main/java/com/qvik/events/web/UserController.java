package com.qvik.events.web;

import com.qvik.events.infra.cache.ProxyCache;
import com.qvik.events.infra.response.ResponseMessage;
import com.qvik.events.infra.response.dto.*;
import com.qvik.events.modules.event.EventService;
import com.qvik.events.modules.exhibitor.Exhibitor;
import com.qvik.events.modules.exhibitor.ExhibitorService;
import com.qvik.events.modules.image.ImageService;
import com.qvik.events.modules.presenter.Presenter;
import com.qvik.events.modules.presenter.PresenterService;
import com.qvik.events.modules.restaurant.Restaurant;
import com.qvik.events.modules.restaurant.RestaurantRepository;
import com.qvik.events.modules.restaurant.RestaurantService;
import com.qvik.events.modules.stage.Stage;
import com.qvik.events.modules.stage.StageService;
import com.qvik.events.modules.tag.TagService;
import com.qvik.events.modules.venue.Venue;
import com.qvik.events.modules.venue.VenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Controller used for user requests */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class UserController {

	private final RestaurantRepository restaurantRepository;

	private final EventService eventService;
	private final VenueService venueService;
	private final StageService stageService;
	private final ExhibitorService exhibitorService;
	private final PresenterService presenterService;
	private final RestaurantService restaurantService;
	private final ImageService imageService;
	private final TagService tagService;

	private final ProxyCache proxy = ProxyCache.getInstance();

	/*
	 * Main page shows links to API Docs & API Definition
	 */
	@GetMapping(path = "/")
	public ResponseMessage home() {
		ResponseMessage response = proxy.getData("/");
		if (response == null){
			Map<String, String> links = new HashMap<>();
			links.put("API Docs", "https://qvik.herokuapp.com/api-docs");
			links.put("API Definition", "https://qvik.herokuapp.com/swagger-ui.html");
			response = convertToResponseMessage(links);

			proxy.setData("/", response);
		}
		return response;
	}

	/*
	 * Last Update Date & Time API
	 */
	@GetMapping(path = "/last-update")
	public ResponseMessage lastUpdate() {
		Map<String, String> links = new HashMap<>();
		links.put("lastUpdateDateTime", proxy.getLastUpdateDateTime().toString());
		return convertToResponseMessage(links);
	}

	/*
	 * Header-setup API
	 */
	@GetMapping(path = "/initial-setup")
	public ResponseMessage initialSetUp() {
		ResponseMessage response = proxy.getData("/initial-setup");
		if (response == null){
			Init_SettingDTO initialSetUp = eventService.findInitialSetUpData();
			response = convertToResponseMessage(initialSetUp);

			proxy.setData("/initial-setup", response);
		}
		return response;
	}

	/*
	 * NEW - Event API
	 */
	@GetMapping(path = "/events")
	public ResponseMessage events(@RequestParam(defaultValue = "NONE", required = false) String groupBy) {
		ResponseMessage response = proxy.getData("/events+"+groupBy);
		if (response == null){
			List<Map<String, Object>> events = eventService.findAllEvents(groupBy);
			response = convertToResponseMessage(events);

			proxy.setData("/events+"+groupBy, response);
		}
		return response;
	}

	/*
	 * OLD - Event APIs
	 */
//	@GetMapping(path = "/events")
//	public ResponseMessage events() {
//		Map<String, Object> events = eventService.findAllEvents();		
//		return convertToResponseMessage(events);
//	}
//	
//	@GetMapping(path = "/ongoing-events")
//	public ResponseMessage eventsOngoing(@RequestParam(name = "on") String date) {
//		Map<String, Object> events = eventService.findOnGoingEvents(date);
//		return convertToResponseMessage(events);
//	}
//	
//	@GetMapping(path = "/events/{eventId}")
//	public ResponseMessage eventsInfo(@PathVariable Long eventId) {
//		Event_DetailsDTO event = eventService.findEventByEventId(eventId);
//		return convertToResponseMessage(event);
//	}
//	
//	@GetMapping(path = "/events/tag")
//	public ResponseMessage eventsTag(@RequestParam(name = "tag") String tagName) {
//		Map<String, Object> events = eventService.findEventsByTags(tagName);
//		return convertToResponseMessage(events);
//	}
//
//	@GetMapping(path = "/events/{eventId}/stage")
//	public ResponseMessage eventsStage(@PathVariable Long eventId) {
//		StagesDTO stagesDTO = eventService.findEventStageByEventId(eventId);
//		return convertToResponseMessage(stagesDTO);
//	}
//
//	@GetMapping(path = "/events/{eventId}/venue")
//	public ResponseMessage eventsVenue(@PathVariable Long eventId) {
//		VenuesDTO venuesDTO = eventService.findEventVenueByEventId(eventId);
//		return convertToResponseMessage(venuesDTO);
//	}
//
//	@GetMapping(path = "/events/{eventId}/exhibitors")
//	public ResponseMessage eventsExhibitor(@PathVariable Long eventId) {
//		ExhibitorsDTO exhibitorsDTO = eventService.findEventExhibitorsByEventId(eventId);
//		return convertToResponseMessage(exhibitorsDTO);
//	}
//
//	@GetMapping(path = "/events/{eventId}/presenters")
//	public ResponseMessage eventsPresenter(@PathVariable Long eventId) {
//		PresentersDTO presentersDTO = eventService.findEventPresentersByEventId(eventId);
//		return convertToResponseMessage(presentersDTO);
//	}
//
//	@GetMapping(path = "/events/{eventId}/restaurants")
//	public ResponseMessage eventsRestaurant(@PathVariable Long eventId) {
//		RestaurantsDTO restaurantsDTO = eventService.findEventRestaurantsByEventId(eventId);
//		return convertToResponseMessage(restaurantsDTO);
//	}
//
//	@GetMapping(path = "/events/{eventId}/tags")
//	public ResponseMessage eventsTag(@PathVariable Long eventId) {
//		TagsDTO tagsDTO = eventService.findEventTagsByEventId(eventId);
//		return convertToResponseMessage(tagsDTO);
//	}
//
//	@GetMapping(path = "/events/{eventId}/full-description")
//	public ResponseMessage eventsFullDescription(@PathVariable Long eventId) throws JsonProcessingException {
//		Event_DetailsDTO event = eventService.findEventByEventId(eventId);
//		String desc = event.getFullDescription();
//		Map<String, String> response = new HashMap<>();
//		response.put("fullDescription", desc);
//		return convertToResponseMessage(response);
//	}

	/*
	 * Venue APIs
	 */
	@GetMapping(path = "/venues")
	public ResponseMessage venues() {
		ResponseMessage response = proxy.getData("/venues");
		if (response == null){
			List<Venue_DetailsDTO> venues = venueService.findAllVenues();
			response = convertToResponseMessage(venues);

			proxy.setData("/venues", response);
		}
		return response;
	}

	@GetMapping(path = "/venues/{venueId}")
	public ResponseMessage venuesInfo(@PathVariable Long venueId) {
		ResponseMessage response = proxy.getData("/venues/"+venueId);
		if (response == null){
			Venue venue = venueService.findVenueByVenueId(venueId);
			response = convertToResponseMessage(venue);

			proxy.setData("/venues/"+venueId, response);
		}
		return response;
	}

	@GetMapping(path = "/venues/{venueId}/stages")
	public ResponseMessage venuesStage(@PathVariable Long venueId) {
		ResponseMessage response = proxy.getData("/venues/"+venueId+"/stages");
		if (response == null){
			Venue venue = venueService.findVenueByVenueId(venueId);
			List<Stage> venueStages = venue.getStages();
			response = convertToResponseMessage(venueStages);

			proxy.setData("/venues/"+venueId+"/stages", response);
		}
		return response;
	}

	@GetMapping(path = "/venues/{venueId}/restaurants")
	public ResponseMessage venusRestaurant(@PathVariable Long venueId) {
		ResponseMessage response = proxy.getData("/venues/"+venueId+"/restaurants");
		if (response == null){
			Venue venue = venueService.findVenueByVenueId(venueId);
			List<Restaurant> restaurants = restaurantRepository.findByVenueEquals(venue);
			response = convertToResponseMessage(restaurants);

			proxy.setData("/venues/"+venueId+"/restaurants", response);
		}
		return response;
	}

	/*
	 * Stage APIs
	 */
	@GetMapping(path = "/stages")
	public ResponseMessage stage() {
		ResponseMessage response = proxy.getData("/stages");
		if (response == null){
			List<Stage_DetailsDTO> stages = stageService.findAllStages();
			response = convertToResponseMessage(stages);

			proxy.setData("/stages", response);
		}
		return response;
	}

	@GetMapping(path = "/stages/{stageId}")
	public ResponseMessage stagesInfo(@PathVariable Long stageId) {
		ResponseMessage response = proxy.getData("/stages/"+stageId);
		if (response == null){
			Stage stage = stageService.findStageByStageId(stageId);
			response = convertToResponseMessage(stage);

			proxy.setData("/stages/"+stageId, response);
		}
		return response;
	}

	/*
	 * Exhibitor APIs
	 */
	@GetMapping(path = "/exhibitors")
	public ResponseMessage exhibitor() {
		ResponseMessage response = proxy.getData("/exhibitors");
		if (response == null){
			List<Exhibitor_DetailsDTO> exhibitors = exhibitorService.findAllExhibitors();
			response = convertToResponseMessage(exhibitors);

			proxy.setData("/exhibitors", response);
		}
		return response;
	}

	@GetMapping(path = "/exhibitors/{exhibitorId}")
	public ResponseMessage exhibitorsInfo(@PathVariable Long exhibitorId) {
		ResponseMessage response = proxy.getData("/exhibitors/"+exhibitorId);
		if (response == null){
			Exhibitor exhibitor = exhibitorService.findExhibitorByExhibitorId(exhibitorId);
			response = convertToResponseMessage(exhibitor);

			proxy.setData("/exhibitors/"+exhibitorId, response);
		}
		return response;
	}

	/*
	 * Presenter APIs
	 */
	@GetMapping(path = "/presenters")
	public ResponseMessage presenter() {
		ResponseMessage response = proxy.getData("/presenters");
		if (response == null){
			List<Presenter_DetailsDTO> presenters = presenterService.findAllPresenters();
			response = convertToResponseMessage(presenters);

			proxy.setData("/presenters", response);
		}
		return response;
	}

	@GetMapping(path = "/presenters/{presenterId}")
	public ResponseMessage presentersInfo(@PathVariable Long presenterId) {
		ResponseMessage response = proxy.getData("/presenters/"+presenterId);
		if (response == null){
			Presenter presenter = presenterService.findPresenterByPresenterId(presenterId);
			response = convertToResponseMessage(presenter);

			proxy.setData("/presenters/"+presenterId, response);
		}
		return response;
	}

	/*
	 * Restaurant APIs
	 */
	@GetMapping(path = "/restaurants")
	public ResponseMessage restaurants() {
		ResponseMessage response = proxy.getData("/restaurants");
		if (response == null){
			Map<String, Object> restaurants = restaurantService.findAllRestaurants();
			response = convertToResponseMessage(restaurants);

			proxy.setData("/restaurants", response);
		}
		return response;
	}

	@GetMapping(path = "/restaurants/{restaurantId}")
	public ResponseMessage restaurantsInfo(@PathVariable Long restaurantId) {
		ResponseMessage response = proxy.getData("/restaurants/"+restaurantId);
		if (response == null){
			Restaurant_DetailsDTO restaurant = restaurantService.findRestaurantByRestaurantId(restaurantId);
			response = convertToResponseMessage(restaurant);

			proxy.setData("/restaurants/"+restaurantId, response);
		}
		return response;
	}

	/*
	 * Image APIs
	 */
	@GetMapping(path = "/images")
	public ResponseMessage images() {
		ResponseMessage response = proxy.getData("/images");
		if (response == null){
			List<ImageDTO> images = imageService.findAllImages();
			response = convertToResponseMessage(images);

			proxy.setData("/images", response);
		}
		return response;
	}

	@GetMapping(value = "/images/{imageId}", produces = MediaType.IMAGE_JPEG_VALUE)
	public Resource downloadImage(@PathVariable Long imageId) {
		Resource resource = proxy.getImage("/images/"+imageId);
		if (resource == null){
			resource = imageService.findImageByImageId(imageId);

			proxy.setImage("/images/"+imageId, resource);
		}
		return resource;
	}

	/*
	 * Tag APIs
	 */
	@GetMapping(path = "/tags")
	public ResponseMessage tags() {
		ResponseMessage response = proxy.getData("/tags");
		if (response == null){
			List<TagDTO> tags = tagService.findAllTags();
			response = convertToResponseMessage(tags);

			proxy.setData("/tags", response);
		}
		return response;
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
