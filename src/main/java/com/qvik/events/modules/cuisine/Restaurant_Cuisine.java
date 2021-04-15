package com.qvik.events.modules.cuisine;

import com.qvik.events.modules.restaurant.Restaurant;
import lombok.*;

import javax.persistence.*;

/* Entity for table Restaurant_Cuisine */
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "restaurantCuisineId")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "restaurant_cuisine")
public class Restaurant_Cuisine {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "restaurant_cuisine_id", nullable = false, updatable = false)
	private long restaurantCuisineId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "restaurant_id")
	private Restaurant restaurant;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cuisine_id")
	private Cuisine cuisine;

}
