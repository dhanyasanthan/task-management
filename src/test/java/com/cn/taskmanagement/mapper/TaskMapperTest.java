package com.cn.taskmanagement.mapper;

import com.cn.taskmanagement.dto.TaskDto;
import com.cn.taskmanagement.model.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TaskMapperTest {

    @Test
    public void testTaskToTaskDto() {
        // Arrange
        Task task = new Task("Task 1", "Description 1", "TODO", "High", LocalDateTime.now());

        // Act
        TaskDto taskDto = TaskMapper.INSTANCE.taskToTaskDto(task);

        // Assert
        assertEquals("Task 1", taskDto.getTitle());
        assertEquals("Description 1", taskDto.getDescription());
        assertEquals("TODO", taskDto.getStatus());
        assertEquals("High", taskDto.getPriority());
        // Add more assertions for other fields
    }

    @Test
    public void testTaskDtoToTask() {
        // Arrange
        TaskDto taskDto = new TaskDto("Task 1", "Description 1", "TODO", "High", LocalDateTime.now());

        // Act
        Task task = TaskMapper.INSTANCE.taskDtoToTask(taskDto);

        // Assert
        assertEquals("Task 1", task.getTitle());
        assertEquals("Description 1", task.getDescription());
        assertEquals("TODO", task.getStatus());
        assertEquals("High", task.getPriority());
        // Add more assertions for other fields
    }

    // Add more test cases for different scenarios as needed
}