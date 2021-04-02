package com.qvik.events.web;

import com.qvik.events.infra.response.ResponseMessage;
import com.qvik.events.modules.event.Event;
import com.qvik.events.modules.event.EventRepository;
import com.qvik.events.modules.exhibitor.Exhibitor;
import com.qvik.events.modules.exhibitor.ExhibitorRepository;
import com.qvik.events.modules.image.ImageRepository;
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

	private final ImageRepository imageRepository;
	private final ImageService imageService;

	private final EventRepository eventRepository;
	private final ExhibitorRepository exhibitorRepository;
	private final PresenterRepository presenterRepository;
	private final RestaurantRepository restaurantRepository;
	private final TagRepository tagRepository;
	private final StageRepository stageRepository;
	private final VenueRepository venueRepository;


	@PostMapping(value = "/events", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Long newEvent(@RequestBody Event event) {
		return eventRepository.save(event).getEventId();
	}

	@PostMapping(value = "/exhibitors", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Long newExhibitor(@RequestBody Exhibitor exhibitor) {
		return exhibitorRepository.save(exhibitor).getExhibitorId();
	}

	@PostMapping(value = "/presenters", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Long newPresenter(@RequestBody Presenter presenter) {
		return presenterRepository.save(presenter).getPresenterId();
	}

	@PostMapping(value = "/restaurants", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Long newRestaurant(@RequestBody Restaurant restaurant) {
		return restaurantRepository.save(restaurant).getRestaurantId();
	}

	@PostMapping(value = "/tags", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Long newTag(@RequestBody Tag tag) {
		return tagRepository.save(tag).getTagId();
	}

	@PostMapping(value = "/stages", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Long newStage(@RequestBody Stage stage) {
		return stageRepository.save(stage).getStageId();
	}

	@PostMapping(value = "/venues", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Long newVenue(@RequestBody Venue venue) {
		return venueRepository.save(venue).getVenueId();
	}

	/*	  
	 * Add New Main Event 
	 */

	/*
	 * Add New Sub Event 
	 */

	/*
	 * Upload Image
	 */
	@PostMapping(path = "/upload-image")
	Long uploadImage(@RequestParam MultipartFile file) throws Exception {
		return imageService.uploadImage(file);
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
