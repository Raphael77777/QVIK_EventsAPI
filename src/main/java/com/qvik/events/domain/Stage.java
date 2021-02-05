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

/** Entity for table Stage */
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "stage_id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stage")
public class Stage {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "stage_id", nullable = false, updatable = false)
	private long stage_id;

	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "location", nullable = false)
	private String location;
	@Column(name = "capacity", nullable = false)
	private Integer capacity;
	@Column(name = "type", nullable = false)
	private String type;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "venue_id")
	private Venue venue;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "stage")
	@JsonBackReference
	private List<Event_Stage> event_stages;

	public Stage(String name, String location, Integer capacity, String type) {
		this.name = name;
		this.location = location;
		this.capacity = capacity;
		this.type = type;
	}
}
