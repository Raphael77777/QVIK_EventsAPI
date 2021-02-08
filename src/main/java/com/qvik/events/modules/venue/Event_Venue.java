package com.qvik.events.modules.venue;

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

	//TODO : Temporary output to be removed at the end of development
	@Override
	public String toString() {
		return "Event_Venue{" +
				"event_venue_id=" + event_venue_id +
				", event=" + event +
				", venue=" + venue +
				", description='" + description + '\'' +
				'}';
	}
}
