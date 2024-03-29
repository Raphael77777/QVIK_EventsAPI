package com.qvik.events.modules.event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
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
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.qvik.events.modules.exhibitor.Event_Exhibitor;
import com.qvik.events.modules.image.Image;
import com.qvik.events.modules.presenter.Event_Presenter;
import com.qvik.events.modules.restaurant.Event_Restaurant;
import com.qvik.events.modules.stage.Stage;
import com.qvik.events.modules.tag.Event_Tag;
import com.qvik.events.modules.venue.Venue;

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
@EqualsAndHashCode(of = "eventId")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "event")
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "event_id", nullable = false, updatable = false)
	private long eventId;

	@Column(name = "start_date", nullable = false)
	private LocalDate startDate;

	@Column(name = "start_time", nullable = false)
	private LocalTime startTime;

	@Column(name = "end_date", nullable = false)
	private LocalDate endDate;

	@Column(name = "end_time", nullable = false)
	private LocalTime endTime;

	@Column(name = "title", nullable = false)
	private String title;
	
	@Column(name = "short_description", nullable = false)
	private String shortDescription;

	@Lob
	@Basic(fetch = FetchType.EAGER)
	@Column(name = "full_description", nullable = false)
	private String fullDescription;

	@Column(name = "is_active", nullable = false)
	private boolean isActive;

	@Column(name = "is_main_event", nullable = false)
	private boolean isMainEvent;

	@Column(name = "has_exhibitor", nullable = false)
	private boolean hasExhibitor;

	@Column(name = "has_restaurant", nullable = false)
	private boolean hasRestaurant;

	@Column(name = "has_presenter", nullable = false)
	private boolean hasPresenter;

	@Column(name = "last_modified")
	@JsonIgnore
	private LocalDateTime lastModified;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "parentEvent")
	@JsonBackReference
	@JsonIgnore
	private List<Event> subEvents;

	@ManyToOne
	@JoinColumn(name = "parent_event")
	@JsonIgnore
	private Event parentEvent;

	@ManyToOne
	@JoinColumn(name = "venue_id")
	@JsonIgnore
	private Venue venue;

	@ManyToOne
	@JoinColumn(name = "stage_id")
	@JsonIgnore
	private Stage stage;

	@ManyToOne
	@JoinColumn(name = "image_id")
	@JsonIgnore
	private Image image;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
	@JsonBackReference
	@JsonIgnore
	private List <Event_Presenter> eventPresenters = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
	@JsonBackReference
	@JsonIgnore
	private List<Event_Restaurant> eventRestaurants= new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
	@JsonBackReference
	@JsonIgnore
	private List<Event_Exhibitor> eventExhibitors= new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
	@JsonBackReference
	@JsonIgnore
	private List<Event_Tag> eventTags= new ArrayList<>();

	public Event(LocalDate start_date, LocalTime start_time, LocalDate end_date, LocalTime end_time, String title,
			String shortDescription, LocalDateTime last_modified, boolean isActive, boolean isMainEvent, boolean has_exhibitor,
			boolean has_restaurant, boolean has_presenter) {
		this.startDate = start_date;
		this.startTime = start_time;
		this.endDate = end_date;
		this.endTime = end_time;
		this.shortDescription = shortDescription;
		this.title = title;
		this.lastModified = last_modified;
		this.isActive=isActive;
		this.isMainEvent=isMainEvent;
		this.hasExhibitor = has_exhibitor;
		this.hasRestaurant = has_restaurant;
		this.hasPresenter = has_presenter;
	}

	// TODO : Temporary output to be removed at the end of development

	@Override
	public String toString() {
		return "Event{" +
				"eventId=" + eventId +
				", startDate=" + startDate +
				", startTime=" + startTime +
				", endDate=" + endDate +
				", endTime=" + endTime +
				", title='" + title + '\'' +
				", shortDescription='" + shortDescription + '\'' +
				", fullDescription='" + fullDescription + '\'' +
				", isActive=" + isActive +
				", isMainEvent=" + isMainEvent +
				", hasExhibitor=" + hasExhibitor +
				", hasRestaurant=" + hasRestaurant +
				", hasPresenter=" + hasPresenter +
				", lastModified=" + lastModified +
				", venue=" + venue +
				", stage=" + stage +
				", image=" + image +
				'}';
	}
}
