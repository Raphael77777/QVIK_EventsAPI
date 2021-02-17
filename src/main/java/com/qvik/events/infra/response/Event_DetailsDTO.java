package com.qvik.events.infra.response;

import java.util.List;

import lombok.Data;

@Data
public class Event_DetailsDTO extends Event_BaseDTO {
	
	private String image;
	
	private String fullDescription;
	
	private List<Event_VenueDTO> event_venues;

	private List<Event_StageDTO> event_stages;

	private List<Event_PresenterDTO> event_presenters;
}
