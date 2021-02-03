package com.qvik.events.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

/** Entity for table Stage */
@Entity
@Table(name = "stage")
public class Stage {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "stage_id", nullable = false, updatable = false)
    private long stage_id;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "location", nullable = false)
    private String location;
    @Column(name = "capacity", nullable = false)
    private Integer capacity;
    @Column(name = "type", nullable = false)
    private String type;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "venue_id")
    private Venue venue;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stage")
    @JsonBackReference
    private List<Event_Stage> event_stages;

    public Stage() {
    }

    public Stage(String name, String location, Integer capacity, String type) {
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.type = type;
    }

    public long getStage_id() {
        return stage_id;
    }

    public void setStage_id(long stage_id) {
        this.stage_id = stage_id;
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

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public List<Event_Stage> getEvent_stages() {
        return event_stages;
    }

    public void setEvent_stages(List<Event_Stage> event_stages) {
        this.event_stages = event_stages;
    }

    @Override
    public String toString() {
        return "Stage{" +
                "stage_id=" + stage_id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", capacity=" + capacity +
                ", type='" + type + '\'' +
                ", venue=" + venue +
                '}';
    }
}
