package com.qvik.events.infra.response.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class ExhibitorsDTO {

	private Set<Event_ExhibitorDetailsDTO> eventExhibitors = new HashSet<>();
}
