package com.qvik.events.modules.stage;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qvik.events.infra.exception.DataNotFoundException;
import com.qvik.events.infra.response.dto.Event_BaseDTO;
import com.qvik.events.infra.response.dto.Stage_DetailsDTO;
import com.qvik.events.modules.event.Event;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class StageService {

	private final StageRepository stageRepository;
	private final ModelMapper modelMapper;

	public Stage findStageByStageId(Long id) {
		return stageRepository.findById(id)
				.orElseThrow(() -> new DataNotFoundException("Stage not found with ID: " + id));
	}

	public List<Stage_DetailsDTO> findAllStages() {
		List<Stage> stages = stageRepository.findAll();
		List<Stage_DetailsDTO> stageDTOs = new ArrayList<>(); // final list

		for (Stage s : stages) {
			// map each stage to DTO
			Stage_DetailsDTO stageDTO = modelMapper.map(s, Stage_DetailsDTO.class);

			// set event information to DTO
			List<Event> events = s.getEvents();
			List<Event_BaseDTO> eventInfoDTOs = new ArrayList<>();
			events.forEach(e -> eventInfoDTOs.add(modelMapper.map(e, Event_BaseDTO.class)));
			stageDTO.setEvents(eventInfoDTOs);

			// save DTO to the final list
			stageDTOs.add(stageDTO);
		}

		return stageDTOs;
	}

	/** OLD CALL - ONLY LINKED STAGE
	public List<Stage_DetailsDTO> findAllStages() {
		List<Stage> stages = stageRepository.findAll();
		List<Stage_DetailsDTO> stageDTOs = new ArrayList<>(); // final list

		for (Stage s : stages) {
			// map each stage to DTO
			Stage_DetailsDTO stageDTO = modelMapper.map(s, Stage_DetailsDTO.class);

			// set event information to DTO
			List<Event> events = s.getEvents();
			if (events.size() != 0) {  // exclude stage which has no events
				List<Event_BaseDTO> eventInfoDTOs = new ArrayList<>();
				events.forEach(e -> eventInfoDTOs.add(modelMapper.map(e, Event_BaseDTO.class)));
				stageDTO.setEvents(eventInfoDTOs);

				// save DTO to the final list
				stageDTOs.add(stageDTO);
			}
		}

		return stageDTOs;
	}
	 */
}
