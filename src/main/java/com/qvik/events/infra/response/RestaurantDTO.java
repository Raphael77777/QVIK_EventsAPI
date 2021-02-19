package com.qvik.events.infra.response;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class RestaurantDTO {
	private long restaurant_id;
	private String name;
}
