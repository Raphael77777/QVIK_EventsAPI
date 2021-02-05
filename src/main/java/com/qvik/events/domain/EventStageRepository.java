package com.qvik.events.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/** Repository for entity Event_Stage */
@RepositoryRestResource
public interface EventStageRepository extends CrudRepository<Event_Stage, Long> {

    /** Custom methods to retrieve a list of Stage with parameters */
    List<Event_Stage> findByEventEquals (Event event);
    List<Event_Stage> findByStageEquals (Stage stage);
}
