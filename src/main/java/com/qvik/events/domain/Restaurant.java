package com.qvik.events.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.sql.Time;
import java.util.List;

/** Entity for table Restaurant */
@Entity
@Table(name = "restaurant")
public class Restaurant {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "restaurant_id", nullable = false, updatable = false)
    private long restaurant_id;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "location", nullable = false)
    private String location;
    @Column(name = "open_date_time", nullable = false)
    private Time open_date_time;
    @Column(name = "close_date_time", nullable = false)
    private Time close_date_time;
    @Column(name = "short_description", nullable = false)
    private String short_description;
    @Column(name = "full_description", nullable = false)
    private String full_description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "restaurant")
    @JsonBackReference
    private List<Event_Restaurant> event_restaurants;

    public Restaurant() {
    }

    public Restaurant(String name, String location, Time open_date_time, Time close_date_time, String short_description, String full_description) {
        this.name = name;
        this.location = location;
        this.open_date_time = open_date_time;
        this.close_date_time = close_date_time;
        this.short_description = short_description;
        this.full_description = full_description;
    }

    public long getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(long presenter_id) {
        this.restaurant_id = presenter_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Time getOpen_date_time() {
        return open_date_time;
    }

    public void setOpen_date_time(Time open_date_time) {
        this.open_date_time = open_date_time;
    }

    public Time getClose_date_time() {
        return close_date_time;
    }

    public void setClose_date_time(Time close_date_time) {
        this.close_date_time = close_date_time;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public String getFull_description() {
        return full_description;
    }

    public void setFull_description(String full_description) {
        this.full_description = full_description;
    }

    public List<Event_Restaurant> getEvent_restaurants() {
        return event_restaurants;
    }

    public void setEvent_restaurants(List<Event_Restaurant> event_restaurants) {
        this.event_restaurants = event_restaurants;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "restaurant_id=" + restaurant_id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", open_date_time=" + open_date_time +
                ", close_date_time=" + close_date_time +
                ", short_description='" + short_description + '\'' +
                ", full_description='" + full_description + '\'' +
                '}';
    }
}
