package com.qvik.events.infra.response.admindto;

import com.qvik.events.modules.event.Event;
import com.qvik.events.modules.stage.Stage;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EventADTO {

    private Event event = new Event();

    private LinkToDTO linkEventStage = new LinkToDTO();
    private LinkToDTO linkEventVenue = new LinkToDTO();
    private LinkToDTO linkEventImage = new LinkToDTO();

    private List<LinkToDTO> linkEventExhibitors = new ArrayList<>();
    private List<LinkToDTO> linkEventPresenters = new ArrayList<>();
    private List<LinkToDTO> linkEventRestaurants = new ArrayList<>();
    private List<LinkToDTO> linkEventTags = new ArrayList<>();

}
