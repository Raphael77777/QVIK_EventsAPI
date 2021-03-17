package com.qvik.events.modules.restaurant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.qvik.events.modules.event.Event;

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
@EqualsAndHashCode(of = "eventRestaurantId")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "event_restaurant")
public class Event_Restaurant {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "event_restaurant_id", nullable = false, updatable = false)
	private long eventRestaurantId;

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

	//TODO : Temporary output to be removed at the end of development
	@Override
	public String toString() {
		return "Event_Restaurant{" +
				"event_restaurant_id=" + eventRestaurantId +
				", event=" + event +
				", restaurant=" + restaurant +
				", description='" + description + '\'' +
				'}';
	}
}
