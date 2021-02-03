package com.qvik.events.domain;

import javax.persistence.*;

/** Entity for table Event_Exhibitor */
@Entity
@Table(name = "event_exhibitor")
public class Event_Exhibitor {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "event_exhibitor_id", nullable = false, updatable = false)
    private long event_exhibitor_id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "exhibitor_id")
    private Exhibitor exhibitor;

    @Column(name = "description", nullable = true)
    private String description;

    public Event_Exhibitor() {
    }

    public Event_Exhibitor(String description) {
        this.description = description;
    }

    public long getEvent_exhibitor_id() {
        return event_exhibitor_id;
    }

    public void setEvent_exhibitor_id(long event_exhibitor_id) {
        this.event_exhibitor_id = event_exhibitor_id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Exhibitor getExhibitor() {
        return exhibitor;
    }

    public void setExhibitor(Exhibitor exhibitor) {
        this.exhibitor = exhibitor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Event_Exhibitor{" +
                "event_exhibitor_id=" + event_exhibitor_id +
                ", event=" + event +
                ", exhibitor=" + exhibitor +
                ", description='" + description + '\'' +
                '}';
    }
}
