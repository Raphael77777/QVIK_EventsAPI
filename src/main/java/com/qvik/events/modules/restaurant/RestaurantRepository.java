package com.qvik.events.modules.restaurant;

import com.qvik.events.modules.stage.Stage;
import com.qvik.events.modules.venue.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/** Repository for entity Restaurant */
@RepositoryRestResource
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findByVenueEquals (Venue venue);
}