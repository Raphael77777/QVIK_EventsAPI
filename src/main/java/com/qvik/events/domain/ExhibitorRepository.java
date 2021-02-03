package com.qvik.events.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/** Repository for entity Exhibitor */
@RepositoryRestResource
public interface ExhibitorRepository extends CrudRepository<Exhibitor, Long> {
}
