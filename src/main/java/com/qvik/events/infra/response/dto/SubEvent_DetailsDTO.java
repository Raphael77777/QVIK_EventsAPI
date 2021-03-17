package com.qvik.events.infra.response.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SubEvent_DetailsDTO extends Event_DetailsDTO {

	private List<String> inheritedTags = new ArrayList<>();
	private List<RestaurantDTO> restaurants = new ArrayList<>();
}
