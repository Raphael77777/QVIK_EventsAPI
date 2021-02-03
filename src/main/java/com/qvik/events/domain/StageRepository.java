package com.qvik.events.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/** Repository for entity Stage */
@RepositoryRestResource
public interface StageRepository extends CrudRepository<Stage, Long> {
}
