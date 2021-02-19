package com.qvik.events.infra.response;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class VenuesDTO {

	private Set<Event_VenueDTO> eventVenues = new HashSet<>();
}
