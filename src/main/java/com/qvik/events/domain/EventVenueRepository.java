package com.qvik.events.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/** Repository for entity account */
@RepositoryRestResource
public interface EventVenueRepository extends CrudRepository<Event_Venue, Long> {
}
