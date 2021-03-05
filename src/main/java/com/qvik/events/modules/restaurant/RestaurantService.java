package com.qvik.events.modules.restaurant;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qvik.events.infra.exception.DataNotFoundException;
import com.qvik.events.infra.response.Restaurant_DetailsDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantService {

	private final RestaurantRepository restaurantRepository;
	private final ModelMapper modelMapper;

	public List<Restaurant_DetailsDTO> findAllRestaurants() {
		List<Restaurant> restaurants = restaurantRepository.findAll();
		List<Restaurant_DetailsDTO> final_restaurants= new ArrayList<>();
		for(Restaurant r : restaurants) {
			Restaurant_DetailsDTO dto = modelMapper.map(r, Restaurant_DetailsDTO.class);
			final_restaurants.add(dto);
		}
		return final_restaurants;
	}
	
	public Restaurant_DetailsDTO findRestaurantByRestaurantId(Long id) {
		
		Restaurant resturant = restaurantRepository.findRestaurantByRestaurantId(id);
		if(resturant == null) {
			new DataNotFoundException("Restaurant not found with ID: " + id);
		}
		
		Restaurant_DetailsDTO restaurantDTO = modelMapper.map(resturant, Restaurant_DetailsDTO.class);
		return restaurantDTO;
	}


}
