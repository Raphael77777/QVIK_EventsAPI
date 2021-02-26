package com.qvik.events.infra.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Event_DetailsDTO extends Event_BaseDTO {
	
	private String image;
	private String shortDescription;
	private String fullDescription;	

	//private Set<Event_PresenterDTO> eventPresenters = new HashSet<>();	
	private List<String> presenters = new ArrayList<>();
}
