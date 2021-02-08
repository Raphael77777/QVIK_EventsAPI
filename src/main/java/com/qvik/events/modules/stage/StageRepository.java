package com.qvik.events.modules.stage;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.qvik.events.modules.venue.Venue;

/** Repository for entity Stage */
@RepositoryRestResource
public interface StageRepository extends JpaRepository<Stage, Long> {

    /** Custom methods to retrieve a list of Stage with parameters */
    List<Stage> findByVenueEquals (Venue venue);
}
