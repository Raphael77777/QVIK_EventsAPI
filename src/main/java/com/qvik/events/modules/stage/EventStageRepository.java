package com.qvik.events.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

/** Repository for entity Event_Stage */
@RepositoryRestResource
public interface EventStageRepository extends JpaRepository<Event_Stage, Long> {

    /** Custom methods to retrieve a list of Stage with parameters */
    List<Event_Stage> findByEventEquals (Event event);
    List<Event_Stage> findByStageEquals (Stage stage);
}
