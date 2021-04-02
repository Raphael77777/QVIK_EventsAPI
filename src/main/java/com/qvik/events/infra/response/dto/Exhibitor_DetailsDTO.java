package com.qvik.events.infra.response.dto;

import lombok.Data;

@Data
public class Exhibitor_DetailsDTO extends ExhibitorDTO {
    private String location;
    private String contact;
    private String shortDescription;
    private String fullDescription;
}
