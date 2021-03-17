package com.qvik.events.infra.response.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Presenter_DetailsDTO extends PresenterDTO{
    private String contact;
    private String shortDescription;
    private String fullDescription;
    List<Event_BaseDTO> events = new ArrayList<>();
}
