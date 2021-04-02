package com.qvik.events.modules.tag;

import com.qvik.events.infra.exception.DataNotFoundException;
import com.qvik.events.infra.response.dto.Event_BaseDTO;
import com.qvik.events.infra.response.dto.Stage_DetailsDTO;
import com.qvik.events.infra.response.dto.TagDTO;
import com.qvik.events.modules.event.Event;
import com.qvik.events.modules.stage.Stage;
import com.qvik.events.modules.stage.StageRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TagService {

	private final TagRepository tagRepository;
	private final ModelMapper modelMapper;

	public Tag findTagByTagId(Long id) {
		return tagRepository.findById(id)
				.orElseThrow(() -> new DataNotFoundException("Tag not found with ID: " + id));
	}

	public List<TagDTO> findAllTags() {
		List<Tag> tags = tagRepository.findAll();
		List<TagDTO> tagDTOS = new ArrayList<>();

		for (Tag t : tags) {
			// map each tag to DTO
			TagDTO tagDTO = modelMapper.map(t, TagDTO.class);
			tagDTOS.add(tagDTO);
		}

		return tagDTOS;
	}
}
