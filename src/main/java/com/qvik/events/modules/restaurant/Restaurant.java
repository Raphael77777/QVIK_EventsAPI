package com.qvik.events.modules.restaurant;

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
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.qvik.events.modules.venue.Venue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Entity for table Restaurant */
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "restaurant_id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "restaurant")
public class Restaurant {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "restaurant_id", nullable = false, updatable = false)
	private long restaurant_id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "location", nullable = false)
	private String location;
	
	@JsonSerialize(using = LocalTimeSerializer.class)
	@JsonFormat(pattern = "HH:mm:ss")
	@Column(name = "open_time", nullable = false)
	private LocalTime openTime;
	
	@JsonSerialize(using = LocalTimeSerializer.class)
	@JsonFormat(pattern = "HH:mm:ss")
	@Column(name = "close_time", nullable = false)
	private LocalTime closeTime;

	@Column(name = "short_description", nullable = false)
	private String shortDescription;

	@Lob
	@Basic(fetch = FetchType.EAGER)
	@Column(name = "full_description", nullable = false)
	private String fullDescription;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "venue_id")
	private Venue venue;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "restaurant")
	@JsonBackReference
	private List<Event_Restaurant> event_restaurants;

	public Restaurant(String name, String location, LocalTime open_time, LocalTime close_time, String short_description,
			String full_description) {
		this.name = name;
		this.location = location;
		this.openTime = open_time;
		this.closeTime = close_time;
		this.shortDescription = short_description;
		this.fullDescription = full_description;
	}

	// TODO : Temporary output to be removed at the end of development
	@Override
	public String toString() {
		return "Restaurant{" + "restaurant_id=" + restaurant_id + ", name='" + name + '\'' + ", location='" + location
				+ '\'' + ", openTime=" + openTime + ", closeTime=" + closeTime + ", shortDescription='"
				+ shortDescription + '\'' + ", fullDescription='" + fullDescription + '\'' + ", venue=" + venue + '}';
	}
}
