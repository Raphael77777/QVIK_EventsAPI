package com.qvik.events.infra.response;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.qvik.events.modules.stage.Stage;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) 
public class Event_DetailsDTO extends Event_BaseDTO {
	
	private String image;
	private String shortDescription;
	private String fullDescription;	

	//private Set<Event_PresenterDTO> eventPresenters = new HashSet<>();
	private VenueDTO venue;
	private StageDTO stage;
	private List<String> presenters = new ArrayList<>();

}
