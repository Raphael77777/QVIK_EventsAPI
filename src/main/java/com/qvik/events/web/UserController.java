package com.qvik.events.web;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qvik.events.domain.EventExhibitorRepository;
import com.qvik.events.domain.EventPresenterRepository;
import com.qvik.events.domain.EventRepository;
import com.qvik.events.domain.EventRestaurantRepository;
import com.qvik.events.domain.EventStageRepository;
import com.qvik.events.domain.EventVenueRepository;
import com.qvik.events.domain.ExhibitorRepository;
import com.qvik.events.domain.PresenterRepository;
import com.qvik.events.domain.RestaurantRepository;
import com.qvik.events.domain.StageRepository;
import com.qvik.events.domain.VenueRepository;

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

	@GetMapping(path = "/")
	public String root() throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(eventRepository.findAll());
	}

	@GetMapping(path = "/events")
	public String events() throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(eventRepository.findAll());
	}

	@GetMapping(path = "/events-info")
	public String eventsInfo(@RequestParam(name = "eventId") Integer eventId) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(eventRepository.findAll());
	}

	@GetMapping(path = "/events-date")
	public String eventsDate(@RequestParam(name = "startDate") Date startDate) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(eventRepository.findAll());
	}

	@GetMapping(path = "/events-stage")
	public String eventsStage(@RequestParam(name = "eventId") Integer eventId) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(eventRepository.findAll());
	}

	@GetMapping(path = "/events-venue")
	public String eventsVenue(@RequestParam(name = "eventId") Integer eventId) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(eventRepository.findAll());
	}

	@GetMapping(path = "/events-exhibitor")
	public String eventsExhibitor(@RequestParam(name = "eventId") Integer eventId) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(eventRepository.findAll());
	}

	@GetMapping(path = "/events-presenter")
	public String eventsPresenter(@RequestParam(name = "eventId") Integer eventId) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(eventRepository.findAll());
	}

	@GetMapping(path = "/events-description")
	public String eventsDescription(@RequestParam(name = "eventId") Integer eventId) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(eventRepository.findAll());
	}

	@GetMapping(path = "/events-restaurant")
	public String eventsRestaurant(@RequestParam(name = "eventId") Integer eventId) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(eventRepository.findAll());
	}

	@GetMapping(path = "/venues-info")
	public String venusInfo(@RequestParam(name = "venueId") Integer venueId) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(eventRepository.findAll());
	}

	@GetMapping(path = "/venues-stage")
	public String venusStage(@RequestParam(name = "venueId") Integer venueId) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(eventRepository.findAll());
	}

	@GetMapping(path = "/venues-restaurant")
	public String venusRestaurant(@RequestParam(name = "venueId") Integer venueId) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(eventRepository.findAll());
	}

	@GetMapping(path = "/stages-info")
	public String stagesInfo(@RequestParam(name = "stageId") Integer stageId) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(eventRepository.findAll());
	}

	@GetMapping(path = "/exhibitors-info")
	public String exhibitorsInfo(@RequestParam(name = "exhibitorId") Integer exhibitorId)
			throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(eventRepository.findAll());
	}

	@GetMapping(path = "/presenters-info")
	public String presentersInfo(@RequestParam(name = "presenterId") Integer presenterId)
			throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(eventRepository.findAll());
	}

	@GetMapping(path = "/restaurants")
	public String restaurants() throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(eventRepository.findAll());
	}

	@GetMapping(path = "/restaurants-info")
	public String restaurantsInfo(@RequestParam(name = "restaurantId") Integer restaurantId)
			throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(eventRepository.findAll());
	}
}
