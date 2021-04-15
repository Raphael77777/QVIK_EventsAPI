package com.qvik.events.infra.response.dto;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;

@Data
public class RestaurantCuisinesDTO {
	private Set<Restaurant_CuisineDTO> restaurantCuisines = new HashSet<>();
}
