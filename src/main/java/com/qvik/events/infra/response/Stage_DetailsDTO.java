package com.qvik.events.infra.response;

import com.qvik.events.modules.venue.Venue;
import lombok.Data;

@Data
public class Stage_DetailsDTO extends StageDTO{
    private String location;
    private Integer capacity;
    private String type;
    private Venue venue;
}
