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
import com.qvik.events.modules.stage.Stage;
import com.qvik.events.modules.stage.StageRepository;
import com.qvik.events.modules.tag.EventTagRepository;
import com.qvik.events.modules.tag.Event_Tag;
import com.qvik.events.modules.tag.RestaurantTagRepository;
import com.qvik.events.modules.tag.Restaurant_Tag;
import com.qvik.events.modules.tag.Tag;
import com.qvik.events.modules.tag.TagRepository;
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
			EventPresenterRepository eventPresenterRepository, EventRepository eventRepository,
			EventRestaurantRepository eventRestaurantRepository, EventTagRepository eventTagRepository,
			ExhibitorRepository exhibitorRepository, PresenterRepository presenterRepository,
			RestaurantRepository restaurantRepository, RestaurantTagRepository restaurantTagRepository,
			StageRepository stageRepository, VenueRepository venueRepository, TagRepository tagRepository) {
		return (args) -> {
			if (true) {
				/** Cleaning DB before insertion of demo data */
				eventExhibitorRepository.deleteAll();
				eventPresenterRepository.deleteAll();
				eventRepository.deleteAll();
				eventRestaurantRepository.deleteAll();
				eventTagRepository.deleteAll();
				exhibitorRepository.deleteAll();
				presenterRepository.deleteAll();
				restaurantRepository.deleteAll();
				restaurantTagRepository.deleteAll();
				stageRepository.deleteAll();
				venueRepository.deleteAll();
				tagRepository.deleteAll();
				log.info("PID 9-740 : DATABASE CLEANED !");
			}

			/** Switch to statement with FALSE to disable the INSERTION of demo data ! */
			if (true) {

				String fullDescription = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus.";

				List<Tag> tags = new ArrayList<>();
				tags.add(new Tag("No Smoking"));
				tags.add(new Tag("Child-friendly"));
				tags.add(new Tag("English speaker"));
				tags.add(new Tag("Online event"));
				tags.add(new Tag("Webinar"));
				tags.add(new Tag("Startup"));
				tags.add(new Tag("Culture"));
				tags.add(new Tag("Finnish Traditional"));
				tags.add(new Tag("Fine-Dine"));

				tagRepository.saveAll(tags);

				log.info("PID 9-740 : TAG SAVED !");

				/************************************************************************************************************/

				List<Venue> venues = new ArrayList<>();
				venues.add(new Venue("Suvilahti", "Helsinki", "Kaasutehtaankatu 1", "+358 52 45 63 96"));
				venues.add(new Venue("The Senate Square", "Helsinki", "00170 Helsinki", "+358 52 45 78 96"));
				venues.add(new Venue("Messukeskuksen ", "Helsinki", "Messuaukio 1", "+358 52 45 78 96"));
				venues.add(new Venue("Finlandia-talo", "Helsinki", "Mannerheimintie 13", "+358 52 45 78 96"));
				venues.add(new Venue("Temppeliaukio Church", "Helsinki", "Temppeliaukio  13", "+358 52 45 78 96"));
				venueRepository.saveAll(venues);

				for (Venue v : venueRepository.findAll()) {
					log.info(v.toString());
				}

				log.info("PID 9-741 : VENUE SAVED !");

				/************************************************************************************************************/
				List<Stage> stages = new ArrayList<>();
				stages.add(new Stage("Spray Paint Wall", "Helsinki", 350, "Big stage"));
				stages.get(stages.size() - 1).setVenue(venueRepository.findByName("Suvilahti"));
				stages.add(new Stage("Instrument Room (building 8)", "Helsinki", 150, "Small stage"));
				stages.get(stages.size() - 1).setVenue(venueRepository.findByName("Suvilahti"));
				stages.add(new Stage("Purification plant (building 6)", "Helsinki", 150, "Small stage"));
				stages.get(stages.size() - 1).setVenue(venueRepository.findByName("Suvilahti"));
				stages.add(new Stage("Hall 1", "Helsinki", 150, "Small hall"));
				stages.get(stages.size() - 1).setVenue(venueRepository.findByName("Suvilahti"));
				stages.add(new Stage("Glass Gallary", "Helsinki", 500, "Big Gallary"));
				stages.get(stages.size() - 1).setVenue(venueRepository.findByName("Suvilahti"));
				stageRepository.saveAll(stages);

				for (Stage s : stageRepository.findAll()) {
					log.info(s.toString());
				}

				log.info("PID 9-742 : STAGES SAVED !");

				/************************************************************************************************************/
				List<Presenter> presenters = new ArrayList<>();
				presenters.add(new Presenter("Jan Pellervo Vapaavuori", "jan@email.com", "I am Helsinki Mayor",
						"Full description"));
				presenters.add(new Presenter("Li Andersson ", "li@email.com", "I am a minister", fullDescription));
				presenters.add(
						new Presenter("Pekka Lundmark", "Pekka@email.com", "I am an entrepreneur", fullDescription));
				presenters.add(
						new Presenter("Mark Zuckerberg", "Mark@email.com", "I am an entrepreneur", fullDescription));
				presenters
						.add(new Presenter("Elon Musk", "elon@email.com", "I am an entrepreneur", fullDescription));

				presenterRepository.saveAll(presenters);

				for (Presenter p : presenterRepository.findAll()) {
					log.info(p.toString());
				}

				log.info("PID 9-743 : PRESENTERS SAVED !");

				/************************************************************************************************************/
				List<Restaurant> restaurants = new ArrayList<>();
				LocalTime open = LocalTime.parse("10:00");

				restaurants.add(new Restaurant("Finnjävel", "Ainonkatu 3, 00100 Helsinki", open, open.plusHours(7),
						"Short Description", fullDescription));
				restaurants.get(restaurants.size() - 1).setVenue(venues.get(0));
				restaurants.add(new Restaurant("Taquería Lopez y Lopez", "Espoo", open, open.plusHours(7),
						"Short Description", fullDescription));
				restaurants.get(restaurants.size() - 1).setVenue(venues.get(0));
				restaurants.add(new Restaurant("Good Pizza", "Helsinki", open, open.plusHours(7), "Short Description",
						fullDescription));
				restaurants.get(restaurants.size() - 1).setVenue(venues.get(0));
				restaurantRepository.saveAll(restaurants);

				for (Restaurant r : restaurantRepository.findAll()) {
					log.info(r.toString());
				}

				log.info("PID 9-744 : RESTAURANTS SAVED !");

				/************************************************************************************************************/
				List<Exhibitor> exhibitors = new ArrayList<>();
				exhibitors.add(new Exhibitor("Qvik", "Helsinki", "#qvik.com", "Short", fullDescription));
				exhibitors.add(new Exhibitor("Google", "California", "#google.com", "Short", fullDescription));
				exhibitors.add(new Exhibitor("Tesla", "tesla ", "#elon.com", "Short", fullDescription));
				exhibitorRepository.saveAll(exhibitors);

				for (Exhibitor e : exhibitorRepository.findAll()) {
					log.info(e.toString());
				}

				log.info("PID 9-745 : EXHIBITORS SAVED !");

				/************************************************************************************************************/
				List<Event> events = new ArrayList<>();
				LocalDate testDate = LocalDate.parse("2021-05-10");
				LocalTime testTime = LocalTime.parse("10:00:00");

				events.add(new Event(testDate.minusDays(1), testTime, testDate.plusDays(1), testTime.plusHours(7),
						"Night of Arts", "This is short Description", "#url", LocalDateTime.now(), true, true, true));
				events.add(new Event(testDate.minusDays(1), testTime.plusHours(1), testDate.minusDays(1),
						testTime.plusHours(2), "Wall of Paint", "This is short Description", "#url",
						LocalDateTime.now(), true, true, true));
				events.add(new Event(testDate.minusDays(1), testTime.plusHours(2), testDate.minusDays(1),
						testTime.plusHours(3), "Art Business", "This is short Description", "#url", LocalDateTime.now(),
						true, true, true));
				events.add(new Event(testDate, testTime, testDate, testTime.plusHours(2), "Paint the streets",
						"This is short Description", "#url", LocalDateTime.now(), true, true, true));
				events.add(new Event(testDate.plusDays(1), testTime, testDate.plusDays(1), testTime.plusHours(1),
						"Design in IT industry", "This is short Description", "#url", LocalDateTime.now(), true, true,
						true));

				events.get(0).setFullDescription(fullDescription);
				events.get(0).setVenue(venues.get(0));

				events.get(1).setParentEvent(events.get(0));
				events.get(1).setFullDescription(fullDescription);
				events.get(1).setStage(stages.get(1));

				events.get(2).setParentEvent(events.get(0));
				events.get(2).setFullDescription(fullDescription);
				events.get(2).setStage(stages.get(2));

				events.get(3).setParentEvent(events.get(0));
				events.get(3).setFullDescription(fullDescription);
				events.get(3).setStage(stages.get(3));

				events.get(4).setParentEvent(events.get(0));
				events.get(4).setFullDescription(fullDescription);
				events.get(4).setStage(stages.get(4));

				eventRepository.saveAll(events);

				for (Event e : eventRepository.findAll()) {
					log.info(e.toString());
				}

				log.info("PID 9-746 : EVENTS SAVED !");

				/************************************************************************************************************/
				List<Event_Tag> event_tags = new ArrayList<>();
				for (int i = 0; i < events.size(); i++) {
					Event_Tag event_tag = new Event_Tag();
					event_tag.setEvent(events.get(i));
					event_tag.setTag(tags.get(i));
					event_tags.add(event_tag);
				}

				// adding more sample tag to parent event
				Event_Tag parent2 = new Event_Tag();
				parent2.setEvent(events.get(0));
				parent2.setTag(tagRepository.findByName("Culture"));
				event_tags.add(parent2);

				// adding more sample tag to sub event
				Event_Tag sub2 = new Event_Tag();
				sub2.setEvent(events.get(3));
				sub2.setTag(tagRepository.findByName("Webinar"));
				event_tags.add(sub2);

				eventTagRepository.saveAll(event_tags);

				log.info("PID 9-747 : EVENT-TAG SAVED !");

				/************************************************************************************************************/
				List<Event_Presenter> event_presenters = new ArrayList<>();
				for (int i = 0; i < events.size(); i++) {
					Event_Presenter event_presenter = new Event_Presenter("Description");
					event_presenter.setEvent(events.get(i));
					event_presenter.setPresenter(presenters.get(i));
					event_presenters.add(event_presenter);
				}
				
				Event_Presenter event_presenter = new Event_Presenter("Description");
				event_presenter.setEvent(events.get(2));
				event_presenter.setPresenter(presenters.get(0));
				event_presenters.add(event_presenter);
				eventPresenterRepository.saveAll(event_presenters);

				/*
				 * for (Event_Presenter e : eventPresenterRepository.findAll()){
				 * log.info(e.toString()); }
				 */

				log.info("PID 9-747 : EVENT-PRESENTER SAVED !");

				/************************************************************************************************************/
				List<Event_Exhibitor> event_exhibitors = new ArrayList<>();
				for (int i = 0; i < exhibitors.size(); i++) {
					Event_Exhibitor event_exhibitor = new Event_Exhibitor("Description");
					event_exhibitor.setEvent(events.get(0));
					event_exhibitor.setExhibitor(exhibitors.get(i));
					event_exhibitors.add(event_exhibitor);
				}
				eventExhibitorRepository.saveAll(event_exhibitors);

				for (Event_Exhibitor e : eventExhibitorRepository.findAll()) {
					log.info(e.toString());
				}

				log.info("PID 9-748 : EVENT-EXHIBITOR SAVED !");

				/************************************************************************************************************/
				List<Event_Restaurant> event_restaurants = new ArrayList<>();
				for (int i = 0; i < restaurants.size(); i++) {
					Event_Restaurant event_restaurant = new Event_Restaurant("Description");
					event_restaurant.setEvent(events.get(i+1));
					event_restaurant.setRestaurant(restaurants.get(i));
					event_restaurants.add(event_restaurant);
				}
				eventRestaurantRepository.saveAll(event_restaurants);

				for (Event_Restaurant e : eventRestaurantRepository.findAll()) {
					log.info(e.toString());
				}

				log.info("PID 9-749 : EVENT-RESTAURANT SAVED !");

				/************************************************************************************************************/
				List<Restaurant_Tag> restaurant_tags = new ArrayList<>();

				Restaurant_Tag res_tag1 = new Restaurant_Tag();
				res_tag1.setRestaurant(restaurants.get(0));
				res_tag1.setTag(tagRepository.findByName("Finnish Traditional"));
				restaurant_tags.add(res_tag1);

				Restaurant_Tag res_tag2 = new Restaurant_Tag();
				res_tag2.setRestaurant(restaurants.get(1));
				res_tag2.setTag(tagRepository.findByName("Finnish Traditional"));
				restaurant_tags.add(res_tag2);

				Restaurant_Tag res_tag4 = new Restaurant_Tag();
				res_tag4.setRestaurant(restaurants.get(1));
				res_tag4.setTag(tagRepository.findByName("Fine-Dine"));
				restaurant_tags.add(res_tag4);

				Restaurant_Tag res_tag3 = new Restaurant_Tag();
				res_tag3.setRestaurant(restaurants.get(2));
				res_tag3.setTag(tagRepository.findByName("Fine-Dine"));
				restaurant_tags.add(res_tag3);

				restaurantTagRepository.saveAll(restaurant_tags);

				log.info("PID 9-747 : REESTAURANT-TAG SAVED !");

				/***********************************************************************************************************
				 * List<Event_Venue> event_venues = new ArrayList<>(); for (int i = 0; i <
				 * events.size(); i++){ Event_Venue event_venue = new
				 * Event_Venue("Description"); event_venue.setEvent(events.get(i));
				 * event_venue.setVenue(venueRepository.findByName("Suvilahti"));
				 * event_venues.add(event_venue); } eventVenueRepository.saveAll(event_venues);
				 */

				/*
				 * for (Event_Venue e : eventVenueRepository.findAll()){ log.info(e.toString());
				 * }
				 */

				/** log.info("PID 9-750 : EVENT-VENUE SAVED !"); */

				/***********************************************************************************************************
				 * List<Event_Stage> event_stages = new ArrayList<>(); for (int i = 0; i <
				 * events.size(); i++){ Event_Stage event_stage = new
				 * Event_Stage("Description"); event_stage.setEvent(events.get(i));
				 * event_stage.setStage(stages.get(i)); event_stages.add(event_stage); }
				 * eventStageRepository.saveAll(event_stages);
				 */

				/*
				 * for (Event_Stage e : eventStageRepository.findAll()){ log.info(e.toString());
				 * }
				 */

				/** log.info("PID 9-751 : EVENT-STAGE SAVED !"); */
			}
		};
	}
}
