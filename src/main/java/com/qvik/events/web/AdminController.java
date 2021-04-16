package com.qvik.events.web;

import com.qvik.events.infra.cache.ProxyCache;
import com.qvik.events.infra.response.ResponseMessage;
import com.qvik.events.infra.response.dto.LinkToDTO;
import com.qvik.events.modules.cuisine.Cuisine;
import com.qvik.events.modules.cuisine.CuisineRepository;
import com.qvik.events.modules.cuisine.RestaurantCuisineRepository;
import com.qvik.events.modules.cuisine.Restaurant_Cuisine;
import com.qvik.events.modules.event.Event;
import com.qvik.events.modules.event.EventRepository;
import com.qvik.events.modules.exhibitor.EventExhibitorRepository;
import com.qvik.events.modules.exhibitor.Event_Exhibitor;
import com.qvik.events.modules.exhibitor.Exhibitor;
import com.qvik.events.modules.exhibitor.ExhibitorRepository;
import com.qvik.events.modules.image.Image;
import com.qvik.events.modules.image.ImageRepository;
import com.qvik.events.modules.image.ImageService;
import com.qvik.events.modules.presenter.EventPresenterRepository;
import com.qvik.events.modules.presenter.Event_Presenter;
import com.qvik.events.modules.presenter.Presenter;
import com.qvik.events.modules.presenter.PresenterRepository;
import com.qvik.events.modules.restaurant.EventRestaurantRepository;
import com.qvik.events.modules.restaurant.Event_Restaurant;
import com.qvik.events.modules.restaurant.Restaurant;
import com.qvik.events.modules.restaurant.RestaurantRepository;
import com.qvik.events.modules.stage.Stage;
import com.qvik.events.modules.stage.StageRepository;
import com.qvik.events.modules.tag.*;
import com.qvik.events.modules.venue.Venue;
import com.qvik.events.modules.venue.VenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.TransactionScoped;
import java.util.ArrayList;
import java.util.List;

