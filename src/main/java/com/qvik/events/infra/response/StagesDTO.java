package com.qvik.events.infra.response;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class StagesDTO {

	private Set<Event_StageDTO> eventStages = new HashSet<>();
}
