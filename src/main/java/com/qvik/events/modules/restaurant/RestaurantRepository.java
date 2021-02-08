package com.qvik.events.modules.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

/** Repository for entity Restaurant */
@RepositoryRestResource
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
