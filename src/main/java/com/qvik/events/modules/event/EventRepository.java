package com.qvik.events.modules.event;

import java.time.LocalDate;
import java.util.List;

import com.qvik.events.modules.image.Image;
import com.qvik.events.modules.stage.Stage;
import com.qvik.events.modules.venue.Venue;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

/** Repository for entity Event */
@RepositoryRestResource
@Transactional(readOnly = true)
public interface EventRepository extends JpaRepository<Event, Long> {
	
	List<Event> findByStartDateOrderByStartDateDesc(LocalDate startDate);

	List<Event> findByStartDateAndEndDateOrderByStartDateDesc(LocalDate startDate, LocalDate endDate);

	@EntityGraph(attributePaths = {"venue", "stage", "eventPresenters", "eventTags"})
	Event findEventWithVenueAndStageAndEventPresentersAndEventTagsByEventId(Long id);

	@EntityGraph(attributePaths = {"stage"})
	Event findEventWithStageByEventId(Long id);

	@EntityGraph(attributePaths = {"venue"})
	Event findEventWithVenueByEventId(Long id);

	@EntityGraph(attributePaths = {"eventPresenters"})
	Event findEventWithEventPresentersByEventId(Long id);

	@EntityGraph(attributePaths = {"eventExhibitors"})
	Event findEventWithEventExhibitorsByEventId(Long id);

	@EntityGraph(attributePaths = {"eventRestaurants"})
	Event findEventWithEventRestaurantsByEventId(Long id);

	@EntityGraph(attributePaths = {"eventTags"})
	Event findEventWithEventTagsByEventId(Long id);
	
	Event findEventByParentEvent (Event event);

	Event findEventByIsMainEventTrue ();

	@EntityGraph(attributePaths = {"stage"})
	List<Event> findEventsWithStageByStage(Stage stage);

	@EntityGraph(attributePaths = {"venue"})
	List<Event> findEventsWithVenueByVenue(Venue venue);

	@EntityGraph(attributePaths = {"image"})
	List<Event> findEventsWithImageByImage(Image image);
}
