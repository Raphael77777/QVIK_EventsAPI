package com.qvik.events.infra.response.dto;

import java.util.ArrayList;
import java.util.List;

import com.qvik.events.modules.image.Image;
import lombok.Data;

@Data
public class Init_SettingDTO extends Event_BaseDTO {
	private List<String> eventTags = new ArrayList<>();
	private List<String> allEventTags = new ArrayList<>();
	private String venue;
	private ImageDTO image;

	private List<String> allRestaurantTags = new ArrayList<>();
	private String restaurantImage;
}
