package com.qvik.events.domain;

import javax.persistence.*;

/** Entity for table Event_Presenter */
@Entity
@Table(name = "event_presenter")
public class Event_Presenter {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "event_presenter_id", nullable = false, updatable = false)
    private long event_presenter_id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "presenter_id")
    private Presenter presenter;

    @Column(name = "description", nullable = true)
    private String description;

    public Event_Presenter() {
    }

    public Event_Presenter(String description) {
        this.description = description;
    }

    public long getEvent_presenter_id() {
        return event_presenter_id;
    }

    public void setEvent_presenter_id(long event_presenter_id) {
        this.event_presenter_id = event_presenter_id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Presenter getPresenter() {
        return presenter;
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Event_Presenter{" +
                "event_presenter_id=" + event_presenter_id +
                ", event=" + event +
                ", presenter=" + presenter +
                ", description='" + description + '\'' +
                '}';
    }
}
