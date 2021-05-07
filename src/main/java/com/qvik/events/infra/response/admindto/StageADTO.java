package com.qvik.events.infra.response.admindto;

import com.qvik.events.modules.stage.Stage;
import lombok.Data;

@Data
public class StageADTO{

    private Stage stage = new Stage();
    private LinkToDTO linkStageVenue = new LinkToDTO();

}
