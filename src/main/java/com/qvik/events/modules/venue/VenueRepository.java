package com.qvik.events.modules.venue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

/** Repository for entity Venue */
@RepositoryRestResource
public interface VenueRepository extends JpaRepository<Venue, Long> {

	@Transactional
	Venue findByName(String name);
}
