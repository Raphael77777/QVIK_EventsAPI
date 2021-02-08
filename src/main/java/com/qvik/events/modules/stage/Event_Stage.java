package com.qvik.events.modules.stage;

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

/** Entity for table Event_Stage */
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "event_stage_id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "event_stage")
public class Event_Stage {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "event_stage_id", nullable = false, updatable = false)
	private long event_stage_id;

	@ManyToOne
	@JoinColumn(name = "event_id")
	private Event event;

	@ManyToOne
	@JoinColumn(name = "stage_id")
	private Stage stage;

	@Column(name = "description", nullable = true)
	private String description;

	public Event_Stage(String description) {
		this.description = description;
	}
}
