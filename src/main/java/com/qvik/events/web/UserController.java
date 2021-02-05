package com.qvik.events.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qvik.events.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/** Controller used for user requests */
@RestController
public class UserController {

    @Autowired
    private EventExhibitorRepository eventExhibitorRepository;
    @Autowired
    private EventPresenterRepository eventPresenterRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private EventRestaurantRepository eventRestaurantRepository;
    @Autowired
    private EventStageRepository eventStageRepository;
    @Autowired
    private EventVenueRepository eventVenueRepository;
    @Autowired
    private ExhibitorRepository exhibitorRepository;
    @Autowired
    private PresenterRepository presenterRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private StageRepository stageRepository;
    @Autowired
    private VenueRepository venueRepository;

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
    public String exhibitorsInfo(@RequestParam(name = "exhibitorId") Integer exhibitorId) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(eventRepository.findAll());
    }

    @GetMapping(path = "/presenters-info")
    public String presentersInfo(@RequestParam(name = "presenterId") Integer presenterId) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(eventRepository.findAll());
    }

    @GetMapping(path = "/restaurants")
    public String restaurants() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(eventRepository.findAll());
    }

    @GetMapping(path = "/restaurants-info")
    public String restaurantsInfo(@RequestParam(name = "restaurantId") Integer restaurantId) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(eventRepository.findAll());
    }
}
