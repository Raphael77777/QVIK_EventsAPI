package com.qvik.events.infra.response.dto;

import lombok.Data;

@Data
public class Venue_DetailsDTO extends VenueDTO{
    private String city;
    private String address;
    private String contact;
    private String transportation;
    private String facilities;
}
