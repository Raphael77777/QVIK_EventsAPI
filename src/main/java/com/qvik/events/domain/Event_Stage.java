package com.qvik.events.domain;

import javax.persistence.*;

/** Entity for table Event_Stage */
@Entity
@Table(name = "event_stage")
public class Event_Stage {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "event_stage_id", nullable = false, updatable = false)
    private long event_stage_id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "stage_id")
    private Stage stage;

    @Column(name = "description", nullable = true)
    private String description;

    public Event_Stage() {
    }

    public Event_Stage(String description) {
        this.description = description;
    }

    public long getEvent_stage_id() {
        return event_stage_id;
    }

    public void setEvent_stage_id(long event_stage_id) {
        this.event_stage_id = event_stage_id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Event_Stage{" +
                "event_stage_id=" + event_stage_id +
                ", event=" + event +
                ", stage=" + stage +
                ", description='" + description + '\'' +
                '}';
    }
}
