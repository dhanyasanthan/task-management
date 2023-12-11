package com.cn.taskmanagement.dto;

import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link ProjectDto} class.
 */
public class ProjectDtoTest {

    /**
     * Test for the equals and hashCode methods.
     */
    @Test
    public void testEqualsAndHashCode() {
        // Arrange
        UUID id = UUID.randomUUID();
        String name = "Project Name";

        // Create two ProjectDto objects with the same ID and name
        ProjectDto projectDto1 = new ProjectDto(id, name);
        ProjectDto projectDto2 = new ProjectDto(id, name);

        // Assert
        assertEquals(projectDto1, projectDto2);
        assertEquals(projectDto1.hashCode(), projectDto2.hashCode());

        // Add additional test case:
        // Test equality with the same object
        assertEquals(projectDto1, projectDto1);
        assertEquals(projectDto1.hashCode(), projectDto1.hashCode());
    }


    /**
     * Test for the getters and setters.
     */
    @Test
    public void testGettersAndSetters() {
        // Arrange
        UUID id = UUID.randomUUID();
        String name = "Project Name";

        // Create a ProjectDto object
        ProjectDto projectDto = new ProjectDto();

        // Set ID and name using setters
        projectDto.setId(id);
        projectDto.setName(name);

        // Assert
        assertEquals(id, projectDto.getId());
        assertEquals(name, projectDto.getName());

        // Add additional test case:
        // Test setter for name with a different value
        String newName = "New Project Name";
        projectDto.setName(newName);
        assertEquals(newName, projectDto.getName());
    }

    /**
     * Test for equals with different ID.
     */
    @Test
    public void testEqualsWithDifferentId() {
        // Arrange
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        String name = "Project Name";

        // Create two ProjectDto objects with different IDs and the same name
        ProjectDto projectDto1 = new ProjectDto(id1, name);
        ProjectDto projectDto2 = new ProjectDto(id2, name);

        // Assert
        assertNotEquals(projectDto1, projectDto2);
    }

    /**
     * Test for equals with null ID.
     */
    @Test
    public void testEqualsWithNullId() {
        // Arrange
        String name = "Project Name";

        // Create two ProjectDto objects with null IDs and the same name
        ProjectDto projectDto1 = new ProjectDto(null, name);
        ProjectDto projectDto2 = new ProjectDto(null, name);

        // Assert
        assertEquals(projectDto1, projectDto2);
    }

}