/** Controller used for admin page */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class AdminController {

	private final ImageService imageService;

	private final EventRepository eventRepository;
	private final ExhibitorRepository exhibitorRepository;
	private final PresenterRepository presenterRepository;
	private final RestaurantRepository restaurantRepository;
	private final TagRepository tagRepository;
	private final CuisineRepository cuisineRepository;
	private final StageRepository stageRepository;
	private final VenueRepository venueRepository;
	private final ImageRepository imageRepository;

	private final EventTagRepository eventTagRepository;
	private final EventExhibitorRepository eventExhibitorRepository;
	private final EventRestaurantRepository eventRestaurantRepository;
	private final EventPresenterRepository eventPresenterRepository;
	private final RestaurantCuisineRepository restaurantCuisineRepository;

	private final ProxyCache proxy = ProxyCache.getInstance();

	/*
	 * CREATE Methods
	 */
	@Transactional
	@PostMapping(value = "/events", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Long createEvent(@RequestBody Event event) {
		proxy.resetData();

		event.setHasExhibitor(false);
		event.setHasRestaurant(false);
		event.setHasPresenter(false);
		Long id = eventRepository.save(event).getEventId();

		if (event.isMainEvent()){
			//SET NO VENUE
		 	Venue venue = venueRepository.findByName("No Venue");
		 	LinkToDTO linkToDTO = new LinkToDTO();
		 	linkToDTO.setSourceId(event.getEventId());
		 	linkToDTO.setDestinationId(venue.getVenueId());
		 	linkEventVenue(linkToDTO, "CREATE");
		}else {
			//SET NO STAGE
			Stage stage = stageRepository.findByName("No Stage");
			LinkToDTO linkToDTO = new LinkToDTO();
			linkToDTO.setSourceId(event.getEventId());
			linkToDTO.setDestinationId(stage.getStageId());
			linkEventStage(linkToDTO, "CREATE");
		}

		return id;
	}

	@PostMapping(value = "/exhibitors", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Long createExhibitor(@RequestBody Exhibitor exhibitor) {
		proxy.resetData();

		return exhibitorRepository.save(exhibitor).getExhibitorId();
	}

	@PostMapping(value = "/presenters", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Long createPresenter(@RequestBody Presenter presenter) {
		proxy.resetData();

		return presenterRepository.save(presenter).getPresenterId();
	}

	@PostMapping(value = "/restaurants", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Long createRestaurant(@RequestBody Restaurant restaurant) {
		proxy.resetData();

		return restaurantRepository.save(restaurant).getRestaurantId();
	}

	@PostMapping(value = "/tags", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Long createTag(@RequestBody Tag tag) {
		proxy.resetData();

		return tagRepository.save(tag).getTagId();
	}

	@PostMapping(value = "/cuisines", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Long createCuisine(@RequestBody Cuisine cuisine) {
		proxy.resetData();

		return cuisineRepository.save(cuisine).getCuisineId();
	}

	@PostMapping(value = "/stages", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Long createStage(@RequestBody Stage stage) {
		proxy.resetData();

		return stageRepository.save(stage).getStageId();
	}

	@PostMapping(value = "/venues", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Long createVenue(@RequestBody Venue venue) {
		proxy.resetData();

		return venueRepository.save(venue).getVenueId();
	}

	/*
	 * UPDATE Methods
	 */
	@PutMapping(value = "/events/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	Long updateEvent(@RequestBody Event event, @PathVariable Long id) {
		proxy.resetData();

		return eventRepository.findById(id)
				.map(eventDb -> {
					eventDb.setStartDate(event.getStartDate());
					eventDb.setStartTime(event.getStartTime());
					eventDb.setEndDate(event.getEndDate());
					eventDb.setEndTime(event.getEndTime());
					eventDb.setTitle(event.getTitle());
					eventDb.setShortDescription(event.getShortDescription());
					eventDb.setFullDescription(event.getFullDescription());
					eventDb.setActive(event.isActive());
					eventDb.setMainEvent(event.isMainEvent());
					return eventRepository.save(eventDb).getEventId();
				})
				.orElseGet(() -> {
					event.setEventId(id);
					return eventRepository.save(event).getEventId();
				});
	}

	@PutMapping(value = "/exhibitors/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	Long updateExhibitor(@RequestBody Exhibitor exhibitor, @PathVariable Long id) {
		proxy.resetData();

		return exhibitorRepository.findById(id)
				.map(exhibitorDb -> {
					exhibitorDb.setName(exhibitor.getName());
					exhibitorDb.setLocation(exhibitor.getLocation());
					exhibitorDb.setContact(exhibitor.getContact());
					exhibitorDb.setShortDescription(exhibitor.getShortDescription());
					exhibitorDb.setFullDescription(exhibitor.getFullDescription());
					return exhibitorRepository.save(exhibitorDb).getExhibitorId();
				})
				.orElseGet(() -> {
					exhibitor.setExhibitorId(id);
					return exhibitorRepository.save(exhibitor).getExhibitorId();
				});
	}

	@PutMapping(value = "/presenters/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	Long updatePresenter(@RequestBody Presenter presenter, @PathVariable Long id) {
		proxy.resetData();

		return presenterRepository.findById(id)
				.map(presenterDb -> {
					presenterDb.setName(presenter.getName());
					presenterDb.setContact(presenter.getContact());
					presenterDb.setShortDescription(presenter.getShortDescription());
					presenterDb.setFullDescription(presenter.getFullDescription());
					return presenterRepository.save(presenterDb).getPresenterId();
				})
				.orElseGet(() -> {
					presenter.setPresenterId(id);
					return presenterRepository.save(presenter).getPresenterId();
				});
	}

	@PutMapping(value = "/restaurants/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	Long updateRestaurant(@RequestBody Restaurant restaurant, @PathVariable Long id) {
		proxy.resetData();

		return restaurantRepository.findById(id)
				.map(restaurantDb -> {
					restaurantDb.setName(restaurant.getName());
					restaurantDb.setLocation(restaurant.getLocation());
					restaurantDb.setOpenTime(restaurant.getOpenTime());
					restaurantDb.setCloseTime(restaurant.getCloseTime());
					restaurantDb.setShortDescription(restaurant.getShortDescription());
					restaurantDb.setFullDescription(restaurant.getFullDescription());
					return restaurantRepository.save(restaurantDb).getRestaurantId();
				})
				.orElseGet(() -> {
					restaurant.setRestaurantId(id);
					return restaurantRepository.save(restaurant).getRestaurantId();
				});
	}

	@PutMapping(value = "/stages/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	Long updateStage(@RequestBody Stage stage, @PathVariable Long id) {
		proxy.resetData();

		return stageRepository.findById(id)
				.map(stageDb -> {
					stageDb.setName(stage.getName());
					stageDb.setLocation(stage.getLocation());
					stageDb.setCapacity(stage.getCapacity());
					stageDb.setType(stage.getType());
					return stageRepository.save(stageDb).getStageId();
				})
				.orElseGet(() -> {
					stage.setStageId(id);
					return stageRepository.save(stage).getStageId();
				});
	}

	@PutMapping(value = "/tags/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	Long updateTag(@RequestBody Tag tag, @PathVariable Long id) {
		proxy.resetData();

		return tagRepository.findById(id)
				.map(tagDb -> {
					tagDb.setName(tag.getName());
					return tagRepository.save(tagDb).getTagId();
				})
				.orElseGet(() -> {
					tag.setTagId(id);
					return tagRepository.save(tag).getTagId();
				});
	}

	@PutMapping(value = "/cuisines/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	Long updateCuisine(@RequestBody Cuisine cuisine, @PathVariable Long id) {
		proxy.resetData();

		return cuisineRepository.findById(id)
				.map(cuisineDb -> {
					cuisineDb.setName(cuisine.getName());
					return cuisineRepository.save(cuisineDb).getCuisineId();
				})
				.orElseGet(() -> {
					cuisine.setCuisineId(id);
					return cuisineRepository.save(cuisine).getCuisineId();
				});
	}

	@PutMapping(value = "/venues/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	Long updateVenue(@RequestBody Venue venue, @PathVariable Long id) {
		proxy.resetData();

		return venueRepository.findById(id)
				.map(venueDb -> {
					venueDb.setName(venue.getName());
					venueDb.setCity(venue.getCity());
					venueDb.setAddress(venue.getAddress());
					venueDb.setContact(venue.getContact());
					venueDb.setTransportation(venue.getTransportation());
					venueDb.setFacilities(venue.getFacilities());
					return venueRepository.save(venueDb).getVenueId();
				})
				.orElseGet(() -> {
					venue.setVenueId(id);
					return venueRepository.save(venue).getVenueId();
				});
	}

	/*
	 * DELETE Methods
	 */
	@DeleteMapping(value = "/events/{id}")
	void deleteEvent(@PathVariable Long id) {
		proxy.resetData();

		Event event = eventRepository.findById(id).get();

		if (event.isMainEvent()){
			return;
		}

		eventRepository.deleteById(id);
	}

	@DeleteMapping(value = "/exhibitors/{id}")
	void deleteExhibitor(@PathVariable Long id) {
		proxy.resetData();

		exhibitorRepository.deleteById(id);
	}

	@DeleteMapping(value = "/presenters/{id}")
	void deletePresenter(@PathVariable Long id) {
		proxy.resetData();

		presenterRepository.deleteById(id);
	}

	@DeleteMapping(value = "/restaurants/{id}")
	void deleteRestaurant(@PathVariable Long id) {
		proxy.resetData();

		restaurantRepository.deleteById(id);
	}

	@DeleteMapping(value = "/tags/{id}")
	void deleteTag(@PathVariable Long id) {
		proxy.resetData();

		tagRepository.deleteById(id);
	}

	@DeleteMapping(value = "/cuisines/{id}")
	void deleteCuisine(@PathVariable Long id) {
		proxy.resetData();

		cuisineRepository.deleteById(id);
	}

	@DeleteMapping(value = "/stages/{id}")
	void deleteStage(@PathVariable Long id) {
		proxy.resetData();

		Stage stage = stageRepository.findById(id).get();

		//ON DELETE SET NULL
		List<Event> events = eventRepository.findEventsWithStageByStage(stage);
		for (Event event : events){
			event.setStage(null);
		}
		eventRepository.saveAll(events);

		stageRepository.delete(stage);
	}

	@DeleteMapping(value = "/venues/{id}")
	void deleteVenue(@PathVariable Long id) {
		proxy.resetData();

		Venue venue = venueRepository.findById(id).get();

		//ON DELETE SET NULL
		List<Event> events = eventRepository.findEventsWithVenueByVenue(venue);
		for (Event event : events){
			event.setVenue(null);
		}
		eventRepository.saveAll(events);

		//ON DELETE SET NULL
		List<Restaurant> restaurants = restaurantRepository.findRestaurantsWithVenueByVenue(venue);
		for (Restaurant restaurant : restaurants){
			restaurant.setVenue(null);
		}
		restaurantRepository.saveAll(restaurants);

		//ON DELETE SET NULL
		List<Stage> stages = stageRepository.findStagesWithVenueByVenue(venue);
		for (Stage stage : stages){
			stage.setVenue(null);
		}
		stageRepository.saveAll(stages);

		venueRepository.deleteById(id);
	}

	@DeleteMapping(value = "/images/{id}")
	void deleteImage(@PathVariable Long id) {
		proxy.resetData();
		proxy.resetImage();

		Image image = imageRepository.findById(id).get();

		//ON DELETE SET NULL
		List<Event> events = eventRepository.findEventsWithImageByImage(image);
		for (Event event : events){
			event.setImage(null);
		}
		eventRepository.saveAll(events);

		imageRepository.deleteById(id);
	}

	/*
	 * LINK Methods
	 */
	@PostMapping(value = "/link-event-exhibitor", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	Long linkEventExhibitor(@RequestBody LinkToDTO linkToDTO, @RequestHeader("operation") String operation){
		proxy.resetData();

		Event event = eventRepository.findById(linkToDTO.getSourceId()).get();
		Exhibitor exhibitor = exhibitorRepository.findById(linkToDTO.getDestinationId()).get();

		switch (operation){
			case "CREATE":
				Event_Exhibitor event_exhibitor = new Event_Exhibitor();
				event_exhibitor.setEvent(event);
				event_exhibitor.setExhibitor(exhibitor);
				return eventExhibitorRepository.save(event_exhibitor).getEventExhibitorId();
			case "DELETE":
				List<Event_Exhibitor> event_exhibitors = eventExhibitorRepository.findByEventEqualsAndExhibitorEquals(event, exhibitor);
				eventExhibitorRepository.deleteAll(event_exhibitors);
				break;
		}
		return 0L;
	}

	@PostMapping(value = "/link-event-presenter", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	Long linkEventPresenter(@RequestBody LinkToDTO linkToDTO, @RequestHeader("operation") String operation){
		proxy.resetData();

		Event event = eventRepository.findById(linkToDTO.getSourceId()).get();
		Presenter presenter = presenterRepository.findById(linkToDTO.getDestinationId()).get();

		switch (operation){
			case "CREATE":
				Event_Presenter event_presenter = new Event_Presenter();
				event_presenter.setEvent(event);
				event_presenter.setPresenter(presenter);
				return eventPresenterRepository.save(event_presenter).getEventPresenterId();
			case "DELETE":
				List<Event_Presenter> event_presenters = eventPresenterRepository.findByEventEqualsAndPresenterEquals(event, presenter);
				eventPresenterRepository.deleteAll(event_presenters);
				break;
		}
		return 0L;
	}

	@PostMapping(value = "/link-event-restaurant", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	Long linkEventRestaurant(@RequestBody LinkToDTO linkToDTO, @RequestHeader("operation") String operation){
		proxy.resetData();

		Event event = eventRepository.findById(linkToDTO.getSourceId()).get();
		Restaurant restaurant = restaurantRepository.findById(linkToDTO.getDestinationId()).get();

		switch (operation){
			case "CREATE":
				Event_Restaurant event_restaurant = new Event_Restaurant();
				event_restaurant.setEvent(event);
				event_restaurant.setRestaurant(restaurant);
				return eventRestaurantRepository.save(event_restaurant).getEventRestaurantId();
			case "DELETE":
				List<Event_Restaurant> event_restaurants = eventRestaurantRepository.findByEventEqualsAndRestaurantEquals(event, restaurant);
				eventRestaurantRepository.deleteAll(event_restaurants);
				break;
		}
		return 0L;
	}

	@PostMapping(value = "/link-event-tag", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	Long linkEventTag(@RequestBody LinkToDTO linkToDTO, @RequestHeader("operation") String operation){
		proxy.resetData();

		Event event = eventRepository.findById(linkToDTO.getSourceId()).get();
		Tag tag = tagRepository.findById(linkToDTO.getDestinationId()).get();

		switch (operation){
			case "CREATE":
				Event_Tag event_tag = new Event_Tag();
				event_tag.setEvent(event);
				event_tag.setTag(tag);
				return eventTagRepository.save(event_tag).getEventTagId();
			case "DELETE":
				List<Event_Tag> event_tags = eventTagRepository.findByEventEqualsAndTagEquals(event, tag);
				eventTagRepository.deleteAll(event_tags);
				break;
		}
		return 0L;
	}

	@PostMapping(value = "/link-restaurant-cuisine", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	Long linkRestaurantCuisine(@RequestBody LinkToDTO linkToDTO, @RequestHeader("operation") String operation){
		proxy.resetData();

		Restaurant restaurant = restaurantRepository.findById(linkToDTO.getSourceId()).get();
		Cuisine cuisine = cuisineRepository.findById(linkToDTO.getDestinationId()).get();

		switch (operation){
			case "CREATE":
				Restaurant_Cuisine restaurant_cuisine = new Restaurant_Cuisine();
				restaurant_cuisine.setRestaurant(restaurant);
				restaurant_cuisine.setCuisine(cuisine);
				return restaurantCuisineRepository.save(restaurant_cuisine).getRestaurantCuisineId();
			case "DELETE":
				List<Restaurant_Cuisine> restaurant_cuisines = restaurantCuisineRepository.findByRestaurantEqualsAndCuisineEquals(restaurant, cuisine);
				restaurantCuisineRepository.deleteAll(restaurant_cuisines);
				break;
		}
		return 0L;
	}

	@PostMapping(value = "/link-event-stage", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	Long linkEventStage(@RequestBody LinkToDTO linkToDTO, @RequestHeader("operation") String operation){
		proxy.resetData();

		Event event = eventRepository.findById(linkToDTO.getSourceId()).get();

		switch (operation){
			case "CREATE":
				Stage stage = stageRepository.findById(linkToDTO.getDestinationId()).get();
				event.setStage(stage);
				return eventRepository.save(event).getEventId();
			case "DELETE":
				event.setStage(null);
				return eventRepository.save(event).getEventId();
		}
		return 0L;
	}

	@PostMapping(value = "/link-event-venue", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	Long linkEventVenue(@RequestBody LinkToDTO linkToDTO, @RequestHeader("operation") String operation){
		proxy.resetData();

		Event event = eventRepository.findById(linkToDTO.getSourceId()).get();

		switch (operation){
			case "CREATE":
				Venue venue = venueRepository.findById(linkToDTO.getDestinationId()).get();
				event.setVenue(venue);
				return eventRepository.save(event).getEventId();
			case "DELETE":
				event.setVenue(null);
				return eventRepository.save(event).getEventId();
		}
		return 0L;
	}

	@PostMapping(value = "/link-restaurant-venue", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	Long linkRestaurantVenue(@RequestBody LinkToDTO linkToDTO, @RequestHeader("operation") String operation){
		proxy.resetData();

		Restaurant restaurant = restaurantRepository.findById(linkToDTO.getSourceId()).get();

		switch (operation){
			case "CREATE":
				Venue venue = venueRepository.findById(linkToDTO.getDestinationId()).get();
				restaurant.setVenue(venue);
				return restaurantRepository.save(restaurant).getRestaurantId();
			case "DELETE":
				restaurant.setVenue(null);
				return restaurantRepository.save(restaurant).getRestaurantId();
		}
		return 0L;
	}

	@PostMapping(value = "/link-stage-venue", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	Long linkStageVenue(@RequestBody LinkToDTO linkToDTO, @RequestHeader("operation") String operation){
		proxy.resetData();

		Stage stage = stageRepository.findById(linkToDTO.getSourceId()).get();

		switch (operation){
			case "CREATE":
				Venue venue = venueRepository.findById(linkToDTO.getDestinationId()).get();
				stage.setVenue(venue);
				return stageRepository.save(stage).getStageId();
			case "DELETE":
				stage.setVenue(null);
				return stageRepository.save(stage).getStageId();
		}
		return 0L;
	}

	@PostMapping(value = "/link-event-image", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	Long linkEventImage(@RequestBody LinkToDTO linkToDTO, @RequestHeader("operation") String operation){
		proxy.resetData();
		proxy.resetImage();

		Event event = eventRepository.findById(linkToDTO.getSourceId()).get();

		switch (operation){
			case "CREATE":
				Image image = imageRepository.findById(linkToDTO.getDestinationId()).get();
				event.setImage(image);
				return eventRepository.save(event).getEventId();
			case "DELETE":
				event.setImage(null);
				return eventRepository.save(event).getEventId();
		}
		return 0L;
	}

	/*
	 * IMAGE Method
	 */
	@PostMapping(path = "/upload-image")
	Long uploadImage(@RequestParam MultipartFile file) throws Exception {
		proxy.resetData();
		proxy.resetImage();

		return imageService.uploadImage(file);
	}

	/*
	 * DUPLICATE Method
	 */
	@PostMapping(value = "/duplicate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Long duplicateEvent(@RequestBody Event event) {
		proxy.resetData();

		Long idToDuplicate = event.getEventId();
		event.setEventId(0);
		Event eventDb = eventRepository.findEventWithVenueAndStageAndImageAndParentEventByEventId(idToDuplicate);

		if (eventDb.isMainEvent()){
			return 0L;
		}

		event.setVenue(eventDb.getVenue());
		event.setStage(eventDb.getStage());
		event.setImage(eventDb.getImage());
		event.setParentEvent(eventDb.getParentEvent());
		event.setHasExhibitor(false);
		event.setHasRestaurant(false);
		event.setHasPresenter(false);
		Long newId = eventRepository.save(event).getEventId();

		// Duplicate eventPresenters
		List<Event_Presenter> newEventPresenters = new ArrayList<>();
		List<Event_Presenter> eventPresenters = eventRepository.findEventWithEventPresentersByEventId(idToDuplicate).getEventPresenters();
		for (Event_Presenter ep : eventPresenters){
			Event_Presenter newEP = new Event_Presenter();
			newEP.setEvent(event);
			newEP.setPresenter(ep.getPresenter());
			newEventPresenters.add(newEP);
		}
		eventPresenterRepository.saveAll(newEventPresenters);

		// Duplicate eventRestaurants
		List<Event_Restaurant> newEventRestaurants = new ArrayList<>();
		List<Event_Restaurant> eventRestaurants = eventRepository.findEventWithEventRestaurantsByEventId(idToDuplicate).getEventRestaurants();
		for (Event_Restaurant er : eventRestaurants){
			Event_Restaurant newER = new Event_Restaurant();
			newER.setEvent(event);
			newER.setRestaurant(er.getRestaurant());
			newEventRestaurants.add(newER);
		}
		eventRestaurantRepository.saveAll(newEventRestaurants);

		// Duplicate eventExhibitors
		List<Event_Exhibitor> newEventExhibitors = new ArrayList<>();
		List<Event_Exhibitor> eventExhibitors = eventRepository.findEventWithEventExhibitorsByEventId(idToDuplicate).getEventExhibitors();
		for (Event_Exhibitor ex : eventExhibitors){
			Event_Exhibitor newEX = new Event_Exhibitor();
			newEX.setEvent(event);
			newEX.setExhibitor(ex.getExhibitor());
			newEventExhibitors.add(newEX);
		}
		eventExhibitorRepository.saveAll(newEventExhibitors);

		// Duplicate eventTags
		List<Event_Tag> newEventTags = new ArrayList<>();
		List<Event_Tag> eventTags = eventRepository.findEventWithEventTagsByEventId(idToDuplicate).getEventTags();
		for (Event_Tag et : eventTags){
			Event_Tag newET = new Event_Tag();
			newET.setEvent(event);
			newET.setTag(et.getTag());
			newEventTags.add(newET);
		}
		eventTagRepository.saveAll(newEventTags);

		return newId;
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
