package com.qvik.events.modules.event;

import java.time.LocalDate;
import java.util.List;

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
	
	@EntityGraph(attributePaths = {"eventVenues", "eventStages", "eventPresenters"})
	Event findEventWithEventVenuesAndEventStagesAndEventPresentersByEventId(Long id);
}
