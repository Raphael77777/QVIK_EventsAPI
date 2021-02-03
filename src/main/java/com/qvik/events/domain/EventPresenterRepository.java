package com.qvik.events.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/** Repository for entity Event_Presenter */
@RepositoryRestResource
public interface EventPresenterRepository extends CrudRepository<Event_Presenter, Long> {
}
