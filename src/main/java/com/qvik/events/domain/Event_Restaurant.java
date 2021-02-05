package com.qvik.events.domain;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Entity for table Event_Restaurant */
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "event_restaurant_id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "event_restaurant")
public class Event_Restaurant {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "event_restaurant_id", nullable = false, updatable = false)
	private long event_restaurant_id;

	@ManyToOne
	@JoinColumn(name = "event_id")
	private Event event;

	@ManyToOne
	@JoinColumn(name = "restaurant_id")
	private Restaurant restaurant;

	@Column(name = "description", nullable = true)
	private String description;

	public Event_Restaurant(String description) {
		this.description = description;
	}
}
