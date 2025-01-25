package com.example.city_feedback.authentication.domain;

import com.example.city_feedback.authentication.domain.models.Role;
import com.example.city_feedback.authentication.domain.models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link User} entity.
 * This class ensures that the behavior of the {@link User} class meets expectations,
 * including attribute management and relationships.
 */
public class UserTest {

    /**
     * Test to verify that User attributes can be correctly set and retrieved.
     */
    @Test
    @DisplayName("Should correctly set and get User attributes")
    public void user_SetAndGetAttributes_ShouldWorkCorrectly() {
        // Arrange
        User user = new User();

        // Act
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPhone("1234567890");
        user.setPassword("password123");

        // Assert
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals("1234567890", user.getPhone());
        assertEquals("password123", user.getPassword());
    }

    /**
     * Test to verify that roles in a many-to-many relationship are managed correctly.
     */
    @Test
    @DisplayName("Should correctly manage roles in the many-to-many relationship")
    public void user_RolesManagement_ShouldWorkCorrectly() {
        // Arrange
        User user = new User();
        Role role1 = new Role("Admin");
        Role role2 = new Role("User");

        // Act
        user.setRoles(List.of(role1, role2));

        // Assert
        assertNotNull(user.getRoles());
        assertEquals(2, user.getRoles().size());
        assertTrue(user.getRoles().contains(role1));
        assertTrue(user.getRoles().contains(role2));
    }

    /**
     * Test to verify that a newly created User initializes roles as an empty list.
     */
    @Test
    @DisplayName("Should initialize roles as an empty list")
    public void user_InitialRoles_ShouldBeEmpty() {
        // Arrange
        User user = new User();

        // Act
        if (user.getRoles() == null) {
            user.setRoles(new ArrayList<>());
        }

        // Assert
        assertNotNull(user.getRoles(), "Roles should not be null.");
        assertTrue(user.getRoles().isEmpty(), "Roles should be empty initially.");
    }

    /**
     * Test to verify that the User entity handles null roles gracefully.
     */
    @Test
    @DisplayName("Should handle null roles gracefully")
    public void user_SetNullRoles_ShouldHandleGracefully() {
        // Arrange
        User user = new User();

        // Act
        user.setRoles(null);

        // Assert
        assertNull(user.getRoles(), "Roles should be null when explicitly set to null.");
    }

    /**
     * Test to verify the behavior when setting duplicate roles.
     */
    @Test
    @DisplayName("Should allow duplicate roles in the roles collection")
    public void user_SetDuplicateRoles_ShouldAllowDuplicates() {
        // Arrange
        User user = new User();
        Role role = new Role("Admin");

        // Act
        user.setRoles(List.of(role, role));

        // Assert
        assertNotNull(user.getRoles(), "Roles should not be null.");
        assertEquals(2, user.getRoles().size(), "Roles collection should allow duplicates.");
    }

    /**
     * Test to verify setting a very large number of roles.
     */
    @Test
    @DisplayName("Should handle a large number of roles")
    public void user_SetLargeNumberOfRoles_ShouldHandleCorrectly() {
        // Arrange
        User user = new User();
        List<Role> largeRoleList = Collections.nCopies(1000, new Role("User"));

        // Act
        user.setRoles(largeRoleList);

        // Assert
        assertNotNull(user.getRoles(), "Roles should not be null.");
        assertEquals(1000, user.getRoles().size(), "Roles collection should handle large lists correctly.");
    }

    /**
     * Test to verify behavior when setting roles with null values.
     */
    @Test
    @DisplayName("Should handle null values within roles collection")
    public void user_SetRolesWithNullValues_ShouldHandleGracefully() {
        // Arrange
        User user = new User();
        Role role = new Role("Admin");

        // Act
        List<Role> rolesWithNull = new ArrayList<>();
        rolesWithNull.add(role);
        rolesWithNull.add(null);
        user.setRoles(rolesWithNull);

        // Assert
        assertNotNull(user.getRoles(), "Roles should not be null.");
        assertTrue(user.getRoles().contains(role), "Roles should contain the non-null role.");
        assertTrue(user.getRoles().contains(null), "Roles should allow null values.");
    }
}
