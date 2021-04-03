package com.qvik.events.modules.tag;

import com.qvik.events.modules.event.Event;
import com.qvik.events.modules.restaurant.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/** Repository for entity account */
@RepositoryRestResource
@Transactional(readOnly = true)
public interface RestaurantTagRepository extends JpaRepository <Restaurant_Tag, Long>{

    List<Restaurant_Tag> findByRestaurantEqualsAndTagEquals (Restaurant restaurant, Tag tag);
}
