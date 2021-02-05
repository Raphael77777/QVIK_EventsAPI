package com.qvik.events.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/** Repository for entity Event_Exhibitor */
@RepositoryRestResource
public interface EventExhibitorRepository extends CrudRepository<Event_Exhibitor, Long> {

    /** Custom methods to retrieve a list of Exhibitor with parameters */
    List<Event_Exhibitor> findByEventEquals (Event event);
    List<Event_Exhibitor> findByExhibitorEquals (Exhibitor exhibitor);
}
