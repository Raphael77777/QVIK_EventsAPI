package com.qvik.events.infra.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ParentEvent_DetailsDTO extends Event_DetailsDTO {
	
	private List<String> allTags = new ArrayList<>();

}
