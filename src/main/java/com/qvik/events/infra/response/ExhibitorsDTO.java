package com.qvik.events.infra.response;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class ExhibitorsDTO {

	private Set<Event_ExhibitorDTO> eventExhibitors = new HashSet<>();
}
