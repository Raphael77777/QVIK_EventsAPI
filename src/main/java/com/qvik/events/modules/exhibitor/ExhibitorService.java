package com.qvik.events.modules.exhibitor;

import com.qvik.events.infra.exception.DataNotFoundException;
import com.qvik.events.infra.response.dto.Exhibitor_DetailsDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExhibitorService {
	
	private final ExhibitorRepository exhibitorRepository;
	private final ModelMapper modelMapper;

	@Transactional
	public Exhibitor findExhibitorByExhibitorId(Long id) {
		return exhibitorRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Exhibitor not found with ID: " + id));
	}

	public List<Exhibitor_DetailsDTO> findAllExhibitors() {
		List<Exhibitor> exhibitors = exhibitorRepository.findAll();
		List<Exhibitor_DetailsDTO> exhibitorDTOS = new ArrayList<>();

		for (Exhibitor e : exhibitors) {
			// map each stage to DTO
			Exhibitor_DetailsDTO exhibitorDTO = modelMapper.map(e, Exhibitor_DetailsDTO.class);
			exhibitorDTOS.add(exhibitorDTO);
		}

		return exhibitorDTOS;
	}
}
