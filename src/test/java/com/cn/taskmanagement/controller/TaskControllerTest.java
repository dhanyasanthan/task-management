package com.cn.taskmanagement.controller;

import com.cn.taskmanagement.model.Task;
import com.cn.taskmanagement.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllTasks_ReturnsListOfTasks() {
        // Arrange
        Task task1 = new Task("Task 2", "Description 2", "ToDo", "Medium", LocalDateTime.now());
        Task task2 = new Task("Task 2", "Description 2", "ToDo", "Medium", LocalDateTime.now());
        List<Task> mockTasks = Arrays.asList(task1, task2);

        when(taskService.getAllTasks()).thenReturn(mockTasks);

        // Act
        List<Task> result = taskController.getAllTasks();

        // Assert
        assertEquals(mockTasks, result);
    }

}