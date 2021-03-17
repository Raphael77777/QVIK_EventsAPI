package com.qvik.events.infra.response.dto;

import lombok.Data;

@Data
public class Presenter_DetailsDTO extends PresenterDTO{
    private String contact;
    private String shortDescription;
    private String fullDescription;
}
