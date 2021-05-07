package com.qvik.events.infra.response.admindto;

import lombok.Data;

@Data
public class LinkToDTO {

	private String operation;
	private Long sourceId;
	private Long destinationId;
}
