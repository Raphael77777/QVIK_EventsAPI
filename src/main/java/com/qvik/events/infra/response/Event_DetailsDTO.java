package com.qvik.events.infra.response;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;

@Data
public class Event_DetailsDTO extends Event_BaseDTO {
	
	private String image;
	
	private String fullDescription;
	

	private Set<Event_VenueDTO> eventVenues = new HashSet<>();

	private Set<Event_StageDTO> eventStages = new HashSet<>();

	private Set<Event_PresenterDTO> eventPresenters = new HashSet<>();


}
