package com.qvik.events.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/** Repository for entity Event_Exhibitor */
@RepositoryRestResource
public interface EventExhibitorRepository extends CrudRepository<Event_Exhibitor, Long> {
}
