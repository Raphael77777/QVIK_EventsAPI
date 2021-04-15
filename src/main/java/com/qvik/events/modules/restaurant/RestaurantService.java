package com.qvik.events.modules.restaurant;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.qvik.events.modules.cuisine.Restaurant_Cuisine;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qvik.events.infra.exception.DataNotFoundException;
import com.qvik.events.infra.response.dto.Restaurant_DetailsDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantService {

	private final RestaurantRepository restaurantRepository;
	private final ModelMapper modelMapper;

	public Map<String, Object> findAllRestaurants() {
		// Variables
		Map<String, Object> finalAllRestaurants = new LinkedHashMap<>();// for return object
		List<Restaurant_DetailsDTO> restaurantDTOList = new ArrayList<>(); // for DTO list
		List<String> allCuisines = new ArrayList<>(); // for all Tags for header

		// Find All Restaurants from the DB
		List<Restaurant> restaurants = restaurantRepository.findAll();
		// Iterate All Restaurants list
		for (Restaurant r : restaurants) {
			Restaurant_DetailsDTO dto = modelMapper.map(r, Restaurant_DetailsDTO.class);
			r.getRestaurantCuisines().forEach(rC -> allCuisines.add(rC.getCuisine().getName())); // add tag for header
			addRestaurantCuisines(r, dto); // add cuisine for the list of restaurants

			restaurantDTOList.add(dto);
		}

		finalAllRestaurants.put("allCuisines", removeDuplicates(allCuisines)); // all cuisines for header
		finalAllRestaurants.put("restaurants", restaurantDTOList); // list of restaurants

		return finalAllRestaurants;
	}

	public Restaurant_DetailsDTO findRestaurantByRestaurantId(Long id) {

		Restaurant resturant = restaurantRepository.findRestaurantByRestaurantId(id);
		if (resturant == null) {
			new DataNotFoundException("Restaurant not found with ID: " + id);
		}

		Restaurant_DetailsDTO restaurantDTO = modelMapper.map(resturant, Restaurant_DetailsDTO.class);
		addRestaurantCuisines(resturant, restaurantDTO);
		return restaurantDTO;
	}

	// Method to add tag name to json data
	private void addRestaurantCuisines(Restaurant r, Restaurant_DetailsDTO rDto) {
		List<Restaurant_Cuisine> rCuisines = r.getRestaurantCuisines();
		rCuisines.forEach(c -> rDto.getAllCuisines().add(c.getCuisine().getName()));
	}

	// Method to remove duplicate from all list
	private List<String> removeDuplicates(List<String> list) {
		Set<String> set = new LinkedHashSet<>(list);
		list.clear();
		list.addAll(set);
		return list;
	}

	public void findAllCuisines() {
		// TODO Auto-generated method stub
	}

}
