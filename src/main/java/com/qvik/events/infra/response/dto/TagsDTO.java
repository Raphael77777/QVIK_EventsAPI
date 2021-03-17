package com.qvik.events.infra.response.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class TagsDTO {

	private Set<Event_TagDTO> eventTags = new HashSet<>();
}
