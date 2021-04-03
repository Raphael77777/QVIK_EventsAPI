package com.qvik.events.modules.presenter;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.qvik.events.modules.event.Event;

/** Repository for entity Event_Presenter */
@RepositoryRestResource
public interface EventPresenterRepository extends JpaRepository<Event_Presenter, Long> {

    /** Custom methods to retrieve a list of Presenter with parameters */
    List<Event_Presenter> findByEventEquals (Event event);
    List<Event_Presenter> findByPresenterEquals (Presenter presenter);

    List<Event_Presenter> findByEventEqualsAndPresenterEquals (Event event, Presenter presenter);
}
