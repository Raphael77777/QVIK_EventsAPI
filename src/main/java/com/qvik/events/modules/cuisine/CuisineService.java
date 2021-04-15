package com.qvik.events.modules.cuisine;

import com.qvik.events.infra.exception.DataNotFoundException;
import com.qvik.events.infra.response.dto.CuisineDTO;
import com.qvik.events.infra.response.dto.TagDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CuisineService {

	private final CuisineRepository cuisineRepository;
	private final ModelMapper modelMapper;

	public Cuisine findCuisineByCuisineId(Long id) {
		return cuisineRepository.findById(id)
				.orElseThrow(() -> new DataNotFoundException("Cuisine not found with ID: " + id));
	}

	public List<CuisineDTO> findAllCuisines() {
		List<Cuisine> cuisines = cuisineRepository.findAll();
		List<CuisineDTO> cuisinesDTOS = new ArrayList<>();

		for (Cuisine c : cuisines) {
			// map each cuisine to DTO
			CuisineDTO cuisineDTO = modelMapper.map(c, CuisineDTO.class);
			cuisinesDTOS.add(cuisineDTO);
		}

		return cuisinesDTOS;
	}
}
