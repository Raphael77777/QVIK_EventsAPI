package com.qvik.events.web;

import com.qvik.events.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/** Controller used for user requests */
@Controller
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
}
