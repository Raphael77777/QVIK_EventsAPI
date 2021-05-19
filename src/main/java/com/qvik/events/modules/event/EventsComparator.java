package com.qvik.events.modules.event;

import com.qvik.events.infra.response.dto.Event_DetailsDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;

public class EventsComparator implements Comparator<Event_DetailsDTO> {

    @Override
    public int compare(Event_DetailsDTO a, Event_DetailsDTO b) {
        LocalDate localDate_A = a.getStartDate();
        LocalDate localDate_B = b.getStartDate();
        LocalTime localTime_A = a.getStartTime();
        LocalTime localTime_B = b.getStartTime();

        if (localDate_A.isBefore(localDate_B)){
            return -1;
        }

        if (localDate_A.isEqual(localDate_B)){
            if (localTime_A.isBefore(localTime_B)){
                return -1;
            }

            if (localTime_A.equals(localTime_B)){
                return 0;
            }

            return 1;
        }

        return 1;
    }
}
