package com.qvik.events.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;

/** Entity for table Exhibitor */
@Entity
@Table(name = "exhibitor")
public class Exhibitor {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "exhibitor_id", nullable = false, updatable = false)
    private long exhibitor_id;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "location", nullable = false)
    private String location;
    @Column(name = "contact", nullable = false)
    private String contact;
    @Column(name = "short_description", nullable = false)
    private String short_description;
    @Column(name = "full_description", nullable = false)
    private String full_description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "exhibitor")
    @JsonBackReference
    private List<Event_Exhibitor> event_exhibitors;

    public Exhibitor() {
    }

    public Exhibitor(String name, String location, String contact, String short_description, String full_description) {
        this.name = name;
        this.location = location;
        this.contact = contact;
        this.short_description = short_description;
        this.full_description = full_description;
    }

    public long getExhibitor_id() {
        return exhibitor_id;
    }

    public void setExhibitor_id(long exhibitor_id) {
        this.exhibitor_id = exhibitor_id;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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

    public List<Event_Exhibitor> getEvent_exhibitors() {
        return event_exhibitors;
    }

    public void setEvent_exhibitors(List<Event_Exhibitor> event_exhibitors) {
        this.event_exhibitors = event_exhibitors;
    }

    @Override
    public String toString() {
        return "Exhibitor{" +
                "exhibitor_id=" + exhibitor_id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", contact='" + contact + '\'' +
                ", short_description='" + short_description + '\'' +
                ", full_description='" + full_description + '\'' +
                '}';
    }
}
