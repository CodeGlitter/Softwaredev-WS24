package com.example.city_feedback;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CityFeedbackApplicationTests {

	@Test
	void contextLoads() {
		// Tests if Spring Boot application starts successfully
	}

	@Test
	void complaintShouldNotHaveEmptyTitle() {
		// Create a complaint
		Complaint complaint = new Complaint();

		// Set empty title
		complaint.setTitle("");

		// Test should fail for empty title
		assertFalse(complaint.getTitle().isEmpty(), "Complaint title should not be empty");
	}

	@Test
	void complaintShouldNotHaveEmptyDescription() {
		// Create a complaint
		Complaint complaint = new Complaint();

		// Set empty description
		complaint.setDescription("");

		// Test should fail for empty description
		assertFalse(complaint.getDescription().isEmpty(), "Complaint description should not be empty");
	}
}