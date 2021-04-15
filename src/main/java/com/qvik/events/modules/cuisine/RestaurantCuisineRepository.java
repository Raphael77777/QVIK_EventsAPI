package com.qvik.events.modules.cuisine;

import com.qvik.events.modules.restaurant.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/** Repository for entity account */
@RepositoryRestResource
@Transactional(readOnly = true)
public interface RestaurantCuisineRepository extends JpaRepository <Restaurant_Cuisine, Long>{

    List<Restaurant_Cuisine> findByRestaurantEqualsAndCuisineEquals (Restaurant restaurant, Cuisine cuisine);
}
