package com.qvik.events.modules.stage;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.qvik.events.modules.event.Event;
import com.qvik.events.modules.venue.Venue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Entity for table Stage */
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "stageId")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stage")
public class Stage {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "stage_id", nullable = false, updatable = false)
	private long stageId;

	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "location", nullable = false)
	private String location;
	
	@Column(name = "capacity", nullable = false)
	private Integer capacity;
	
	@Column(name = "type", nullable = false)
	private String type;

	@ManyToOne
	@JoinColumn(name = "venue_id")
	@JsonIgnore
	private Venue venue;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "stage")
	@JsonBackReference
	@JsonIgnore
	private List<Event> events;

	public Stage(String name, String location, Integer capacity, String type) {
		this.name = name;
		this.location = location;
		this.capacity = capacity;
		this.type = type;
	}

	//TODO : Temporary output to be removed at the end of development
	@Override
	public String toString() {
		return "Stage{" +
				"stage_id=" + stageId +
				", name='" + name + '\'' +
				", location='" + location + '\'' +
				", capacity=" + capacity +
				", type='" + type + '\'' +
				", venue=" + venue +
				'}';
	}
}
