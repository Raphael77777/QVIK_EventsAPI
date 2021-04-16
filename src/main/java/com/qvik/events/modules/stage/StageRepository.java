package com.qvik.events.modules.stage;
import java.util.List;

import com.qvik.events.modules.cuisine.Cuisine;
import com.qvik.events.modules.restaurant.Restaurant;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.qvik.events.modules.venue.Venue;

/** Repository for entity Stage */
@RepositoryRestResource
public interface StageRepository extends JpaRepository<Stage, Long> {

    /** Custom methods to retrieve a list of Stage with parameters */
    List<Stage> findByVenueEquals (Venue venue);

    @EntityGraph(attributePaths = {"venue"})
    List<Stage> findStagesWithVenueByVenue(Venue venue);

    Stage findByName(String name);
}
