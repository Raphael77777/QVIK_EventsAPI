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

/** Entity for table Exhibitor */
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "exhibitor_id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "exhibitor")
public class Exhibitor {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "exhibitor_id", nullable = false, updatable = false)
	private long exhibitor_id;

	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "location", nullable = false)
	private String location;
	@Column(name = "contact", nullable = false)
	private String contact;
	@Column(name = "short_description", nullable = false)
	private String short_description;
	@Column(name = "full_description", nullable = false)
	private String full_description;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "exhibitor")
	@JsonBackReference
	private List<Event_Exhibitor> event_exhibitors;

	public Exhibitor(String name, String location, String contact, String short_description, String full_description) {
		this.name = name;
		this.location = location;
		this.contact = contact;
		this.short_description = short_description;
		this.full_description = full_description;
	}

}
