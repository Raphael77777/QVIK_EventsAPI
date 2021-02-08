package com.qvik.events.modules.exhibitor;

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

/** Entity for table Event_Exhibitor */
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "event_exhibitor_id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "event_exhibitor")
public class Event_Exhibitor {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "event_exhibitor_id", nullable = false, updatable = false)
	private long event_exhibitor_id;

	@ManyToOne
	@JoinColumn(name = "event_id")
	private Event event;

	@ManyToOne
	@JoinColumn(name = "exhibitor_id")
	private Exhibitor exhibitor;

	@Column(name = "description", nullable = true)
	private String description;

	public Event_Exhibitor(String description) {
		this.description = description;
	}

	//TODO : Temporary output to be removed at the end of development
	@Override
	public String toString() {
		return "Event_Exhibitor{" +
				"event_exhibitor_id=" + event_exhibitor_id +
				", event=" + event +
				", exhibitor=" + exhibitor +
				", description='" + description + '\'' +
				'}';
	}
}
