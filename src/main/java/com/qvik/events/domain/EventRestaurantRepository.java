package com.qvik.events.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

/** Repository for entity Event_Restaurant */
@RepositoryRestResource
public interface EventRestaurantRepository extends JpaRepository<Event_Restaurant, Long> {

    /** Custom methods to retrieve a list of Restaurant with parameters */
    List<Event_Restaurant> findByEventEquals (Event event);
    List<Event_Restaurant> findByRestaurantEquals (Restaurant restaurant);
}
