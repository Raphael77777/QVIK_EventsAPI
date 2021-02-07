package com.qvik.events.domain;

import java.sql.Time;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
	
	@Column(name = "open_date_time", nullable = false)
	private Time open_date_time;
	
	@Column(name = "close_date_time", nullable = false)
	private Time close_date_time;
	
	@Column(name = "short_description", nullable = false)
	private String short_description;
	
	@Lob
	@Basic(fetch = FetchType.EAGER)
	@Column(name = "full_description", nullable = false)
	private String full_description;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "restaurant")
	@JsonBackReference
	private List<Event_Restaurant> event_restaurants;

	public Restaurant(String name, String location, Time open_date_time, Time close_date_time, String short_description,
			String full_description) {
		this.name = name;
		this.location = location;
		this.open_date_time = open_date_time;
		this.close_date_time = close_date_time;
		this.short_description = short_description;
		this.full_description = full_description;
	}
}
