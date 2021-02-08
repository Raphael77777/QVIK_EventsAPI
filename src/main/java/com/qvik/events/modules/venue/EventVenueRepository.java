package com.qvik.events.modules.venue;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.qvik.events.modules.event.Event;

/** Repository for entity account */
@RepositoryRestResource
public interface EventVenueRepository extends JpaRepository<Event_Venue, Long> {

    /** Custom methods to retrieve a list of Venue with parameters */
    List<Event_Venue> findByEventEquals (Event event);
    List<Event_Venue> findByVenueEquals (Venue venue);
}
