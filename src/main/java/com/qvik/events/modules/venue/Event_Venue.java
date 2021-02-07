package com.qvik.events.domain;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Entity for table Event_Venue */
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "event_venue_id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "event_venue")
public class Event_Venue {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "event_venue_id", nullable = false, updatable = false)
	private long event_venue_id;

	@ManyToOne
	@JoinColumn(name = "event_id")
	private Event event;

	@ManyToOne
	@JoinColumn(name = "venue_id")
	private Venue venue;

	@Column(name = "description", nullable = true)
	private String description;

	public Event_Venue(String description) {
		this.description = description;
	}
}
