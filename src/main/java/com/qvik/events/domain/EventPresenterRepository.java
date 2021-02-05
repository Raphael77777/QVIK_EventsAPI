package com.qvik.events.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

/** Repository for entity Event_Presenter */
@RepositoryRestResource
public interface EventPresenterRepository extends JpaRepository<Event_Presenter, Long> {

    /** Custom methods to retrieve a list of Presenter with parameters */
    List<Event_Presenter> findByEventEquals (Event event);
    List<Event_Presenter> findByPresenterEquals (Presenter presenter);
}
