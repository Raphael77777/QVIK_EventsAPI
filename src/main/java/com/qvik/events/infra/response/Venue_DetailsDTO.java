package com.qvik.events.infra.response;

import lombok.Data;

@Data
public class Venue_DetailsDTO extends VenueDTO{
    private String city;
    private String address;
    private String contact;
}
