package com.qvik.events.modules.venue;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.qvik.events.modules.event.Event;
import com.qvik.events.modules.restaurant.Restaurant;
import com.qvik.events.modules.stage.Stage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
	private List<Restaurant> restaurants;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "venue")
	@JsonBackReference
	private List<Event> events;

	public Venue(String name, String city, String address, String contact) {
		this.name = name;
		this.city = city;
		this.address = address;
		this.contact = contact;
	}

	//TODO : Temporary output to be removed at the end of development
	@Override
	public String toString() {
		return "Venue{" +
				"venue_id=" + venue_id +
				", name='" + name + '\'' +
				", city='" + city + '\'' +
				", address='" + address + '\'' +
				", contact='" + contact + '\'' +
				'}';
	}
}
