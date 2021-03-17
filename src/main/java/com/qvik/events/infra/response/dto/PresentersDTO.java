package com.qvik.events.infra.response.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class PresentersDTO {

	private Set<Event_PresenterDetailsDTO> eventPresenters = new HashSet<>();
}
