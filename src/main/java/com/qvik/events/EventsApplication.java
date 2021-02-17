package com.qvik.events;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.qvik.events.modules.event.Event;
import com.qvik.events.modules.event.EventRepository;
import com.qvik.events.modules.exhibitor.EventExhibitorRepository;
import com.qvik.events.modules.exhibitor.Event_Exhibitor;
import com.qvik.events.modules.exhibitor.Exhibitor;
import com.qvik.events.modules.exhibitor.ExhibitorRepository;
import com.qvik.events.modules.presenter.EventPresenterRepository;
import com.qvik.events.modules.presenter.Event_Presenter;
import com.qvik.events.modules.presenter.Presenter;
import com.qvik.events.modules.presenter.PresenterRepository;
import com.qvik.events.modules.restaurant.EventRestaurantRepository;
import com.qvik.events.modules.restaurant.Event_Restaurant;
import com.qvik.events.modules.restaurant.Restaurant;
import com.qvik.events.modules.restaurant.RestaurantRepository;
import com.qvik.events.modules.stage.EventStageRepository;
import com.qvik.events.modules.stage.Event_Stage;
import com.qvik.events.modules.stage.Stage;
import com.qvik.events.modules.stage.StageRepository;
import com.qvik.events.modules.venue.EventVenueRepository;
import com.qvik.events.modules.venue.Event_Venue;
import com.qvik.events.modules.venue.Venue;
import com.qvik.events.modules.venue.VenueRepository;

@SpringBootApplication
@EnableTransactionManagement
public class EventsApplication {

    private static final Logger log = LoggerFactory.getLogger(EventsApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(EventsApplication.class, args);
    }

