package com.qvik.events.infra.response.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Init_SettingDTO extends Event_BaseDTO {
	private List<String> eventTags = new ArrayList<>();
	private List<String> allEventTags = new ArrayList<>();
	private String eventImage;
	private String venue;
	
	private List<String> allRestaurantTags = new ArrayList<>();
	private String restaurantImage;
}
