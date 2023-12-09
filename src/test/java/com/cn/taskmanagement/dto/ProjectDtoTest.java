package com.cn.taskmanagement.dto;

import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

public class ProjectDtoTest {

    @Test
    public void testEqualsAndHashCode() {
        // Arrange
        UUID id = UUID.randomUUID();
        String name = "Project Name";

        ProjectDto projectDto1 = new ProjectDto(id, name);
        ProjectDto projectDto2 = new ProjectDto(id, name);

        // Assert
        assertEquals(projectDto1, projectDto2);
        assertEquals(projectDto1.hashCode(), projectDto2.hashCode());
    }

    @Test
    public void testGettersAndSetters() {
        // Arrange
        UUID id = UUID.randomUUID();
        String name = "Project Name";

        ProjectDto projectDto = new ProjectDto();
        projectDto.setId(id);
        projectDto.setName(name);

        // Assert
        assertEquals(id, projectDto.getId());
        assertEquals(name, projectDto.getName());
    }

    @Test
    public void testEqualsWithDifferentId() {
        // Arrange
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        String name = "Project Name";

        ProjectDto projectDto1 = new ProjectDto(id1, name);
        ProjectDto projectDto2 = new ProjectDto(id2, name);

        // Assert
        assertNotEquals(projectDto1, projectDto2);
    }

    @Test
    public void testEqualsWithNullId() {
        // Arrange
        String name = "Project Name";

        ProjectDto projectDto1 = new ProjectDto(null, name);
        ProjectDto projectDto2 = new ProjectDto(null, name);

        // Assert
        assertEquals(projectDto1, projectDto2);
    }

    // Add more test cases as needed
}
