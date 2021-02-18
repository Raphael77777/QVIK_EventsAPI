package com.qvik.events.infra.response;

import java.util.List;

import lombok.Data;

@Data
public class Event_DetailsDTO extends Event_BaseDTO {
	
	private String image;
	
	private String fullDescription;
	
	private List<Event_VenueDTO> eventVenues;

	private List<Event_StageDTO> eventStages;

	private List<Event_PresenterDTO> eventPresenters;
}
