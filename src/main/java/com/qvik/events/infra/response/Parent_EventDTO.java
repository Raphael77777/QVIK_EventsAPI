package com.qvik.events.infra.response;

import java.util.List;

import lombok.Data;

@Data
public class Parent_EventDTO extends Event_BaseDTO {	

	
	private List<Event_VenueDTO> eventVenues;

}
