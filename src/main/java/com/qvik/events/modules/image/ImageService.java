package com.qvik.events.modules.image;

import com.qvik.events.infra.exception.DataNotFoundException;
import com.qvik.events.infra.response.dto.ImageDTO;
import com.qvik.events.infra.response.dto.Venue_DetailsDTO;
import com.qvik.events.modules.venue.Venue;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class ImageService {

	private final ImageRepository imageRepository;
	private final ModelMapper modelMapper;

	public Resource findImageByImageId(Long id) {
		byte[] image = imageRepository.findById(id)
				.orElseThrow(() -> new DataNotFoundException("Image not found with ID: " + id))
				.getContent();

		if (image == null){
			throw new DataNotFoundException("Content not found with ID: " + id);
		}

		return new ByteArrayResource(image);
	}

	public Long uploadImage(MultipartFile file) throws IOException {
		Image image = new Image();
		image.setName(file.getName());
		image.setContent(file.getBytes());

		return imageRepository.save(image).getImageId();
	}

	public List<ImageDTO> findAllImages() {
	 	List<Image> images = imageRepository.findAll();
	 	List<ImageDTO> imagesDTO = new ArrayList<>();

		for (Image i : images) {
			ImageDTO dto = modelMapper.map(i, ImageDTO.class);
			imagesDTO.add(dto);
		}

		return imagesDTO;
	}
}
