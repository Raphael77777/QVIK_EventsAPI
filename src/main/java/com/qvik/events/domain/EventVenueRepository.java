package com.qvik.events.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/** Repository for entity account */
@RepositoryRestResource
public interface EventVenueRepository extends CrudRepository<Event_Venue, Long> {

    /** Custom methods to retrieve a list of Venue with parameters */
    List<Event_Venue> findByEventEquals (Event event);
    List<Event_Venue> findByVenueEquals (Venue venue);
}
