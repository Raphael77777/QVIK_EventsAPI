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

import com.qvik.events.modules.restaurant.Restaurant;

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
@EqualsAndHashCode(of = "restaurant_tag_id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "restaurant_tag")
public class Restaurant_Tag {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "restaurant_tag_id", nullable = false, updatable = false)
	private long restaurant_tag_id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "restaurant_id")
	private Restaurant restaurant;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tag_id")
	private Tag tag;

}
