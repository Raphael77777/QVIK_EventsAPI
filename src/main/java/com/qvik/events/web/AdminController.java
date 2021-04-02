package com.qvik.events.web;

import com.qvik.events.infra.response.ResponseMessage;
import com.qvik.events.modules.event.Event;
import com.qvik.events.modules.event.EventRepository;
import com.qvik.events.modules.exhibitor.Exhibitor;
import com.qvik.events.modules.exhibitor.ExhibitorRepository;
import com.qvik.events.modules.image.ImageService;
import com.qvik.events.modules.presenter.Presenter;
import com.qvik.events.modules.presenter.PresenterRepository;
import com.qvik.events.modules.restaurant.Restaurant;
import com.qvik.events.modules.restaurant.RestaurantRepository;
import com.qvik.events.modules.stage.Stage;
import com.qvik.events.modules.stage.StageRepository;
import com.qvik.events.modules.tag.Tag;
import com.qvik.events.modules.tag.TagRepository;
import com.qvik.events.modules.venue.Venue;
import com.qvik.events.modules.venue.VenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
	private final StageRepository stageRepository;
	private final VenueRepository venueRepository;

	/*
	 * CREATE Methods
	 */
	@PostMapping(value = "/events", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Long createEvent(@RequestBody Event event) {
		event.setHasExhibitor(false);
		event.setHasRestaurant(false);
		event.setHasPresenter(false);
		return eventRepository.save(event).getEventId();
	}

	@PostMapping(value = "/exhibitors", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Long createExhibitor(@RequestBody Exhibitor exhibitor) {
		return exhibitorRepository.save(exhibitor).getExhibitorId();
	}

	@PostMapping(value = "/presenters", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Long createPresenter(@RequestBody Presenter presenter) {
		return presenterRepository.save(presenter).getPresenterId();
	}

	@PostMapping(value = "/restaurants", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Long createRestaurant(@RequestBody Restaurant restaurant) {
		return restaurantRepository.save(restaurant).getRestaurantId();
	}

	@PostMapping(value = "/tags", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Long createTag(@RequestBody Tag tag) {
		return tagRepository.save(tag).getTagId();
	}

	@PostMapping(value = "/stages", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Long createStage(@RequestBody Stage stage) {
		return stageRepository.save(stage).getStageId();
	}

	@PostMapping(value = "/venues", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Long createVenue(@RequestBody Venue venue) {
		return venueRepository.save(venue).getVenueId();
	}

	/*
	 * UPDATE Methods
	 */
	@PutMapping(value = "/events/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	Long updateEvent(@RequestBody Event event, @PathVariable Long id) {
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

	@PutMapping(value = "/venues/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	Long updateVenue(@RequestBody Venue venue, @PathVariable Long id) {
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
		//TODO: On delete set NULL
		eventRepository.deleteById(id);
	}

	@DeleteMapping(value = "/exhibitors/{id}")
	void deleteExhibitor(@PathVariable Long id) {
		//TODO: On delete set NULL
		exhibitorRepository.deleteById(id);
	}

	@DeleteMapping(value = "/presenters/{id}")
	void deletePresenter(@PathVariable Long id) {
		//TODO: On delete set NULL
		presenterRepository.deleteById(id);
	}

	@DeleteMapping(value = "/restaurants/{id}")
	void deleteRestaurant(@PathVariable Long id) {
		//TODO: On delete set NULL
		restaurantRepository.deleteById(id);
	}

	@DeleteMapping(value = "/tags/{id}")
	void deleteTag(@PathVariable Long id) {
		//TODO: On delete set NULL
		tagRepository.deleteById(id);
	}

	@DeleteMapping(value = "/stages/{id}")
	void deleteStage(@PathVariable Long id) {
		//TODO: On delete set NULL
		stageRepository.deleteById(id);
	}

	@DeleteMapping(value = "/venues/{id}")
	void deleteVenue(@PathVariable Long id) {
		//TODO: On delete set NULL
		venueRepository.deleteById(id);
	}

	/*
	 * LINK Methods
	 */
	//TODO: Implement methods

	/*
	 * IMAGE Method
	 */
	@PostMapping(path = "/upload-image")
	Long uploadImage(@RequestParam MultipartFile file) throws Exception {
		return imageService.uploadImage(file);
	}

	/*
	 * DUPLICATE Method
	 */
	//TODO: Implement method

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
