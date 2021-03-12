package com.qvik.events.modules.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

/** Repository for entity account */
@RepositoryRestResource
@Transactional(readOnly = true)
public interface EventTagRepository extends JpaRepository<Event_Tag, Long> {

}
