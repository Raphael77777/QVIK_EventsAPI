package com.qvik.events.infra.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Init_SettingDTO extends Parent_EventDTO {

	private String eventImage;
	private List<String> allRestaurantTags = new ArrayList<>();
	private String restaurantImage;
}
