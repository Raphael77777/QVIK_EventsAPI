package com.qvik.events.infra.response;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class Event_DetailsWithStageDTO extends Event_DetailsDTO {

	private StageDTO stage;

}
