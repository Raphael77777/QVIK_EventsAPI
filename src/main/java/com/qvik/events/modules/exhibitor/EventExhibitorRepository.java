package com.qvik.events.modules.exhibitor;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.qvik.events.modules.event.Event;

/** Repository for entity Event_Exhibitor */
@RepositoryRestResource
public interface EventExhibitorRepository extends JpaRepository <Event_Exhibitor, Long> {

    /** Custom methods to retrieve a list of Exhibitor with parameters */
    List<Event_Exhibitor> findByEventEquals (Event event);
    List<Event_Exhibitor> findByExhibitorEquals (Exhibitor exhibitor);
}
