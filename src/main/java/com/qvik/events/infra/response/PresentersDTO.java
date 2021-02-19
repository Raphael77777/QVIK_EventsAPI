package com.qvik.events.infra.response;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class PresentersDTO {

	private Set<Event_PresenterDTO> eventPresenters = new HashSet<>();
}
