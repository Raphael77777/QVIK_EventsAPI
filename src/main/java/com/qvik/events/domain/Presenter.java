package com.qvik.events.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;

/** Entity for table Presenter */
@Entity
@Table(name = "presenter")
public class Presenter {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "presenter_id", nullable = false, updatable = false)
    private long presenter_id;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "contact", nullable = false)
    private String contact;
    @Column(name = "short_description", nullable = false)
    private String short_description;
    @Column(name = "full_description", nullable = false)
    private String full_description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "presenter")
    @JsonBackReference
    private List<Event_Presenter> event_presenters;

    public Presenter() {
    }

    public Presenter(String name, String contact, String short_description, String full_description) {
        this.name = name;
        this.contact = contact;
        this.short_description = short_description;
        this.full_description = full_description;
    }

    public long getPresenter_id() {
        return presenter_id;
    }

    public void setPresenter_id(long presenter_id) {
        this.presenter_id = presenter_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<Event_Presenter> getEvent_presenters() {
        return event_presenters;
    }

    public void setEvent_presenters(List<Event_Presenter> event_presenters) {
        this.event_presenters = event_presenters;
    }

    @Override
    public String toString() {
        return "Presenter{" +
                "presenter_id=" + presenter_id +
                ", name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                ", short_description='" + short_description + '\'' +
                ", full_description='" + full_description + '\'' +
                '}';
    }
}
