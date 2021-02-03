package com.qvik.events.domain;

import javax.persistence.*;

/** Entity for table Event_Venue */
@Entity
@Table(name = "event_venue")
public class Event_Venue {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "event_venue_id", nullable = false, updatable = false)
    private long event_venue_id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;

    @Column(name = "description", nullable = true)
    private String description;

    public Event_Venue() {
    }

    public Event_Venue(String description) {
        this.description = description;
    }

    public long getEvent_venue_id() {
        return event_venue_id;
    }

    public void setEvent_venue_id(long event_venue_id) {
        this.event_venue_id = event_venue_id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Event_Venue{" +
                "event_venue_id=" + event_venue_id +
                ", event=" + event +
                ", venue=" + venue +
                ", description='" + description + '\'' +
                '}';
    }
}
