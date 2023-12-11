package com.cn.taskmanagement.service.usecase;

import com.cn.taskmanagement.dto.SortingAndPaginationParamsDto;
import com.cn.taskmanagement.dto.TaskDto;
import com.cn.taskmanagement.model.Task;
import com.cn.taskmanagement.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link TaskServiceImpl} class.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    /**
     * Test for retrieving all tasks.
     */
    @Test
    void getAllTasks() {
        // Arrange
        Task task1 = new Task("Task 1", "Description 1", "TODO", "High", null);
        Task task2 = new Task("Task 2", "Description 2", "In Progress", "Medium", null);

        when(taskRepository.findAll()).thenReturn(Arrays.asList(task1, task2));

        // Act
        List<TaskDto> result = taskService.getAllTasks();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Task 1", result.get(0).getTitle());
        assertEquals("Task 2", result.get(1).getTitle());

        // Verify interactions
        verify(taskRepository, times(1)).findAll();
        verifyNoMoreInteractions(taskRepository);
    }

    /**
     * Test for retrieving a task by its ID when the task exists.
     */
    @Test
    void getTaskById_TaskExists() {
        // Arrange
        UUID taskId = UUID.randomUUID();
        Task task = new Task("Task 1", "Description 1", "TODO", "High", null);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        // Act
        Optional<TaskDto> result = taskService.getTaskById(taskId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Task 1", result.get().getTitle());

        // Verify interactions
        verify(taskRepository, times(1)).findById(taskId);
        verifyNoMoreInteractions(taskRepository);
    }

    /**
     * Test for retrieving a task by its ID when the task does not exist.
     */
    @Test
    void getTaskById_TaskNotExists() {
        // Arrange
        UUID taskId = UUID.randomUUID();

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // Act
        Optional<TaskDto> result = taskService.getTaskById(taskId);

        // Assert
        assertTrue(result.isEmpty());

        // Verify interactions
        verify(taskRepository, times(1)).findById(taskId);
        verifyNoMoreInteractions(taskRepository);
    }

    /**
     * Test for retrieving tasks by title.
     */
    @Test
    void getTasksByTitle() {
        // Arrange
        String title = "Task";
        Task task1 = new Task("Task 1", "Description 1", "TODO", "High", null);
        Task task2 = new Task("Task 2", "Description 2", "In Progress", "Medium", null);

        when(taskRepository.findByTitle(title)).thenReturn(Arrays.asList(task1, task2));

        // Act
        List<TaskDto> result = taskService.getTasksByTitle(title);

        // Assert
        assertEquals(2, result.size());
        assertEquals("Task 1", result.get(0).getTitle());
        assertEquals("Task 2", result.get(1).getTitle());

        // Verify interactions
        verify(taskRepository, times(1)).findByTitle(title);
        verifyNoMoreInteractions(taskRepository);
    }

    /**
     * Test for creating a new task.
     */
    @Test
    void createTask() {
        // Arrange
        TaskDto taskDto = new TaskDto("New Task", "New Description", "TODO", "Medium", null);
        Task createdTask = new Task("New Task", "New Description", "TODO", "Medium", null);

        when(taskRepository.save(Mockito.any(Task.class))).thenReturn(createdTask);

        // Act
        TaskDto result = taskService.createTask(taskDto);

        // Assert
        assertEquals("New Task", result.getTitle());

        // Verify interactions
        verify(taskRepository, times(1)).save(Mockito.any(Task.class));
        verifyNoMoreInteractions(taskRepository);
    }

    /**
     * Test for updating an existing task when the task ID is valid.
     */
    @Test
    void updateTask_ValidTaskId() {
        // Arrange
        UUID taskId = UUID.randomUUID();
        TaskDto updatedTaskDto = new TaskDto("Updated Task", "Updated Description", "In Progress", "Medium", null);
        Task existingTask = new Task("Task 1", "Description 1", "TODO", "High", null);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(Mockito.any(Task.class))).thenReturn(existingTask);

        // Act
        Optional<TaskDto> result = taskService.updateTask(taskId, updatedTaskDto);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Updated Task", result.get().getTitle());

        // Verify interactions
        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, times(1)).save(Mockito.any(Task.class));
        verifyNoMoreInteractions(taskRepository);
    }

    /**
     * Test for updating an existing task when the task ID is invalid.
     */
    @Test
    void updateTask_InvalidTaskId() {
        // Arrange
        UUID taskId = UUID.randomUUID();
        TaskDto updatedTaskDto = new TaskDto("Updated Task", "Updated Description", "In Progress", "Medium", null);

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // Act
        Optional<TaskDto> result = taskService.updateTask(taskId, updatedTaskDto);

        // Assert
        assertTrue(result.isEmpty());

        // Verify interactions
        verify(taskRepository, times(1)).findById(taskId);
        verifyNoMoreInteractions(taskRepository);
    }

    /**
     * Test for deleting an existing task when the task ID is valid.
     */
    @Test
    void deleteTask_ValidTaskId() {
        // Arrange
        UUID taskId = UUID.randomUUID();
        Task existingTask = new Task("Task 1", "Description 1", "TODO", "High", null);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(Mockito.any(Task.class))).thenReturn(existingTask);

        // Act
        boolean result = taskService.deleteTask(taskId);

        // Assert
        assertTrue(result);

        // Verify interactions
        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, times(1)).save(Mockito.any(Task.class));
        verifyNoMoreInteractions(taskRepository);
    }

    /**
     * Test for deleting a task when the task ID is invalid.
     */
    @Test
    void deleteTask_InvalidTaskId() {
        // Arrange
        UUID taskId = UUID.randomUUID();

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // Act
        boolean result = taskService.deleteTask(taskId);

        // Assert
        assertFalse(result);

        // Verify interactions
        verify(taskRepository, times(1)).findById(taskId);
        verifyNoMoreInteractions(taskRepository);
    }

    /**
     * Test for batch updating task status.
     */
    @Test
    void batchUpdateTaskStatus() {
        // Arrange
        List<UUID> taskIds = Arrays.asList(UUID.randomUUID(), UUID.randomUUID());
        String newStatus = "Completed";

        // Act
        taskService.batchUpdateTaskStatus(taskIds, newStatus);

        // Verify interactions
        verify(taskRepository, times(1)).batchUpdateStatus(taskIds, newStatus);
        verifyNoMoreInteractions(taskRepository);
    }

    /**
     * Test for retrieving all tasks with sorting and pagination.
     */
    @Test
    void getAllTasksWithSortingAndPagination() {
        // Arrange
        SortingAndPaginationParamsDto params = new SortingAndPaginationParamsDto("priority", "ASC", 0, 10);
        Task task1 = new Task("Task 1", "Description 1", "TODO", "High", null);
        Task task2 = new Task("Task 2", "Description 2", "In Progress", "Medium", null);

        when(taskRepository.findAll((Sort) Mockito.any())).thenReturn(Arrays.asList(task1, task2));

        // Act
        List<TaskDto> result = taskService.getAllTasksWithSortingAndPagination(params);

        // Assert
        assertEquals(2, result.size());
        assertEquals("Task 1", result.get(0).getTitle());
        assertEquals("Task 2", result.get(1).getTitle());

        // Verify interactions
        verify(taskRepository, times(1)).findAll((Sort) Mockito.any());
        verifyNoMoreInteractions(taskRepository);
    }

    /**
     * Test for retrieving all tasks with sorting and pagination when the result is an empty list.
     */
    @Test
    void getAllTasksWithSortingAndPagination_EmptyList() {
        // Arrange
        SortingAndPaginationParamsDto params = new SortingAndPaginationParamsDto("priority", "ASC", 0, 10);

        when(taskRepository.findAll((Sort) Mockito.any())).thenReturn(Collections.emptyList());

        // Act
        List<TaskDto> result = taskService.getAllTasksWithSortingAndPagination(params);

        // Assert
        assertTrue(result.isEmpty());

        // Verify interactions
        verify(taskRepository, times(1)).findAll((Sort) Mockito.any());
        verifyNoMoreInteractions(taskRepository);
    }

}
