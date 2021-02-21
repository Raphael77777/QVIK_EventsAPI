package com.qvik.events.infra.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.Data;

import java.time.LocalTime;

@Data
public class Restaurant_DetailsDTO extends RestaurantDTO{
    private String location;

    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime openTime;

    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime closeTime;

    private String shortDescription;
    private String fullDescription;

    private VenueDTO venue;
}
