package com.qvik.events.modules.restaurant;

import org.springframework.stereotype.Service;

import com.qvik.events.infra.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestaurantService {

	private final RestaurantRepository restaurantRepository;
	
	public Restaurant findRestaurantByRestaurantId(Long id) {
		return restaurantRepository.findById(id).orElseThrow(() -> new DataNotFoundException());
	}
}
