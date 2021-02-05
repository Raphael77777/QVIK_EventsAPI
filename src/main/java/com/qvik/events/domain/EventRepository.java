package com.qvik.events.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

/** Repository for entity Event */
@RepositoryRestResource
public interface EventRepository extends JpaRepository<Event, Long> {
}
