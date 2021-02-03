package com.qvik.events.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.sql.Time;
import java.util.List;

/** Entity for table Venue */
@Entity
@Table(name = "venue")
public class Venue {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "venue_id", nullable = false, updatable = false)
    private long venue_id;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "city", nullable = false)
    private String city;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "contact", nullable = false)
    private String contact;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "venue")
    @JsonBackReference
    private List<Stage> stages;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "venue")
    @JsonBackReference
    private List<Event_Venue> event_venues;

    public Venue() {
    }

    public Venue(String name, String city, String address, String contact) {
        this.name = name;
        this.city = city;
        this.address = address;
        this.contact = contact;
    }

    public long getVenue_id() {
        return venue_id;
    }

    public void setVenue_id(long venue_id) {
        this.venue_id = venue_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public List<Stage> getStages() {
        return stages;
    }

    public void setStages(List<Stage> stages) {
        this.stages = stages;
    }

    public List<Event_Venue> getEvent_venues() {
        return event_venues;
    }

    public void setEvent_venues(List<Event_Venue> event_venues) {
        this.event_venues = event_venues;
    }

    @Override
    public String toString() {
        return "Venue{" +
                "venue_id=" + venue_id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                '}';

    }
}
