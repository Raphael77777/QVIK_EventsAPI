package com.qvik.events.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

/** Repository for entity Event_Exhibitor */
@RepositoryRestResource
public interface EventExhibitorRepository extends JpaRepository <Event_Exhibitor, Long> {

    /** Custom methods to retrieve a list of Exhibitor with parameters */
    List<Event_Exhibitor> findByEventEquals (Event event);
    List<Event_Exhibitor> findByExhibitorEquals (Exhibitor exhibitor);
}
