package com.qvik.events.infra.response;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class RestaurantsDTO {

	private Set<Event_RestaurantDetailsDTO> eventRestaurants = new HashSet<>();
}
