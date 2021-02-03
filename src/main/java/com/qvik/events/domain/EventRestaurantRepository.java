package com.qvik.events.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/** Repository for entity Event_Restaurant */
@RepositoryRestResource
public interface EventRestaurantRepository extends CrudRepository<Event_Restaurant, Long> {
}
