package com.qvik.events.modules.cuisine;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/** Entity for table Cuisine */
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "cuisineId")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cuisine")
public class Cuisine {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "cuisine_id", nullable = false, updatable = false)
	private long cuisineId;

	@Column(name = "name", nullable = false)
	private String name;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cuisine")
	@JsonBackReference
	@JsonIgnore
	private List<Restaurant_Cuisine> restaurant_cuisines;

	public Cuisine(String name) {
		this.name = name;
	}

	// TODO : Temporary output to be removed at the end of development
	@Override
	public String toString() {
		return "Cuisine{" +
				"cuisine_id=" + cuisineId +
				", name='" + name + '\'' +
				'}';
	}
}
