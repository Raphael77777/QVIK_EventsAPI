package com.qvik.events.infra.response;


import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import com.qvik.events.modules.stage.Stage;
import lombok.Data;

@Data
public class Sub_EventDTO extends Event_BaseDTO {
	
	private String stage;
	private List<String> tags = new ArrayList<>();
	private List<String> presenters = new ArrayList<>();
}
