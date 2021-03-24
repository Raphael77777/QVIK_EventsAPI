package com.qvik.events.web;

import com.qvik.events.infra.response.ResponseMessage;
import com.qvik.events.modules.image.ImageRepository;
import com.qvik.events.modules.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/** Controller used for admin page */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class AdminController {

	private final ImageRepository imageRepository;
	private final ImageService imageService;

	/*	  
	 * Add New Main Event 
	 */
	
	
	/*
	 * Add New Sub Event 
	 */

	/*
	 * Upload Image
	 */
	@PostMapping(path = "/upload-image")
	Long uploadImage(@RequestParam MultipartFile file) throws Exception {
		return imageService.uploadImage(file);
	}

	/*
	 * Convert Data to ResponseMessage.class
	 */
	private ResponseMessage convertToResponseMessage(Object object) {
		ResponseMessage message = new ResponseMessage(HttpStatus.OK);
		message.add(object);
		return message;
	}

	/*
	 * Controller level exception handler
	 */
	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseMessage badRequestErrorHandling(Exception ex) {
		ResponseMessage error = new ResponseMessage(HttpStatus.BAD_REQUEST, ex);
		return error;
	}

}
