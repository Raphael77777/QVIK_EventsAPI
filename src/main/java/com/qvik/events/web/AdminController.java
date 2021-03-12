package com.qvik.events.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.qvik.events.infra.response.ResponseMessage;
import com.qvik.events.modules.event.EventService;
import com.qvik.events.modules.exhibitor.ExhibitorRepository;
import com.qvik.events.modules.exhibitor.ExhibitorService;
import com.qvik.events.modules.presenter.PresenterRepository;
import com.qvik.events.modules.presenter.PresenterService;
import com.qvik.events.modules.restaurant.RestaurantRepository;
import com.qvik.events.modules.restaurant.RestaurantService;
import com.qvik.events.modules.stage.StageRepository;
import com.qvik.events.modules.stage.StageService;
import com.qvik.events.modules.tag.TagRepository;
import com.qvik.events.modules.venue.VenueRepository;
import com.qvik.events.modules.venue.VenueService;

import lombok.RequiredArgsConstructor;

/** Controller used for admin page */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class AdminController {

	/*	  
	 * Add New Main Event 
	 */
	
	
	/*
	 * Add New Sub Event 
	 */
	
		
	
	
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
