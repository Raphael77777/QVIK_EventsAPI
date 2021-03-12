package com.qvik.events.infra.response;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;

@Data
public class RestaurantTagsDTO {

	private Set <Restaurant_TagDTO> restaurantTags = new HashSet<>();
}
