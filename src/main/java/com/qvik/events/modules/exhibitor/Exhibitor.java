package com.qvik.events.modules.exhibitor;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Entity for table Exhibitor */
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "exhibitorId")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "exhibitor")
public class Exhibitor {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "exhibitor_id", nullable = false, updatable = false)
	private long exhibitorId;

	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "location", nullable = false)
	private String location;
	
	@Column(name = "contact", nullable = false)
	private String contact;
	
	@Column(name = "short_description", nullable = false)
	private String shortDescription;
	
	@Lob
	@Basic(fetch = FetchType.EAGER)
	@Column(name = "full_description", nullable = false)
	private String fullDescription;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "exhibitor")
	@JsonBackReference
	@JsonIgnore
	private List<Event_Exhibitor> event_exhibitors;

	public Exhibitor(String name, String location, String contact, String short_description, String full_description) {
		this.name = name;
		this.location = location;
		this.contact = contact;
		this.shortDescription = short_description;
		this.fullDescription = full_description;
	}

	//TODO : Temporary output to be removed at the end of development
	@Override
	public String toString() {
		return "Exhibitor{" +
				"exhibitor_id=" + exhibitorId +
				", name='" + name + '\'' +
				", location='" + location + '\'' +
				", contact='" + contact + '\'' +
				", shortDescription='" + shortDescription + '\'' +
				", fullDescription='" + fullDescription + '\'' +
				'}';
	}
}
