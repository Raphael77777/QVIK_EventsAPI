package com.qvik.events.web;

import com.qvik.events.EventsApplication;
import com.qvik.events.infra.cache.ProxyCache;
import com.qvik.events.infra.response.ResponseMessage;
import com.qvik.events.infra.response.admindto.*;
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
import com.qvik.events.modules.tag.EventTagRepository;
import com.qvik.events.modules.tag.Event_Tag;
import com.qvik.events.modules.tag.Tag;
import com.qvik.events.modules.tag.TagRepository;
import com.qvik.events.modules.venue.Venue;
import com.qvik.events.modules.venue.VenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
	ResponseMessage createEvent(@RequestBody EventADTO event) {
		proxy.resetData();

		if (event.getEvent().isMainEvent()){
			Event e = eventRepository.findEventByIsMainEventTrue();
			if (e != null){
				return badRequestErrorHandling(new Exception("Cannot create main event! Try to modify the existing event [n°"+e.getEventId()+"]"));
			}
		}

		event.getEvent().setHasExhibitor(!event.getLinkEventExhibitors().isEmpty());
		event.getEvent().setHasPresenter(!event.getLinkEventPresenters().isEmpty());
		event.getEvent().setHasRestaurant(!event.getLinkEventRestaurants().isEmpty());

		Long id = eventRepository.save(event.getEvent()).getEventId();

		if (event.getEvent().isMainEvent()){
			// LINK EVENT-VENUE
			LinkToDTO link = event.getLinkEventVenue();
			if (link != null && link.getOperation() != null){
				link.setSourceId(id);
				linkEventVenue(link);
			}else {
				//SET NO VENUE
				Long venueId = venueRepository.findByName("No Venue").getVenueId();
				LinkToDTO linkToDTO = new LinkToDTO();
				linkToDTO.setSourceId(id);
				linkToDTO.setDestinationId(venueId);
				linkToDTO.setOperation("CREATE");
				linkEventVenue(linkToDTO);
			}
		}else {
			// LINK EVENT-STAGE
			LinkToDTO link = event.getLinkEventStage();
			if (link != null && link.getOperation() != null){
				link.setSourceId(id);
				linkEventStage(link);
			}else {
				//SET NO STAGE
				Long stageId = stageRepository.findByName("No Stage").getStageId();
				LinkToDTO linkToDTO = new LinkToDTO();
				linkToDTO.setSourceId(id);
				linkToDTO.setDestinationId(stageId);
				linkToDTO.setOperation("CREATE");
				linkEventStage(linkToDTO);
			}
		}

		// LINK EVENT-IMAGE
		LinkToDTO link = event.getLinkEventImage();
		if (link != null && link.getOperation() != null){
			link.setSourceId(id);
			linkEventImage(link);
		}

		// LINK EVENT-EXHIBITORS
		List<LinkToDTO> linksEX = event.getLinkEventExhibitors();
		if (linksEX != null){
			for (LinkToDTO l : linksEX){
				if (l != null && l.getOperation() != null){
					l.setSourceId(id);
					linkEventExhibitor(l);
				}
			}
		}

		// LINK EVENT-PRESENTERS
		List<LinkToDTO> linksPR = event.getLinkEventPresenters();
		if (linksPR != null){
			for (LinkToDTO l : linksPR){
				if (l != null && l.getOperation() != null){
					l.setSourceId(id);
					linkEventPresenter(l);
				}
			}
		}

		// LINK EVENT-RESTAURANTS
		List<LinkToDTO> linksRE = event.getLinkEventRestaurants();
		if (linksRE != null){
			for (LinkToDTO l : linksRE){
				if (l != null && l.getOperation() != null){
					l.setSourceId(id);
					linkEventRestaurant(l);
				}
			}
		}

		// LINK EVENT-TAG
		List<LinkToDTO> linksTA = event.getLinkEventTags();
		if (linksTA != null){
			for (LinkToDTO l : linksTA){
				if (l != null && l.getOperation() != null){
					l.setSourceId(id);
					linkEventTag(l);
				}
			}
		}

		return convertToResponseMessage(id, "CREATE");
	}

	@PostMapping(value = "/exhibitors", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseMessage createExhibitor(@RequestBody Exhibitor exhibitor) {
		proxy.resetData();

		return convertToResponseMessage(exhibitorRepository.save(exhibitor).getExhibitorId(), "CREATE");
	}

	@PostMapping(value = "/presenters", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseMessage createPresenter(@RequestBody Presenter presenter) {
		proxy.resetData();

		return convertToResponseMessage(presenterRepository.save(presenter).getPresenterId(), "CREATE");
	}

	@PostMapping(value = "/restaurants", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseMessage createRestaurant(@RequestBody RestaurantADTO restaurant) {
		proxy.resetData();

		Long id = restaurantRepository.save(restaurant.getRestaurant()).getRestaurantId();

		// LINK RESTAURANT-VENUE
		LinkToDTO link = restaurant.getLinkRestaurantVenue();
		if (link != null && link.getOperation() != null){
			link.setSourceId(id);
			linkRestaurantVenue(link);
		}else {
			//SET NO VENUE
			Long venueId = venueRepository.findByName("No Venue").getVenueId();
			LinkToDTO linkToDTO = new LinkToDTO();
			linkToDTO.setSourceId(id);
			linkToDTO.setDestinationId(venueId);
			linkToDTO.setOperation("CREATE");
			linkRestaurantVenue(linkToDTO);
		}

		// LINK RESTAURANT-CUISINES
		List<LinkToDTO> links = restaurant.getLinkRestaurantCuisines();
		if (links != null){
			for (LinkToDTO l : links){
				if (l != null && l.getOperation() != null){
					l.setSourceId(id);
					linkRestaurantCuisine(l);
				}
			}
		}

		return convertToResponseMessage(id, "CREATE");
	}

	@PostMapping(value = "/tags", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseMessage createTag(@RequestBody Tag tag) {
		proxy.resetData();

		return convertToResponseMessage(tagRepository.save(tag).getTagId(), "CREATE");
	}

	@PostMapping(value = "/cuisines", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseMessage createCuisine(@RequestBody Cuisine cuisine) {
		proxy.resetData();

		return convertToResponseMessage(cuisineRepository.save(cuisine).getCuisineId(), "CREATE");
	}

	@PostMapping(value = "/stages", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseMessage createStage(@RequestBody StageADTO stage) {
		proxy.resetData();

		Long id = stageRepository.save(stage.getStage()).getStageId();

		// LINK STAGE-VENUE
		LinkToDTO link = stage.getLinkStageVenue();
		if (link != null && link.getOperation() != null){
			link.setSourceId(id);
			linkStageVenue(link);
		}else {
			//SET NO VENUE
			Long venueId = venueRepository.findByName("No Venue").getVenueId();
			LinkToDTO linkToDTO = new LinkToDTO();
			linkToDTO.setSourceId(id);
			linkToDTO.setDestinationId(venueId);
			linkToDTO.setOperation("CREATE");
			linkStageVenue(linkToDTO);
		}

		return convertToResponseMessage(id, "CREATE");
	}

	@PostMapping(value = "/venues", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseMessage createVenue(@RequestBody Venue venue) {
		proxy.resetData();

		return convertToResponseMessage(venueRepository.save(venue).getVenueId(), "CREATE");
	}

	/*
	 * UPDATE Methods
	 */
	@PutMapping(value = "/events/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseMessage updateEvent(@RequestBody EventADTO event, @PathVariable Long id) {
		proxy.resetData();

		return convertToResponseMessage(eventRepository.findById(id)
				.map(eventDb -> {
					Event e = event.getEvent();
					eventDb.setStartDate(e.getStartDate());
					eventDb.setStartTime(e.getStartTime());
					eventDb.setEndDate(e.getEndDate());
					eventDb.setEndTime(e.getEndTime());
					eventDb.setTitle(e.getTitle());
					eventDb.setShortDescription(e.getShortDescription());
					eventDb.setFullDescription(e.getFullDescription());
					eventDb.setActive(e.isActive());
					eventDb.setMainEvent(e.isMainEvent());
					Long eventId = eventRepository.save(eventDb).getEventId();

					if (event.getEvent().isMainEvent()){
						// LINK EVENT-VENUE
						LinkToDTO link = event.getLinkEventVenue();
						if (link != null && link.getOperation() != null){
							link.setSourceId(eventId);
							linkEventVenue(link);
						}
					}else {
						// LINK EVENT-STAGE
						LinkToDTO link = event.getLinkEventStage();
						if (link != null && link.getOperation() != null){
							link.setSourceId(eventId);
							linkEventStage(link);
						}
					}

					// LINK EVENT-IMAGE
					LinkToDTO link = event.getLinkEventImage();
					if (link != null && link.getOperation() != null){
						link.setSourceId(eventId);
						linkEventImage(link);
					}

					// LINK EVENT-EXHIBITORS
					List<LinkToDTO> linksEX = event.getLinkEventExhibitors();
					if (linksEX != null){
						for (LinkToDTO l : linksEX){
							if (l != null && l.getOperation() != null){
								l.setSourceId(eventId);
								linkEventExhibitor(l);
							}
						}
					}

					// LINK EVENT-PRESENTERS
					List<LinkToDTO> linksPR = event.getLinkEventPresenters();
					if (linksPR != null){
						for (LinkToDTO l : linksPR){
							if (l != null && l.getOperation() != null){
								l.setSourceId(eventId);
								linkEventPresenter(l);
							}
						}
					}

					// LINK EVENT-RESTAURANTS
					List<LinkToDTO> linksRE = event.getLinkEventRestaurants();
					if (linksRE != null){
						for (LinkToDTO l : linksRE){
							if (l != null && l.getOperation() != null){
								l.setSourceId(eventId);
								linkEventRestaurant(l);
							}
						}
					}

					// LINK EVENT-TAG
					List<LinkToDTO> linksTA = event.getLinkEventTags();
					if (linksTA != null){
						for (LinkToDTO l : linksTA){
							if (l != null && l.getOperation() != null){
								l.setSourceId(eventId);
								linkEventTag(l);
							}
						}
					}

					return eventId;
				})
				.orElseGet(() -> {
					event.getEvent().setEventId(id);
					return eventRepository.save(event.getEvent()).getEventId();
				}), "UPDATE");
	}

	@PutMapping(value = "/exhibitors/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseMessage updateExhibitor(@RequestBody Exhibitor exhibitor, @PathVariable Long id) {
		proxy.resetData();

		return convertToResponseMessage(exhibitorRepository.findById(id)
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
				}), "UPDATE");
	}

	@PutMapping(value = "/presenters/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseMessage updatePresenter(@RequestBody Presenter presenter, @PathVariable Long id) {
		proxy.resetData();

		return convertToResponseMessage(presenterRepository.findById(id)
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
				}), "UPDATE");
	}

	@PutMapping(value = "/restaurants/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseMessage updateRestaurant(@RequestBody RestaurantADTO restaurant, @PathVariable Long id) {
		proxy.resetData();

		return convertToResponseMessage(restaurantRepository.findById(id)
				.map(restaurantDb -> {
					Restaurant r = restaurant.getRestaurant();
					restaurantDb.setName(r.getName());
					restaurantDb.setLocation(r.getLocation());
					restaurantDb.setOpenTime(r.getOpenTime());
					restaurantDb.setCloseTime(r.getCloseTime());
					restaurantDb.setShortDescription(r.getShortDescription());
					restaurantDb.setFullDescription(r.getFullDescription());
					Long restaurantId = restaurantRepository.save(restaurantDb).getRestaurantId();

					// LINK RESTAURANT-VENUE
					LinkToDTO link = restaurant.getLinkRestaurantVenue();
					if (link != null && link.getOperation() != null){
						link.setSourceId(restaurantId);
						linkRestaurantVenue(link);
					}

					// LINK RESTAURANT-CUISINES
					List<LinkToDTO> links = restaurant.getLinkRestaurantCuisines();
					if (links != null){
						for (LinkToDTO l : links){
							if (l != null && l.getOperation() != null){
								l.setSourceId(restaurantId);
								linkRestaurantCuisine(l);
							}
						}
					}

					return restaurantId;
				})
				.orElseGet(() -> {
					restaurant.getRestaurant().setRestaurantId(id);
					return restaurantRepository.save(restaurant.getRestaurant()).getRestaurantId();
				}), "UPDATE");
	}

	@PutMapping(value = "/stages/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseMessage updateStage(@RequestBody StageADTO stage, @PathVariable Long id) {
		proxy.resetData();

		return convertToResponseMessage(stageRepository.findById(id)
				.map(stageDb -> {
					Stage s = stage.getStage();
					stageDb.setName(s.getName());
					stageDb.setLocation(s.getLocation());
					stageDb.setCapacity(s.getCapacity());
					stageDb.setType(s.getType());
					Long stageId = stageRepository.save(stageDb).getStageId();

					// LINK STAGE-VENUE
					LinkToDTO link = stage.getLinkStageVenue();
					if (link != null && link.getOperation() != null){
						link.setSourceId(stageId);
						linkStageVenue(link);
					}

					return stageId;
				})
				.orElseGet(() -> {
					stage.getStage().setStageId(id);
					return stageRepository.save(stage.getStage()).getStageId();
				}), "UPDATE");
	}

	@PutMapping(value = "/tags/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseMessage updateTag(@RequestBody Tag tag, @PathVariable Long id) {
		proxy.resetData();

		return convertToResponseMessage(tagRepository.findById(id)
				.map(tagDb -> {
					tagDb.setName(tag.getName());
					return tagRepository.save(tagDb).getTagId();
				})
				.orElseGet(() -> {
					tag.setTagId(id);
					return tagRepository.save(tag).getTagId();
				}), "UPDATE");
	}

	@PutMapping(value = "/cuisines/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseMessage updateCuisine(@RequestBody Cuisine cuisine, @PathVariable Long id) {
		proxy.resetData();

		return convertToResponseMessage(cuisineRepository.findById(id)
				.map(cuisineDb -> {
					cuisineDb.setName(cuisine.getName());
					return cuisineRepository.save(cuisineDb).getCuisineId();
				})
				.orElseGet(() -> {
					cuisine.setCuisineId(id);
					return cuisineRepository.save(cuisine).getCuisineId();
				}), "UPDATE");
	}

	@PutMapping(value = "/venues/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseMessage updateVenue(@RequestBody Venue venue, @PathVariable Long id) {
		proxy.resetData();

		return convertToResponseMessage(venueRepository.findById(id)
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
				}), "UPDATE");
	}

	/*
	 * DELETE Methods
	 */
	@DeleteMapping(value = "/events/{id}")
	ResponseMessage deleteEvent(@PathVariable Long id) {
		proxy.resetData();

		Event event = eventRepository.findById(id).get();

		if (event.isMainEvent()){
			return badRequestErrorHandling(new Exception("Cannot delete main event! Try to modify the existing event [n°"+event.getEventId()+"]"));
		}

		eventRepository.deleteById(id);

		return convertToResponseMessage(id, "DELETE");
	}

	@DeleteMapping(value = "/exhibitors/{id}")
	ResponseMessage deleteExhibitor(@PathVariable Long id) {
		proxy.resetData();

		exhibitorRepository.deleteById(id);
		return convertToResponseMessage(id, "DELETE");
	}

	@DeleteMapping(value = "/presenters/{id}")
	ResponseMessage deletePresenter(@PathVariable Long id) {
		proxy.resetData();

		presenterRepository.deleteById(id);
		return convertToResponseMessage(id, "DELETE");
	}

	@DeleteMapping(value = "/restaurants/{id}")
	ResponseMessage deleteRestaurant(@PathVariable Long id) {
		proxy.resetData();

		restaurantRepository.deleteById(id);
		return convertToResponseMessage(id, "DELETE");
	}

	@DeleteMapping(value = "/tags/{id}")
	ResponseMessage deleteTag(@PathVariable Long id) {
		proxy.resetData();

		tagRepository.deleteById(id);
		return convertToResponseMessage(id, "DELETE");
	}

	@DeleteMapping(value = "/cuisines/{id}")
	ResponseMessage deleteCuisine(@PathVariable Long id) {
		proxy.resetData();

		cuisineRepository.deleteById(id);
		return convertToResponseMessage(id, "DELETE");
	}

	@DeleteMapping(value = "/stages/{id}")
	ResponseMessage deleteStage(@PathVariable Long id) {
		proxy.resetData();

		Stage stage = stageRepository.findById(id).get();

		//ON DELETE SET NULL
		List<Event> events = eventRepository.findEventsWithStageByStage(stage);
		for (Event event : events){
			event.setStage(null);
		}
		eventRepository.saveAll(events);

		stageRepository.delete(stage);
		return convertToResponseMessage(id, "DELETE");
	}

	@DeleteMapping(value = "/venues/{id}")
	ResponseMessage deleteVenue(@PathVariable Long id) {
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
		return convertToResponseMessage(id, "DELETE");
	}

	@DeleteMapping(value = "/images/{id}")
	ResponseMessage deleteImage(@PathVariable Long id) {
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
		return convertToResponseMessage(id, "DELETE");
	}

	@DeleteMapping(value = "/deleteAll/{credentials}")
	ResponseMessage deleteAll(@PathVariable String credentials) {
		proxy.resetData();
		proxy.resetImage();

		if (credentials.equals(EventsApplication.KEY_DeleteALL)){
			eventExhibitorRepository.deleteAll();
			eventPresenterRepository.deleteAll();
			eventRepository.deleteAll();
			eventRestaurantRepository.deleteAll();
			eventTagRepository.deleteAll();
			exhibitorRepository.deleteAll();
			presenterRepository.deleteAll();
			restaurantRepository.deleteAll();
			restaurantCuisineRepository.deleteAll();
			stageRepository.deleteAll();
			venueRepository.deleteAll();
			tagRepository.deleteAll();
			imageRepository.deleteAll();
			cuisineRepository.deleteAll();

			return convertToResponseMessage(0L, "DELETE ALL");
		}

		return badRequestErrorHandling(new Exception("Cannot delete all data! Please contact admin"));
	}

	/*
	 * LINK Methods
	 */
	@PostMapping(value = "/link-event-exhibitor", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseMessage linkEventExhibitor(@RequestBody LinkToDTO linkToDTO){
		proxy.resetData();

		Event event = eventRepository.findById(linkToDTO.getSourceId()).get();
		Exhibitor exhibitor = exhibitorRepository.findById(linkToDTO.getDestinationId()).get();

		List<Event_Exhibitor> event_exhibitors = eventExhibitorRepository.findByEventEqualsAndExhibitorEquals(event, exhibitor);

		switch (linkToDTO.getOperation()){
			case "CREATE":
				if (event_exhibitors.isEmpty()) {
					Event_Exhibitor event_exhibitor = new Event_Exhibitor();
					event_exhibitor.setEvent(event);
					event_exhibitor.setExhibitor(exhibitor);
					return convertToResponseMessage(eventExhibitorRepository.save(event_exhibitor).getEventExhibitorId(), "CREATE LINK");
				}else {
					return null;
				}
			case "DELETE":
				eventExhibitorRepository.deleteAll(event_exhibitors);
				return convertToResponseMessage(event.getEventId(), "DELETE LINK");
		}
		return badRequestErrorHandling(new Exception("Cannot link/unlink. Caused by : Wrong operation!"));
	}

	@PostMapping(value = "/link-event-presenter", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseMessage linkEventPresenter(@RequestBody LinkToDTO linkToDTO){
		proxy.resetData();

		Event event = eventRepository.findById(linkToDTO.getSourceId()).get();
		Presenter presenter = presenterRepository.findById(linkToDTO.getDestinationId()).get();

		List<Event_Presenter> event_presenters = eventPresenterRepository.findByEventEqualsAndPresenterEquals(event, presenter);

		switch (linkToDTO.getOperation()){
			case "CREATE":
				if (event_presenters.isEmpty()) {
					Event_Presenter event_presenter = new Event_Presenter();
					event_presenter.setEvent(event);
					event_presenter.setPresenter(presenter);
					return convertToResponseMessage(eventPresenterRepository.save(event_presenter).getEventPresenterId(), "CREATE LINK");
				}else {
					return null;
				}
			case "DELETE":
				eventPresenterRepository.deleteAll(event_presenters);
				return convertToResponseMessage(event.getEventId(), "DELETE LINK");
		}
		return badRequestErrorHandling(new Exception("Cannot link/unlink. Caused by : Wrong operation!"));
	}

	@PostMapping(value = "/link-event-restaurant", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseMessage linkEventRestaurant(@RequestBody LinkToDTO linkToDTO){
		proxy.resetData();

		Event event = eventRepository.findById(linkToDTO.getSourceId()).get();
		Restaurant restaurant = restaurantRepository.findById(linkToDTO.getDestinationId()).get();

		List<Event_Restaurant> event_restaurants = eventRestaurantRepository.findByEventEqualsAndRestaurantEquals(event, restaurant);

		switch (linkToDTO.getOperation()){
			case "CREATE":
				if (event_restaurants.isEmpty()) {
					Event_Restaurant event_restaurant = new Event_Restaurant();
					event_restaurant.setEvent(event);
					event_restaurant.setRestaurant(restaurant);
					return convertToResponseMessage(eventRestaurantRepository.save(event_restaurant).getEventRestaurantId(), "CREATE LINK");
				}else {
					return null;
				}
			case "DELETE":
				eventRestaurantRepository.deleteAll(event_restaurants);
				return convertToResponseMessage(event.getEventId(), "DELETE LINK");
		}
		return badRequestErrorHandling(new Exception("Cannot link/unlink. Caused by : Wrong operation!"));
	}

	@PostMapping(value = "/link-event-tag", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseMessage linkEventTag(@RequestBody LinkToDTO linkToDTO){
		proxy.resetData();

		Event event = eventRepository.findById(linkToDTO.getSourceId()).get();
		Tag tag = tagRepository.findById(linkToDTO.getDestinationId()).get();

		List<Event_Tag> event_tags = eventTagRepository.findByEventEqualsAndTagEquals(event, tag);

		switch (linkToDTO.getOperation()){
			case "CREATE":
				if (event_tags.isEmpty()) {
					Event_Tag event_tag = new Event_Tag();
					event_tag.setEvent(event);
					event_tag.setTag(tag);
					return convertToResponseMessage(eventTagRepository.save(event_tag).getEventTagId(), "CREATE LINK");
				}else {
					return null;
				}
			case "DELETE":
				eventTagRepository.deleteAll(event_tags);
				return convertToResponseMessage(event.getEventId(), "DELETE LINK");
		}
		return badRequestErrorHandling(new Exception("Cannot link/unlink. Caused by : Wrong operation!"));
	}

	@PostMapping(value = "/link-restaurant-cuisine", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseMessage linkRestaurantCuisine(@RequestBody LinkToDTO linkToDTO){
		proxy.resetData();

		Restaurant restaurant = restaurantRepository.findById(linkToDTO.getSourceId()).get();
		Cuisine cuisine = cuisineRepository.findById(linkToDTO.getDestinationId()).get();

		List<Restaurant_Cuisine> restaurant_cuisines = restaurantCuisineRepository.findByRestaurantEqualsAndCuisineEquals(restaurant, cuisine);

		switch (linkToDTO.getOperation()){
			case "CREATE":
				if (restaurant_cuisines.isEmpty()) {
					Restaurant_Cuisine restaurant_cuisine = new Restaurant_Cuisine();
					restaurant_cuisine.setRestaurant(restaurant);
					restaurant_cuisine.setCuisine(cuisine);
					return convertToResponseMessage(restaurantCuisineRepository.save(restaurant_cuisine).getRestaurantCuisineId(), "CREATE LINK");
				}else {
					return null;
				}
			case "DELETE":
				restaurantCuisineRepository.deleteAll(restaurant_cuisines);
				return convertToResponseMessage(restaurant.getRestaurantId(), "DELETE LINK");
		}
		return badRequestErrorHandling(new Exception("Cannot link/unlink. Caused by : Wrong operation!"));
	}

	@PostMapping(value = "/link-event-stage", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseMessage linkEventStage(@RequestBody LinkToDTO linkToDTO){
		proxy.resetData();

		Event event = eventRepository.findById(linkToDTO.getSourceId()).get();

		switch (linkToDTO.getOperation()){
			case "CREATE":
				Stage stage = stageRepository.findById(linkToDTO.getDestinationId()).get();
				event.setStage(stage);
				return convertToResponseMessage(eventRepository.save(event).getEventId(), "CREATE LINK");
			case "DELETE":
				Stage s = stageRepository.findByName("No Stage");
				event.setStage(s);
				return convertToResponseMessage(eventRepository.save(event).getEventId(), "DELETE LINK");
		}
		return badRequestErrorHandling(new Exception("Cannot link/unlink. Caused by : Wrong operation!"));
	}

	@PostMapping(value = "/link-event-venue", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseMessage linkEventVenue(@RequestBody LinkToDTO linkToDTO){
		proxy.resetData();

		Event event = eventRepository.findById(linkToDTO.getSourceId()).get();

		switch (linkToDTO.getOperation()){
			case "CREATE":
				Venue venue = venueRepository.findById(linkToDTO.getDestinationId()).get();
				event.setVenue(venue);
				return convertToResponseMessage(eventRepository.save(event).getEventId(), "CREATE LINK");
			case "DELETE":
				Venue v = venueRepository.findByName("No Venue");
				event.setVenue(v);
				return convertToResponseMessage(eventRepository.save(event).getEventId(), "DELETE LINK");
		}
		return badRequestErrorHandling(new Exception("Cannot link/unlink. Caused by : Wrong operation!"));
	}

	@PostMapping(value = "/link-restaurant-venue", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseMessage linkRestaurantVenue(@RequestBody LinkToDTO linkToDTO){
		proxy.resetData();

		Restaurant restaurant = restaurantRepository.findById(linkToDTO.getSourceId()).get();

		switch (linkToDTO.getOperation()){
			case "CREATE":
				Venue venue = venueRepository.findById(linkToDTO.getDestinationId()).get();
				restaurant.setVenue(venue);
				return convertToResponseMessage(restaurantRepository.save(restaurant).getRestaurantId(), "CREATE LINK");
			case "DELETE":
				Venue v = venueRepository.findByName("No Venue");
				restaurant.setVenue(v);
				return convertToResponseMessage(restaurantRepository.save(restaurant).getRestaurantId(), "DELETE LINK");
		}
		return badRequestErrorHandling(new Exception("Cannot link/unlink. Caused by : Wrong operation!"));
	}

	@PostMapping(value = "/link-stage-venue", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseMessage linkStageVenue(@RequestBody LinkToDTO linkToDTO){
		proxy.resetData();

		Stage stage = stageRepository.findById(linkToDTO.getSourceId()).get();

		switch (linkToDTO.getOperation()){
			case "CREATE":
				Venue venue = venueRepository.findById(linkToDTO.getDestinationId()).get();
				stage.setVenue(venue);
				return convertToResponseMessage(stageRepository.save(stage).getStageId(), "CREATE LINK");
			case "DELETE":
				Venue v = venueRepository.findByName("No Venue");
				stage.setVenue(v);
				return convertToResponseMessage(stageRepository.save(stage).getStageId(), "DELETE LINK");
		}
		return badRequestErrorHandling(new Exception("Cannot link/unlink. Caused by : Wrong operation!"));
	}

	@PostMapping(value = "/link-event-image", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseMessage linkEventImage(@RequestBody LinkToDTO linkToDTO){
		proxy.resetData();
		proxy.resetImage();

		Event event = eventRepository.findById(linkToDTO.getSourceId()).get();

		switch (linkToDTO.getOperation()){
			case "CREATE":
				Image image = imageRepository.findById(linkToDTO.getDestinationId()).get();
				event.setImage(image);
				return convertToResponseMessage(eventRepository.save(event).getEventId(), "CREATE LINK");
			case "DELETE":
				event.setImage(null);
				return convertToResponseMessage(eventRepository.save(event).getEventId(), "DELETE LINK");
		}
		return badRequestErrorHandling(new Exception("Cannot link/unlink. Caused by : Wrong operation!"));
	}

	/*
	 * IMAGE Method
	 */
	@PostMapping(path = "/upload-image")
	ResponseMessage uploadImage(@RequestParam MultipartFile file) throws Exception {
		proxy.resetData();
		proxy.resetImage();

		return convertToResponseMessage(imageService.uploadImage(file), "UPLOAD");
	}

	/*
	 * DUPLICATE Method
	 */
	@PostMapping(value = "/duplicate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseMessage duplicateEvent(@RequestBody Event event) {
		proxy.resetData();

		Long idToDuplicate = event.getEventId();
		event.setEventId(0);
		Event eventDb = eventRepository.findEventWithVenueAndStageAndImageAndParentEventByEventId(idToDuplicate);

		if (eventDb.isMainEvent()){
			return badRequestErrorHandling(new Exception("Cannot duplicate main event!"));
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

		return convertToResponseMessage(newId, "DUPLICATE");
	}

	/*
	 * Convert Data to ResponseMessage.class
	 */
	private ResponseMessage convertToResponseMessage(Long id, String transaction) {
		ResponseMessage message = new ResponseMessage(HttpStatus.OK);
		message.add(new AdminResponseDTO(id, transaction));
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
