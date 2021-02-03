package com.qvik.events.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/** Repository for entity Event */
@RepositoryRestResource
public interface EventRepository extends CrudRepository<Event, Long> {
}
