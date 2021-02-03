package com.qvik.events.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/** Repository for entity Venue */
@RepositoryRestResource
public interface VenueRepository extends CrudRepository<Venue, Long> {
}
