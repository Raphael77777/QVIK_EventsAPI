package com.qvik.events.domain;

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
	private String short_description;
	
	@Lob
	@Basic(fetch = FetchType.EAGER)
	@Column(name = "full_description", nullable = false)
	private String full_description;
	
	@Column(name = "category", nullable = false)
	private String category;
	
	@Lob
	@Basic(fetch = FetchType.EAGER)
	@Column(name = "image", nullable = false)
	private String image;
	
	@Column(name = "status", nullable = false)
	private String status;
	
	@Column(name = "last_modified", nullable = false)
	private LocalDateTime last_modified;
	
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

	public Event(LocalDate start_date, LocalTime start_time, LocalDate end_date, LocalTime end_time, String short_description, String full_description,
			String category, String image, String status, LocalDateTime last_modified, boolean has_exhibitor,
			boolean has_restaurant) {
		this.startDate = start_date;
		this.startTime = start_time;
		this.endDate = end_date;
		this.endTime = end_time;
		this.short_description = short_description;
		this.full_description = full_description;
		this.category = category;
		this.image = image;
		this.status = status;
		this.last_modified = last_modified;
		this.has_exhibitor = has_exhibitor;
		this.has_restaurant = has_restaurant;
	}

	@Override
	public String toString() {
		if (parent_event == null) {
			return "Event{" + "event_id=" + event_id + ", start_date=" + startDate + ", end_date="
					+ endDate + ", short_description='" + short_description + '\'' + ", full_description='"
					+ full_description + '\'' + ", category='" + category + '\'' + ", image='" + image + '\''
					+ ", status='" + status + '\'' + ", last_modified=" + last_modified + ", has_exhibitor="
					+ has_exhibitor + ", has_restaurant=" + has_restaurant + '}';
		} else {
			return "Event{" + "event_id=" + event_id + ", start_date=" + startDate + ", end_date="
					+ endDate + ", short_description='" + short_description + '\'' + ", full_description='"
					+ full_description + '\'' + ", category='" + category + '\'' + ", image='" + image + '\''
					+ ", status='" + status + '\'' + ", last_modified=" + last_modified + ", has_exhibitor="
					+ has_exhibitor + ", has_restaurant=" + has_restaurant + ", parent_event=" + parent_event + '}';
		}
	}
}
