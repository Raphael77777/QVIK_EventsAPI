package com.qvik.events.modules.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/** Repository for entity account */
@RepositoryRestResource
public interface EventTagRepository extends JpaRepository<Event_Tag, Long> {

}
