package com.qvik.events.infra.response;


import java.util.List;


import lombok.Data;

@Data
public class Sub_EventDTO extends Event_BaseDTO {
	
	private List<Event_StageDTO> event_stages;
}
