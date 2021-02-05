package com.qvik.events.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/** Repository for entity Event_Restaurant */
@RepositoryRestResource
public interface EventRestaurantRepository extends CrudRepository<Event_Restaurant, Long> {

    /** Custom methods to retrieve a list of Restaurant with parameters */
    List<Event_Restaurant> findByEventEquals (Event event);
    List<Event_Restaurant> findByRestaurantEquals (Restaurant restaurant);
}
