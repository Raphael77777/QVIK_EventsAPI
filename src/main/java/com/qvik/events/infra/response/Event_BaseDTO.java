package com.qvik.events.infra.response;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import lombok.Data;

@Data
public class Event_BaseDTO {
	
	private long event_id;

	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate startDate;

	@JsonSerialize(using = LocalTimeSerializer.class)
	@JsonFormat(pattern = "HH:mm:ss")
	private LocalTime startTime;

	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate endDate;

	@JsonSerialize(using = LocalTimeSerializer.class)
	@JsonFormat(pattern = "HH:mm:ss")
	private LocalTime endTime;

	private String title;
}
