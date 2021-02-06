package com.qvik.events.domain;

import org.springframework.stereotype.Service;

import com.qvik.events.config.RestaurantNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestaurantService {

	private final RestaurantRepository restaurantRepository;
	
	public Restaurant findRestaurantByRestaurantId(Long id) {
		return restaurantRepository.findById(id).orElseThrow(() -> new RestaurantNotFoundException(id));
	}
}
