package com.cn.taskmanagement.service;

import com.cn.taskmanagement.model.Task;
import com.cn.taskmanagement.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void getAllTasks() {
        List<Task> tasks = Arrays.asList(
                new Task("Task 1", "Description 1", "TODO", "High", null),
                new Task("Task 2", "Description 2", "IN_PROGRESS", "Medium", null)
        );

        when(taskRepository.findAll()).thenReturn(tasks);

        List<Task> result = taskService.getAllTasks();

        assertEquals(2, result.size());
        assertEquals("Task 1", result.get(0).getTitle());
        assertEquals("Task 2", result.get(1).getTitle());

        verify(taskRepository, times(1)).findAll();
        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    void getTaskById() {
        LocalDateTime deadline = LocalDateTime.of(2023, 12, 31, 23, 59);
        Task task = new Task("Task 1", "Description 1", "TODO", "High", deadline);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Optional<Task> result = taskService.getTaskById(1L);

        // Access the title only if the result is present
        result.ifPresent(taskResult -> assertEquals("Task 1", taskResult.getTitle()));

        verify(taskRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(taskRepository);
    }

    // TODO: Additional test methods for create, update, and delete can be added
}