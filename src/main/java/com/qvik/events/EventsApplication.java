package com.qvik.events;

import com.qvik.events.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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

                List<Venue> venues = new ArrayList<>();
                venues.add( new Venue("name", "San Fransisco", "Street", "+358 52 45 63 96"));
                venues.add( new Venue("name", "Chicago", "Street", "+358 52 45 78 96"));
                venueRepository.saveAll(venues);

                for (Venue v : venueRepository.findAll()) {
                    log.info(v.toString());
                }

                log.info("PID 9-741 : VENUE SAVED !");

                /************************************************************************************************************/
                List<Stage> stages = new ArrayList<>();
                stages.add(new Stage("name", "San Fransisco", 350, "Big stage"));
                stages.get(stages.size()-1).setVenue(venues.get(stages.size()-1));
                stages.add(new Stage("name", "Chicago", 150, "Small stage"));
                stages.get(stages.size()-1).setVenue(venues.get(stages.size()-1));
                stageRepository.saveAll(stages);

                for (Stage s : stageRepository.findAll()){
                    log.info(s.toString());
                }

                log.info("PID 9-742 : STAGES SAVED !");

                /************************************************************************************************************/
                List<Presenter> presenters = new ArrayList<>();
                presenters.add(new Presenter("name", "San Fransisco", "Short", "Full description"));
                presenters.add(new Presenter("name", "Chicago", "Short", "Full description"));
                presenterRepository.saveAll(presenters);

                for (Presenter p : presenterRepository.findAll()){
                    log.info(p.toString());
                }

                log.info("PID 9-743 : PRESENTERS SAVED !");

                /************************************************************************************************************/
                List<Restaurant> restaurants = new ArrayList<>();
                restaurants.add(new Restaurant("name", "San Fransisco", new Time(System.currentTimeMillis()), new Time(System.currentTimeMillis()), "Short", "Full description"));
                restaurants.add(new Restaurant("name", "Chicago", new Time(System.currentTimeMillis()), new Time(System.currentTimeMillis()), "Short", "Full description"));
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
                LocalDate now = LocalDate.now();
                LocalTime time = LocalTime.now();
                events.add(new Event(now.minusDays(3), time, now.plusDays(2), time.plusHours(1), "Short", "Full description", "music", "#url", "OPEN", LocalDateTime.now(), false, false));
                events.add(new Event(now.minusDays(2), time, now.plusDays(1), time.plusHours(2), "Short", "Full description", "music", "#url", "OPEN", LocalDateTime.now(), false, false));
                events.get(1).setParent_event(events.get(0));
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
