package com.qvik.events.modules.event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.qvik.events.modules.exhibitor.Event_Exhibitor;
import com.qvik.events.modules.presenter.Event_Presenter;
import com.qvik.events.modules.restaurant.Event_Restaurant;
import com.qvik.events.modules.stage.Event_Stage;
import com.qvik.events.modules.venue.Event_Venue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Entity for table Event */
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "event_id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "event")
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "event_id", nullable = false, updatable = false)
	private long event_id;
	
	@Column(name = "start_date", nullable = false)
	private LocalDate startDate;
	
	@Column(name = "start_time", nullable = false)
	private LocalTime startTime;
	
	@Column(name = "end_time", nullable = false)
	private LocalTime endTime;
	
	@Column(name = "end_date", nullable = false)
	private LocalDate endDate;
	
	@Column(name = "short_description", nullable = false)
	private String shortDescription;
	
	@Lob
	@Basic(fetch = FetchType.EAGER)
	@Column(name = "full_description", nullable = false)
	private String fullDescription;
	
	@Column(name = "category", nullable = false)
	private String category;
	
	@Lob
	@Basic(fetch = FetchType.EAGER)
	@Column(name = "image", nullable = false)
	private String image;
	
	@Column(name = "status", nullable = false)
	private String status;
	
	@Column(name = "last_modified", nullable = false)
	private LocalDateTime lastModified;
	
	@Column(name = "has_exhibitor", nullable = false)
	private boolean hasExhibitor;
	
	@Column(name = "has_restaurant", nullable = false)
	private boolean hasRestaurant;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "parentEvent")
	@JsonBackReference
	private List<Event> subEvents;

	@ManyToOne
	@JoinColumn(name = "parent_event")
	private Event parentEvent;

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

	public Event(LocalDate start_date, LocalTime start_time, LocalDate end_date, LocalTime end_time, String short_description, String full_description,
			String category, String image, String status, LocalDateTime last_modified, boolean has_exhibitor,
			boolean has_restaurant) {
		this.startDate = start_date;
		this.startTime = start_time;
		this.endDate = end_date;
		this.endTime = end_time;
		this.shortDescription = short_description;
		this.fullDescription = full_description;
		this.category = category;
		this.image = image;
		this.status = status;
		this.lastModified = last_modified;
		this.hasExhibitor = has_exhibitor;
		this.hasRestaurant = has_restaurant;
	}

	//TODO : Temporary output to be removed at the end of development
	@Override
	public String toString() {
		if (parentEvent == null) {
			return "Event{" + "event_id=" + event_id + ", start_date=" + startDate + ", end_date="
					+ endDate + ", short_description='" + shortDescription + '\'' + ", full_description='"
					+ fullDescription + '\'' + ", category='" + category + '\'' + ", image='" + image + '\''
					+ ", status='" + status + '\'' + ", last_modified=" + lastModified + ", has_exhibitor="
					+ hasExhibitor + ", has_restaurant=" + hasRestaurant + '}';
		} else {
			return "Event{" + "event_id=" + event_id + ", start_date=" + startDate + ", end_date="
					+ endDate + ", short_description='" + shortDescription + '\'' + ", full_description='"
					+ fullDescription + '\'' + ", category='" + category + '\'' + ", image='" + image + '\''
					+ ", status='" + status + '\'' + ", last_modified=" + lastModified + ", has_exhibitor="
					+ hasExhibitor + ", has_restaurant=" + hasRestaurant + ", parent_event=" + parentEvent + '}';
		}
	}
}
