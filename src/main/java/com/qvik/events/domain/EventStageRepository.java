package com.qvik.events.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/** Repository for entity Event_Stage */
@RepositoryRestResource
public interface EventStageRepository extends CrudRepository<Event_Stage, Long> {
}
