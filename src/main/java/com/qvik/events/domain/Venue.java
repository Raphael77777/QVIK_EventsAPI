package com.qvik.events.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/** Entity for table Venue */
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "venue_id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "venue")
public class Venue {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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

	public Venue(String name, String city, String address, String contact) {
		this.name = name;
		this.city = city;
		this.address = address;
		this.contact = contact;
	}
}
