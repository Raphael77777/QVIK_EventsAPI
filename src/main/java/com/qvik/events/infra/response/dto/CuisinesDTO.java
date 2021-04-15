package com.qvik.events.infra.response.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class CuisinesDTO {

	private Set<Restaurant_CuisineDTO> restaurantCuisine = new HashSet<>();
}
