package com.example.city_feedback;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CityFeedbackApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
		// Tests if the application loads
	}

	@Test
	void shouldAccessHomePage() throws Exception {
		// Tests if the home endpoint is reachable
		mockMvc.perform(get("/"))
				.andExpect(status().isOk());
	}

	@Test
	void shouldAccessComplaintsEndpoint() throws Exception {
		// Tests if the complaints endpoint is reachable
		mockMvc.perform(get("/complaints"))
				.andExpect(status().isOk());
	}
}