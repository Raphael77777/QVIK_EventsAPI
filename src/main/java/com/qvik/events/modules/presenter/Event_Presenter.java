package com.qvik.events.modules.presenter;

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

/** Entity for table Event_Presenter */
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "event_presenter_id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "event_presenter")
public class Event_Presenter {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "event_presenter_id", nullable = false, updatable = false)
	private long event_presenter_id;

	@ManyToOne
	@JoinColumn(name = "event_id")
	private Event event;

	@ManyToOne
	@JoinColumn(name = "presenter_id")
	private Presenter presenter;

	@Column(name = "description", nullable = true)
	private String description;

	public Event_Presenter(String description) {
		this.description = description;
	}

}
