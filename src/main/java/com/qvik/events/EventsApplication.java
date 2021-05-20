package com.qvik.events;

import com.qvik.events.modules.cuisine.CuisineRepository;
import com.qvik.events.modules.cuisine.RestaurantCuisineRepository;
import com.qvik.events.modules.event.EventRepository;
import com.qvik.events.modules.exhibitor.EventExhibitorRepository;
import com.qvik.events.modules.exhibitor.ExhibitorRepository;
import com.qvik.events.modules.image.ImageRepository;
import com.qvik.events.modules.presenter.EventPresenterRepository;
import com.qvik.events.modules.presenter.PresenterRepository;
import com.qvik.events.modules.restaurant.EventRestaurantRepository;
import com.qvik.events.modules.restaurant.RestaurantRepository;
import com.qvik.events.modules.stage.StageRepository;
import com.qvik.events.modules.tag.EventTagRepository;
import com.qvik.events.modules.tag.TagRepository;
import com.qvik.events.modules.venue.VenueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class EventsApplication {

	public static final String ADMIN_KEY = "QVIK2021AdminKey";

	public static final Logger log = LoggerFactory.getLogger(EventsApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(EventsApplication.class, args);
	}

	/** INSERTION OF DEMO DATA */
	@Bean
	public CommandLineRunner QvikDemoData(EventExhibitorRepository eventExhibitorRepository,
										  EventPresenterRepository eventPresenterRepository, EventRepository eventRepository,
										  EventRestaurantRepository eventRestaurantRepository, EventTagRepository eventTagRepository,
										  ExhibitorRepository exhibitorRepository, PresenterRepository presenterRepository,
										  RestaurantRepository restaurantRepository, RestaurantCuisineRepository restaurantCuisineRepository,
										  StageRepository stageRepository, VenueRepository venueRepository, TagRepository tagRepository,
										  ImageRepository imageRepository, CuisineRepository cuisineRepository) {
		return (args) -> {

			/** Switch to statement with FALSE to disable the CLEANING of all data ! */
			if (true) {
				DemoData.clean(log, eventExhibitorRepository,
						eventPresenterRepository, eventRepository,
						eventRestaurantRepository, eventTagRepository,
						exhibitorRepository, presenterRepository,
						restaurantRepository, restaurantCuisineRepository,
						stageRepository, venueRepository, tagRepository,
						imageRepository, cuisineRepository);
			}

			/** Switch to statement with FALSE to disable the INSERTION of demo data ! */
			if (true) {
				DemoData.populate(log, eventExhibitorRepository,
						eventPresenterRepository, eventRepository,
						eventRestaurantRepository, eventTagRepository,
						exhibitorRepository, presenterRepository,
						restaurantRepository, restaurantCuisineRepository,
						stageRepository, venueRepository, tagRepository,
						imageRepository, cuisineRepository);
			}
		};
	}
}
