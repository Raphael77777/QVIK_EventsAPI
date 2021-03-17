package com.qvik.events.modules.presenter;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qvik.events.infra.exception.DataNotFoundException;
import com.qvik.events.infra.response.dto.Event_BaseDTO;
import com.qvik.events.infra.response.dto.Presenter_DetailsDTO;
import com.qvik.events.infra.response.dto.PresentersDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PresenterService {

	private final PresenterRepository presenterRepository;
	private final ModelMapper modelMapper;

	public Presenter findPresenterByPresenterId(Long id) {
		return presenterRepository.findById(id)
				.orElseThrow(() -> new DataNotFoundException("Presenter not found with ID: " + id));
	}

	public List<Presenter_DetailsDTO> findAllPresenters() {
		List<Presenter> presenters = presenterRepository.findAll();
		List<Presenter_DetailsDTO> presenterDTOs = new ArrayList<>(); // final list
		//presenters.forEach(p -> presenterDTOs.add(modelMapper.map(p, Presenter_DetailsDTO.class)));

		for (Presenter p : presenters) {
			// map each presenter to DTO
			Presenter_DetailsDTO presenterDTO = modelMapper.map(p, Presenter_DetailsDTO.class);

			// set event information to DTO
			List<Event_Presenter> eventPresenters = p.getEvent_presenters();
			List<Event_BaseDTO> eventInfoDTOs = new ArrayList<>();
			eventPresenters.forEach(ep -> eventInfoDTOs.add(modelMapper.map(ep.getEvent(), Event_BaseDTO.class)));
			presenterDTO.setEvents(eventInfoDTOs);

			// save DTO to the final list
			presenterDTOs.add(presenterDTO);
		}

		return presenterDTOs;
	}
}
