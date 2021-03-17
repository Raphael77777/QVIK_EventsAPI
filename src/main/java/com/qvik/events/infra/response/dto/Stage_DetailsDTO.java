package com.qvik.events.infra.response.dto;

import java.util.ArrayList;
import java.util.List;

import com.qvik.events.modules.venue.Venue;
import lombok.Data;

@Data
public class Stage_DetailsDTO extends StageDTO{
    private String location;
    private Integer capacity;
    private String type;
    private VenueDTO venue;
    List<Event_BaseDTO> events = new ArrayList<>();
}
