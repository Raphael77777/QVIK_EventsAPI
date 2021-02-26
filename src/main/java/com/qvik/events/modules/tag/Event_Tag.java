package com.qvik.events.modules.tag;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

/* Entity for table Event_Tag */
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "event_tag_id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "event_tag")
public class Event_Tag {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "event_tag_id", nullable = false, updatable = false)
	private long event_tag_id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "event_id")
	private Event event;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tag_id")
	private Tag tag;

	// TODO : Temporary output to be removed at the end of development
	@Override
	public String toString() {
		return "Event_Tag{" +
				"event_tag_id=" + event_tag_id +
				", event=" + event +
				", tag=" + tag +
				'}';
	}
}
