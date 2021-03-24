package com.qvik.events.infra.response.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) 
public class Event_DetailsDTO extends Event_BaseDTO {
	
	private boolean activeEvent;
	private String shortDescription;
	private String fullDescription;	
	private VenueDTO venue;
	private StageDTO stage;
	private ImageDTO image;
	private List<PresenterDTO> presenters = new ArrayList<>();
	private List<RestaurantDTO> restaurants = new ArrayList<>();
	private List<String> inheritedTags = new ArrayList<>();
	private List<String> eventTags = new ArrayList<>();
	
}
