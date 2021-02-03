package com.qvik.events.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/** Repository for entity Presenter */
@RepositoryRestResource
public interface PresenterRepository extends CrudRepository<Presenter, Long> {
}
