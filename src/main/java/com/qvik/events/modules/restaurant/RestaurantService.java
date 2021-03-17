package com.qvik.events.modules.restaurant;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qvik.events.infra.exception.DataNotFoundException;
import com.qvik.events.infra.response.Restaurant_DetailsDTO;
import com.qvik.events.modules.tag.Restaurant_Tag;

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
		List<String> allTags = new ArrayList<>(); // for all Tags for header

		// Find All Restaurants from the DB
		List<Restaurant> restaurants = restaurantRepository.findAll();
		// Iterate All Restaurants list
		for (Restaurant r : restaurants) {
			Restaurant_DetailsDTO dto = modelMapper.map(r, Restaurant_DetailsDTO.class);
			r.getRestaurantTags().forEach(rT -> allTags.add(rT.getTag().getName())); // add tag for header
			addRestaurarntTags(r, dto); // add tag for the list of restaurants

			restaurantDTOList.add(dto);
		}

		finalAllRestaurants.put("allTags", removeDuplicates(allTags)); // all tags for header
		finalAllRestaurants.put("restaurants", restaurantDTOList); // list of restaurants

		return finalAllRestaurants;
	}

	public Restaurant_DetailsDTO findRestaurantByRestaurantId(Long id) {

		Restaurant resturant = restaurantRepository.findRestaurantByRestaurantId(id);
		if (resturant == null) {
			new DataNotFoundException("Restaurant not found with ID: " + id);
		}

		Restaurant_DetailsDTO restaurantDTO = modelMapper.map(resturant, Restaurant_DetailsDTO.class);
		addRestaurarntTags(resturant, restaurantDTO);
		return restaurantDTO;
	}

	// Method to add tag name to json data
	private void addRestaurarntTags(Restaurant r, Restaurant_DetailsDTO rDto) {
		List<Restaurant_Tag> rTags = r.getRestaurantTags();
		rTags.forEach(t -> rDto.getAllTags().add(t.getTag().getName()));
	}

	// Method to remove duplicate from allTag list
	private List<String> removeDuplicates(List<String> list) {
		Set<String> set = new LinkedHashSet<>(list);
		list.clear();
		list.addAll(set);
		return list;
	}

	public void findAllTags() {
		// TODO Auto-generated method stub
		
	}

}
