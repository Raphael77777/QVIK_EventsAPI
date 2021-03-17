package com.qvik.events.infra.response.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) 
public class Event_DetailsDTO extends Event_BaseDTO {
	
	private String image;
	private String shortDescription;
	private String fullDescription;	
	private VenueDTO venue;
	private StageDTO stage;
	private List<PresenterDTO> presenters = new ArrayList<>();
	
}
