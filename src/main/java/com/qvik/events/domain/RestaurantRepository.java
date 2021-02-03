package com.qvik.events.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/** Repository for entity Restaurant */
@RepositoryRestResource
public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {
}
