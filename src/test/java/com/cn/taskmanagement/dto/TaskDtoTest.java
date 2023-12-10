package com.cn.taskmanagement.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class TaskDtoTest {

    @Test
    public void testEqualsAndHashCode() {
        // Arrange
        UUID id = UUID.randomUUID();
        String title = "Task Title";
        String description = "Task Description";
        String status = "In Progress";
        String priority = "High";
        LocalDateTime deadline = LocalDateTime.of(2023, 12, 31, 23, 59);

        TaskDto taskDto1 = new TaskDto(title, description, status, priority, deadline);
        taskDto1.setId(id);

        TaskDto taskDto2 = new TaskDto(title, description, status, priority, deadline);
        taskDto2.setId(id);

        // Assert
        assertEquals(taskDto1, taskDto2);
        assertEquals(taskDto1.hashCode(), taskDto2.hashCode());
    }

    @Test
    public void testGettersAndSetters() {
        // Arrange
        UUID id = UUID.randomUUID();
        String title = "Task Title";
        String description = "Task Description";
        String status = "In Progress";
        String priority = "High";
        LocalDateTime deadline = LocalDateTime.of(2023, 12, 31, 23, 59);
        ProjectDto projectDto = new ProjectDto(UUID.randomUUID(), "Project Name");

        TaskDto taskDto = new TaskDto(title,description,status,priority,deadline);

        // Assert
        assertEquals(id, taskDto.getId());
        assertEquals(title, taskDto.getTitle());
        assertEquals(description, taskDto.getDescription());
        assertEquals(status, taskDto.getStatus());
        assertEquals(priority, taskDto.getPriority());
        assertEquals(deadline, taskDto.getDeadline());
        assertEquals(projectDto, taskDto.getProject());
    }

    @Test
    public void testEqualsWithDifferentId() {
        // Arrange
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        String title = "Task Title";
        String description = "Task Description";
        String status = "In Progress";
        String priority = "High";
        LocalDateTime deadline = LocalDateTime.of(2023, 12, 31, 23, 59);

        TaskDto taskDto1 = new TaskDto(title, description, status, priority, deadline);
        taskDto1.setId(id1);

        TaskDto taskDto2 = new TaskDto(title, description, status, priority, deadline);
        taskDto2.setId(id2);

        // Assert
        assertNotEquals(taskDto1, taskDto2);
    }

    @Test
    public void testEqualsWithNullId() {
        // Arrange
        String title = "Task Title";
        String description = "Task Description";
        String status = "In Progress";
        String priority = "High";
        LocalDateTime deadline = LocalDateTime.of(2023, 12, 31, 23, 59);

        TaskDto taskDto1 = new TaskDto(title, description, status, priority, deadline);
        taskDto1.setId(null);

        TaskDto taskDto2 = new TaskDto(title, description, status, priority, deadline);
        taskDto2.setId(null);

        // Assert
        assertEquals(taskDto1, taskDto2);
    }

    // Add more test cases as needed
}
