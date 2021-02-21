package com.qvik.events.infra.response;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class Event_DetailsWithVenueDTO extends Event_DetailsDTO {

	private VenueDTO venue;

}
