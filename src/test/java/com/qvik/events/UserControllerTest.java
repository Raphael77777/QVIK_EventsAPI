package com.qvik.events;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.qvik.events.modules.event.EventRepository;
import com.qvik.events.web.UserController;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

	@Autowired
	MockMvc mockMvc;
	@Autowired
	UserController userController;
	@Autowired
	EventRepository eventRepository;
	
	/*
	 * Information of Sample data 
	 */
	private final LocalDate startDate = LocalDate.now().minusDays(3);	
	private final int totalNumberOfEvents = 2;

	@BeforeEach
	public void beforeEach() {
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}

	@Test
	@DisplayName("All events")
	void allEvents() throws Exception {
		mockMvc.perform(get("/api/v1/events")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("status").exists())
				.andExpect(jsonPath("message").exists()).andExpect(jsonPath("data").exists())
				.andExpect(jsonPath("$.data.length()", equalTo(totalNumberOfEvents))).andDo(print());
	}

	@Test
	@DisplayName("Search by valid ID")
	void eventById_success() throws Exception {
		mockMvc.perform(get("/api/v1/events/11"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("status").exists())
				.andExpect(jsonPath("message").exists())
				.andExpect(jsonPath("data").exists())
				.andDo(print());
	}

	@Test
	@DisplayName("Search by invalid ID")
	void eventById_faio() throws Exception {
		mockMvc.perform(get("/api/v1/events/100")).andExpect(status().is4xxClientError())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("status").exists())
				.andExpect(jsonPath("message").exists()).andExpect(jsonPath("data").doesNotExist()).andDo(print());
	}

	@Test
	@DisplayName("Search by valid start date")
	void eventByStartDate_success() throws Exception{
			
		mockMvc.perform(get("/api/v1/events/date").param("start", startDate.toString()))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("status").exists())
				.andExpect(jsonPath("message").exists())
				.andExpect(jsonPath("data").exists())
				.andDo(print());
	}
	
	@Test
	@DisplayName("Search by invalid start and end dates")
	void eventByDates_success() throws Exception{
		mockMvc.perform(get("/api/v1/events/date")
				.param("start", "2022-02-10")
				.param("end", ""))
				.andExpect(status().is4xxClientError())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("status").exists())
				.andExpect(jsonPath("message").exists())
				.andExpect(jsonPath("data").doesNotExist())
				.andDo(print());
	}
		
}
