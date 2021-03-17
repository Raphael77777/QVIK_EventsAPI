package com.qvik.events.infra.response.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Parent_EventDTO extends Event_BaseDTO {

	private List<String> allEventTags = new ArrayList<>();
	private String venue;

}
