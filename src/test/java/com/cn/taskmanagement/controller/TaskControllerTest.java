package com.cn.taskmanagement.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cn.taskmanagement.dto.TaskDto;
import com.cn.taskmanagement.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * Unit tests for the TaskController class.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
class TaskControllerTest {

  private static final String TASKS_ENDPOINT = "/api/tasks";

  @Mock
  private TaskService taskService;

  @InjectMocks
  private TaskController taskController;

  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper; // Inject the configured ObjectMapper

  private TaskDto sampleTaskDto;

  @BeforeEach
  void setUp() {
    // Initialize mocks and set up the MockMvc instance
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    objectMapper = new ObjectMapper();
    sampleTaskDto = createSampleTaskDto();
  }

  private TaskDto createSampleTaskDto() {
    // Create and return a sample TaskDto
    TaskDto taskDto = new TaskDto("Sample Task", "Sample description", "IN_PROGRESS", "HIGH",
        LocalDateTime.of(2023, 12, 31, 23, 59));
    return taskDto;
  }

  /**
   * Tests the getAllTasks method of the TaskController class.
   */
  @Test
  void getAllTasks() throws Exception {
    List<TaskDto> tasks = Collections.singletonList(sampleTaskDto);

    // Mock the behavior of the taskService to return a list containing sampleTaskDto
    when(taskService.getAllTasks()).thenReturn(tasks);

    // Perform a GET request to the /api/tasks endpoint and verify the result
    mockMvc.perform(get(TASKS_ENDPOINT))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(tasks)));

    // Verify that the getAllTasks method of taskService is called exactly once
    verify(taskService, times(1)).getAllTasks();
  }

  /**
   * Tests the getAllTasksWithSortingAndPagination method of the TaskController class.
   */
  @Test
  void getAllTasksWithSortingAndPagination() throws Exception {
    List<TaskDto> tasks = Collections.singletonList(sampleTaskDto);

    // Mock the behavior of the taskService to return a list containing sampleTaskDto
    when(taskService.getAllTasksWithSortingAndPagination(any())).thenReturn(tasks);

    // Perform a GET request to the /api/tasks/all endpoint with sorting and pagination parameters
    mockMvc.perform(get(TASKS_ENDPOINT + "/all")
            .param("sortBy", "priority")
            .param("sortOrder", "ASC")
            .param("page", "0")
            .param("size", "10"))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(tasks)));

    // Verify that the getAllTasksWithSortingAndPagination method of taskService is called exactly once
    verify(taskService, times(1)).getAllTasksWithSortingAndPagination(any());
  }

  /**
   * Tests the getTaskById method of the TaskController class for an existing task.
   */
  @Test
  void getTaskById() throws Exception {
    UUID taskId = sampleTaskDto.getId();

    // Mock the behavior of the taskService to return the sampleTaskDto for the given taskId
    when(taskService.getTaskById(taskId)).thenReturn(Optional.of(sampleTaskDto));

    // Perform a GET request to the /api/tasks/{id} endpoint and verify the result
    mockMvc.perform(get(TASKS_ENDPOINT + "/{id}", taskId))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(sampleTaskDto)));

    // Verify that the getTaskById method of taskService is called exactly once with the provided taskId
    verify(taskService, times(1)).getTaskById(taskId);
  }

  /**
   * Tests the getTaskById method of the TaskController class for a nonexistent task.
   */
  @Test
  void getTaskById_NotFound() throws Exception {
    UUID taskId = UUID.randomUUID();

    // Mock the behavior of the taskService to return an empty Optional (nonexistent task)
    when(taskService.getTaskById(taskId)).thenReturn(Optional.empty());

    // Perform a GET request to the /api/tasks/{id} endpoint and expect a 404 Not Found status
    mockMvc.perform(get(TASKS_ENDPOINT + "/{id}", taskId))
        .andExpect(status().isNotFound());

    // Verify that the getTaskById method of taskService is called exactly once with the provided taskId
    verify(taskService, times(1)).getTaskById(taskId);
  }

  /**
   * Tests the createTask method of the TaskController class.
   */
  @Test
  void createTask() throws Exception {
    // Mock the behavior of the taskService to return the sampleTaskDto when creating a new task
    when(taskService.createTask(any())).thenReturn(sampleTaskDto);

    // Perform a POST request to the /api/tasks endpoint with JSON content and verify the result
    mockMvc.perform(post(TASKS_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(sampleTaskDto)))
        .andExpect(status().isCreated())
        .andExpect(content().json(objectMapper.writeValueAsString(sampleTaskDto)));

    // Verify that the createTask method of taskService is called exactly once with any TaskDto as argument
    verify(taskService, times(1)).createTask(any());
  }

  /**
   * Tests the updateTask method of the TaskController class for an existing task.
   */
  @Test
  void updateTask() throws Exception {
    UUID taskId = sampleTaskDto.getId();

    // Mock the behavior of the taskService to return the sampleTaskDto after updating
    when(taskService.updateTask(eq(taskId), any())).thenReturn(Optional.of(sampleTaskDto));

    // Perform a PUT request to the /api/tasks/{id} endpoint with JSON content and verify the result
    mockMvc.perform(put(TASKS_ENDPOINT + "/{id}", taskId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(sampleTaskDto)))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(sampleTaskDto)));

    // Verify that the updateTask method of taskService is called exactly once with the provided taskId and any TaskDto
    verify(taskService, times(1)).updateTask(eq(taskId), any());
  }


  /**
   * Tests the updateTask method of the TaskController class for a nonexistent task.
   */
  @Test
  void updateTask_NotFound() throws Exception {
    UUID taskId = UUID.randomUUID();

    // Mock the behavior of the taskService to return an empty Optional (nonexistent task after update)
    when(taskService.updateTask(eq(taskId), any())).thenReturn(Optional.empty());

    // Perform a PUT request to the /api/tasks/{id} endpoint with JSON content and expect a 404 Not Found status
    mockMvc.perform(put(TASKS_ENDPOINT + "/{id}", taskId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(sampleTaskDto)))
        .andExpect(status().isNotFound());

    // Verify that the updateTask method of taskService is called exactly once with the provided taskId and any TaskDto
    verify(taskService, times(1)).updateTask(eq(taskId), any());
  }

  /**
   * Tests the deleteTask method of the TaskController class for an existing task.
   */
  @Test
  void deleteTask() throws Exception {
    UUID taskId = sampleTaskDto.getId();

    // Mock the behavior of the taskService to return true when deleting an existing task
    when(taskService.deleteTask(taskId)).thenReturn(true);

    // Perform a DELETE request to the /api/tasks/{id} endpoint and expect a 204 No Content status
    mockMvc.perform(delete(TASKS_ENDPOINT + "/{id}", taskId))
        .andExpect(status().isNoContent());

    // Verify that the deleteTask method of taskService is called exactly once with the provided taskId
    verify(taskService, times(1)).deleteTask(taskId);
  }

  /**
   * Tests the deleteTask method of the TaskController class for a nonexistent task.
   */
  @Test
  void deleteTask_NotFound() throws Exception {
    UUID taskId = UUID.randomUUID();

    // Mock the behavior of the taskService to return false when trying to delete a nonexistent task
    when(taskService.deleteTask(taskId)).thenReturn(false);

    // Perform a DELETE request to the /api/tasks/{id} endpoint and expect a 404 Not Found status
    mockMvc.perform(delete(TASKS_ENDPOINT + "/{id}", taskId))
        .andExpect(status().isNotFound());

    // Verify that the deleteTask method of taskService is called exactly once with the provided taskId
    verify(taskService, times(1)).deleteTask(taskId);
  }

  /**
   * Tests the batchUpdateTaskStatus method of the TaskController class.
   */
  @Test
  void batchUpdateTaskStatus() throws Exception {
    List<UUID> taskIds = Collections.singletonList(
        (sampleTaskDto.getId() != null) ? sampleTaskDto.getId() : UUID.randomUUID()
    );
    String newStatus = "COMPLETED";

    // Perform a PUT request to the /api/tasks/batchUpdate endpoint with parameters and expect a 200 OK status
    mockMvc.perform(put(TASKS_ENDPOINT + "/batchUpdate")
            .param("taskIds", taskIds.get(0).toString())
            .param("newStatus", newStatus))
        .andExpect(status().isOk());

    // Verify that the batchUpdateTaskStatus method of taskService is called exactly once with the provided taskIds and newStatus
    verify(taskService, times(1)).batchUpdateTaskStatus(taskIds, newStatus);
  }

}