    /** INSERTION OF DEMO DATA */
    @Bean
    public CommandLineRunner QvikDemoData(EventExhibitorRepository eventExhibitorRepository,
                                          EventPresenterRepository eventPresenterRepository,
                                          EventRepository eventRepository,
                                          EventRestaurantRepository eventRestaurantRepository,
                                          EventStageRepository eventStageRepository,
                                          EventVenueRepository eventVenueRepository,
                                          ExhibitorRepository exhibitorRepository,
                                          PresenterRepository presenterRepository,
                                          RestaurantRepository restaurantRepository,
                                          StageRepository stageRepository,
                                          VenueRepository venueRepository) {
        return (args) -> {
            if(true){
                /** Cleaning DB before insertion of demo data*/
                eventExhibitorRepository.deleteAll();
                eventPresenterRepository.deleteAll();
                eventRepository.deleteAll();
                eventRestaurantRepository.deleteAll();
                eventStageRepository.deleteAll();
                eventVenueRepository.deleteAll();
                exhibitorRepository.deleteAll();
                presenterRepository.deleteAll();
                restaurantRepository.deleteAll();
                stageRepository.deleteAll();
                venueRepository.deleteAll();
                log.info("PID 9-740 : DATABASE CLEANED !");
            }

            /** Switch to statement with FALSE to disable the INSERTION of demo data ! */
            if(true) {
            	
            	String fullDescription = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus. ";

                List<Venue> venues = new ArrayList<>();
                venues.add( new Venue("Suvialahti", "Helsinki", "Street", "+358 52 45 63 96"));
                venues.add( new Venue("The Senate Square", "Helsinki", "Street", "+358 52 45 78 96"));
                venueRepository.saveAll(venues);

                for (Venue v : venueRepository.findAll()) {
                    log.info(v.toString());
                }

                log.info("PID 9-741 : VENUE SAVED !");

                /************************************************************************************************************/
                List<Stage> stages = new ArrayList<>();
                stages.add(new Stage("Spray Paint Wall", "Helsinki", 350, "Big stage"));
                stages.get(stages.size()-1).setVenue(venues.get(stages.size()-1));
                stages.add(new Stage("Stage Name", "Helsinki", 150, "Small stage"));
                stages.get(stages.size()-1).setVenue(venues.get(stages.size()-1));
                stageRepository.saveAll(stages);

                for (Stage s : stageRepository.findAll()){
                    log.info(s.toString());
                }

                log.info("PID 9-742 : STAGES SAVED !");

                /************************************************************************************************************/
                List<Presenter> presenters = new ArrayList<>();
                presenters.add(new Presenter("Pablo Picasso", "pablo@email.com", "I am an artist.", "Full description"));
                presenters.add(new Presenter("Vincent van Gogh", "vincent@email.com", "I am an artist", "Full description"));
                presenterRepository.saveAll(presenters);

                for (Presenter p : presenterRepository.findAll()){
                    log.info(p.toString());
                }

                log.info("PID 9-743 : PRESENTERS SAVED !");

                /************************************************************************************************************/
                List<Restaurant> restaurants = new ArrayList<>();
                LocalTime open = LocalTime.parse("10:00");

                restaurants.add(new Restaurant("name", "San Fransisco", open, open.plusHours(7), "Short", "Full description"));
                restaurants.get(restaurants.size()-1).setVenue(venues.get(restaurants.size()-1));
                restaurants.add(new Restaurant("name", "Chicago", open, open.plusHours(7), "Short", "Full description"));
                restaurants.get(restaurants.size()-1).setVenue(venues.get(restaurants.size()-1));
                restaurantRepository.saveAll(restaurants);

                for (Restaurant r : restaurantRepository.findAll()){
                    log.info(r.toString());
                }

                log.info("PID 9-744 : RESTAURANTS SAVED !");

                /************************************************************************************************************/
                List<Exhibitor> exhibitors = new ArrayList<>();
                exhibitors.add(new Exhibitor("name", "San Fransisco", "#google.com", "Short", "Full description"));
                exhibitors.add(new Exhibitor("name", "Chicago", "#elon.com", "Short", "Full description"));
                exhibitorRepository.saveAll(exhibitors);

                for (Exhibitor e : exhibitorRepository.findAll()){
                    log.info(e.toString());
                }

                log.info("PID 9-745 : EXHIBITORS SAVED !");

                /************************************************************************************************************/
                List<Event> events = new ArrayList<>();
                LocalDate today = LocalDate.now();
                LocalTime start = LocalTime.parse("10:00:00");
                
                events.add(new Event(today.minusDays(1), start, today.plusDays(1), start.plusHours(7), "Night of Arts", "music", "#url", "OPEN", LocalDateTime.now(), true, true, true));
                events.add(new Event(today.minusDays(1), start.plusHours(1), today.minusDays(1), start.plusHours(2), "Wall of Paint", "music", "#url", "OPEN", LocalDateTime.now(), true, true, true));
                events.get(0).setFullDescription(fullDescription);
                events.get(1).setParentEvent(events.get(0));
                events.get(1).setFullDescription(fullDescription);
                eventRepository.saveAll(events);

                for (Event e : eventRepository.findAll()){
                    log.info(e.toString());
                }

                log.info("PID 9-746 : EVENTS SAVED !");

                /************************************************************************************************************/
                List<Event_Presenter> event_presenters = new ArrayList<>();
                for (int i = 0; i < events.size(); i++){
                    Event_Presenter event_presenter = new Event_Presenter("Description");
                    event_presenter.setEvent(events.get(i));
                    event_presenter.setPresenter(presenters.get(i));
                    event_presenters.add(event_presenter);
                }
                eventPresenterRepository.saveAll(event_presenters);

                for (Event_Presenter e : eventPresenterRepository.findAll()){
                    log.info(e.toString());
                }

                log.info("PID 9-747 : EVENT-PRESENTER SAVED !");

                /************************************************************************************************************/
                List<Event_Exhibitor> event_exhibitors = new ArrayList<>();
                for (int i = 0; i < events.size(); i++){
                    Event_Exhibitor event_exhibitor = new Event_Exhibitor("Description");
                    event_exhibitor.setEvent(events.get(i));
                    event_exhibitor.setExhibitor(exhibitors.get(i));
                    event_exhibitors.add(event_exhibitor);
                }
                eventExhibitorRepository.saveAll(event_exhibitors);

                for (Event_Exhibitor e : eventExhibitorRepository.findAll()){
                    log.info(e.toString());
                }

                log.info("PID 9-748 : EVENT-EXHIBITOR SAVED !");

                /************************************************************************************************************/
                List<Event_Restaurant> event_restaurants = new ArrayList<>();
                for (int i = 0; i < events.size(); i++){
                    Event_Restaurant event_restaurant = new Event_Restaurant("Description");
                    event_restaurant.setEvent(events.get(i));
                    event_restaurant.setRestaurant(restaurants.get(i));
                    event_restaurants.add(event_restaurant);
                }
                eventRestaurantRepository.saveAll(event_restaurants);

                for (Event_Restaurant e : eventRestaurantRepository.findAll()){
                    log.info(e.toString());
                }

                log.info("PID 9-749 : EVENT-RESTAURANT SAVED !");

                /************************************************************************************************************/
                List<Event_Venue> event_venues = new ArrayList<>();
                for (int i = 0; i < events.size(); i++){
                    Event_Venue event_venue = new Event_Venue("Description");
                    event_venue.setEvent(events.get(i));
                    event_venue.setVenue(venues.get(i));
                    event_venues.add(event_venue);
                }
                eventVenueRepository.saveAll(event_venues);

                for (Event_Venue e : eventVenueRepository.findAll()){
                    log.info(e.toString());
                }

                log.info("PID 9-750 : EVENT-VENUE SAVED !");

                /************************************************************************************************************/
                List<Event_Stage> event_stages = new ArrayList<>();
                for (int i = 0; i < events.size(); i++){
                    Event_Stage event_stage = new Event_Stage("Description");
                    event_stage.setEvent(events.get(i));
                    event_stage.setStage(stages.get(i));
                    event_stages.add(event_stage);
                }
                eventStageRepository.saveAll(event_stages);

                for (Event_Stage e : eventStageRepository.findAll()){
                    log.info(e.toString());
                }

                log.info("PID 9-751 : EVENT-SATGE SAVED !");
            }
        };
    }

}
