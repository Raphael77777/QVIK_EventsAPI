package com.qvik.events.domain;

import javax.persistence.*;

/** Entity for table Event_Restaurant */
@Entity
@Table(name = "event_restaurant")
public class Event_Restaurant {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "event_restaurant_id", nullable = false, updatable = false)
    private long event_restaurant_id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Column(name = "description", nullable = true)
    private String description;

    public Event_Restaurant() {
    }

    public Event_Restaurant(String description) {
        this.description = description;
    }

    public long getEvent_restaurant_id() {
        return event_restaurant_id;
    }

    public void setEvent_restaurant_id(long event_restaurant_id) {
        this.event_restaurant_id = event_restaurant_id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Event_Restaurant{" +
                "event_restaurant_id=" + event_restaurant_id +
                ", event=" + event +
                ", restaurant=" + restaurant +
                ", description='" + description + '\'' +
                '}';
    }
}
