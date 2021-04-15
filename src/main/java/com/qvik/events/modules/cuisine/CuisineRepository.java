package com.qvik.events.modules.cuisine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

/** Repository for entity Tag */
@Transactional(readOnly = true)
@RepositoryRestResource
public interface CuisineRepository extends JpaRepository <Cuisine, Long> {
	Cuisine findByName(String name);
}
