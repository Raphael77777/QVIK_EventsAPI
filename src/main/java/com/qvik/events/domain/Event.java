package com.qvik.events.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/** Entity for table Event */
@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "event_id", nullable = false, updatable = false)
    private long event_id;

    @Column(name = "start_date_time", nullable = false)
    private Date start_date_time;
    @Column(name = "end_date_time", nullable = false)
    private Date end_date_time;
    @Column(name = "short_description", nullable = false)
    private String short_description;
    @Column(name = "full_description", nullable = false)
    private String full_description;
    @Column(name = "category", nullable = false )
    private String category;
    @Column(name = "image", nullable = false)
    private String image;
    @Column(name = "status", nullable = false)
    private String status;
    @Column(name = "last_modified", nullable = false)
    private Date last_modified;
    @Column(name = "has_exhibitor", nullable = false)
    private boolean has_exhibitor;
    @Column(name = "has_restaurant", nullable = false)
    private boolean has_restaurant;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent_event")
    @JsonBackReference
    private List<Event> sub_events;

    @ManyToOne
    @JoinColumn(name = "parent_event")
    private Event parent_event;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    @JsonBackReference
    private List<Event_Venue> event_venues;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    @JsonBackReference
    private List<Event_Stage> event_stages;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    @JsonBackReference
    private List<Event_Presenter> event_presenters;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    @JsonBackReference
    private List<Event_Restaurant> event_restaurants;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    @JsonBackReference
    private List<Event_Exhibitor> event_exhibitors;

    public Event() {
    }

    public Event(Date start_date_time, Date end_date_time, String short_description, String full_description, String category, String image, String status, Date last_modified, boolean has_exhibitor, boolean has_restaurant) {
        this.start_date_time = start_date_time;
        this.end_date_time = end_date_time;
        this.short_description = short_description;
        this.full_description = full_description;
        this.category = category;
        this.image = image;
        this.status = status;
        this.last_modified = last_modified;
        this.has_exhibitor = has_exhibitor;
        this.has_restaurant = has_restaurant;
    }

    public long getEvent_id() {
        return event_id;
    }

    public void setEvent_id(long event_id) {
        this.event_id = event_id;
    }

    public Date getStart_date_time() {
        return start_date_time;
    }

    public void setStart_date_time(Date start_date_time) {
        this.start_date_time = start_date_time;
    }

    public Date getEnd_date_time() {
        return end_date_time;
    }

    public void setEnd_date_time(Date end_date_time) {
        this.end_date_time = end_date_time;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getLast_modified() {
        return last_modified;
    }

    public void setLast_modified(Date last_modified) {
        this.last_modified = last_modified;
    }

    public boolean isHas_exhibitor() {
        return has_exhibitor;
    }

    public void setHas_exhibitor(boolean has_exhibitor) {
        this.has_exhibitor = has_exhibitor;
    }

    public boolean isHas_restaurant() {
        return has_restaurant;
    }

    public void setHas_restaurant(boolean has_restaurant) {
        this.has_restaurant = has_restaurant;
    }

    public List<Event> getSub_events() {
        return sub_events;
    }

    public void setSub_events(List<Event> sub_events) {
        this.sub_events = sub_events;
    }

    public Event getParent_event() {
        return parent_event;
    }

    public void setParent_event(Event parent_event) {
        this.parent_event = parent_event;
    }

    public List<Event_Venue> getEvent_venues() {
        return event_venues;
    }

    public void setEvent_venues(List<Event_Venue> event_venues) {
        this.event_venues = event_venues;
    }

    public List<Event_Stage> getEvent_stages() {
        return event_stages;
    }

    public void setEvent_stages(List<Event_Stage> event_stages) {
        this.event_stages = event_stages;
    }

    public List<Event_Presenter> getEvent_presenters() {
        return event_presenters;
    }

    public void setEvent_presenters(List<Event_Presenter> event_presenters) {
        this.event_presenters = event_presenters;
    }

    public List<Event_Restaurant> getEvent_restaurants() {
        return event_restaurants;
    }

    public void setEvent_restaurants(List<Event_Restaurant> event_restaurants) {
        this.event_restaurants = event_restaurants;
    }

    public List<Event_Exhibitor> getEvent_exhibitors() {
        return event_exhibitors;
    }

    public void setEvent_exhibitors(List<Event_Exhibitor> event_exhibitors) {
        this.event_exhibitors = event_exhibitors;
    }

    @Override
    public String toString() {
        if (parent_event == null){
            return "Event{" +
                    "event_id=" + event_id +
                    ", start_date_time=" + start_date_time +
                    ", end_date_time=" + end_date_time +
                    ", short_description='" + short_description + '\'' +
                    ", full_description='" + full_description + '\'' +
                    ", category='" + category + '\'' +
                    ", image='" + image + '\'' +
                    ", status='" + status + '\'' +
                    ", last_modified=" + last_modified +
                    ", has_exhibitor=" + has_exhibitor +
                    ", has_restaurant=" + has_restaurant +
                    '}';
        }else {
            return "Event{" +
                    "event_id=" + event_id +
                    ", start_date_time=" + start_date_time +
                    ", end_date_time=" + end_date_time +
                    ", short_description='" + short_description + '\'' +
                    ", full_description='" + full_description + '\'' +
                    ", category='" + category + '\'' +
                    ", image='" + image + '\'' +
                    ", status='" + status + '\'' +
                    ", last_modified=" + last_modified +
                    ", has_exhibitor=" + has_exhibitor +
                    ", has_restaurant=" + has_restaurant +
                    ", parent_event=" + parent_event +
                    '}';
        }
    }
}
