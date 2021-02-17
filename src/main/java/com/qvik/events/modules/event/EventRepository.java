package com.qvik.events.modules.event;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/** Repository for entity Event */
@RepositoryRestResource
public interface EventRepository extends JpaRepository<Event, Long> {
	
	List<Event> findByStartDateOrderByStartDateDesc(LocalDate startDate);

	List<Event> findByStartDateAndEndDateOrderByStartDateDesc(LocalDate startDate, LocalDate endDate);
}
