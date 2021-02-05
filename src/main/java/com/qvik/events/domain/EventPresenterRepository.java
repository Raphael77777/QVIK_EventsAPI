package com.qvik.events.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/** Repository for entity Event_Presenter */
@RepositoryRestResource
public interface EventPresenterRepository extends CrudRepository<Event_Presenter, Long> {

    /** Custom methods to retrieve a list of Presenter with parameters */
    List<Event_Presenter> findByEventEquals (Event event);
    List<Event_Presenter> findByPresenterEquals (Presenter presenter);
}
