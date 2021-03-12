package com.qvik.events.infra.response;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) 
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
    private List<String> allTags = new ArrayList<>();
}
