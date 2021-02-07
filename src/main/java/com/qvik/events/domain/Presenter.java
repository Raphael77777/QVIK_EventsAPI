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

/** Entity for table Presenter */
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "presenter_id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "presenter")
public class Presenter {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "presenter_id", nullable = false, updatable = false)
	private long presenter_id;

	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "contact", nullable = false)
	private String contact;
	
	@Column(name = "short_description", nullable = false)
	private String short_description;
	
	@Lob
	@Basic(fetch = FetchType.EAGER)
	@Column(name = "full_description", nullable = false)
	private String full_description;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "presenter")
	@JsonBackReference
	private List<Event_Presenter> event_presenters;

	public Presenter(String name, String contact, String short_description, String full_description) {
		this.name = name;
		this.contact = contact;
		this.short_description = short_description;
		this.full_description = full_description;
	}

}
