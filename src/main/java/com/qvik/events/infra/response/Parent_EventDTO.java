package com.qvik.events.infra.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Parent_EventDTO extends Event_BaseDTO {	

	
	private String venue;
	private List<String> tags = new ArrayList<>();

}
