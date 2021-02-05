package com.qvik.events.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/** Repository for entity Stage */
@RepositoryRestResource
public interface StageRepository extends CrudRepository<Stage, Long> {

    /** Custom methods to retrieve a list of Stage with parameters */
    List<Stage> findByVenueEquals (Venue venue);
}
