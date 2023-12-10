package com.cn.taskmanagement.controller;

import com.cn.taskmanagement.dto.TaskDto;
import com.cn.taskmanagement.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TaskControllerTest {

    private static final String TASKS_ENDPOINT = "/api/tasks";

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private TaskDto sampleTaskDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
        objectMapper = new ObjectMapper();
        sampleTaskDto = createSampleTaskDto();
    }

    private TaskDto createSampleTaskDto() {
        TaskDto taskDto = new TaskDto("Sample Task","Sample description","IN_PROGRESS","HIGH",LocalDateTime.of(2023, 12, 31, 23, 59));
        return taskDto;
    }

    @Test
    void getAllTasks() throws Exception {
        List<TaskDto> tasks = Collections.singletonList(sampleTaskDto);

        when(taskService.getAllTasks()).thenReturn(tasks);

        mockMvc.perform(get(TASKS_ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(tasks)));

        verify(taskService, times(1)).getAllTasks();
    }

    @Test
    void getAllTasksWithSortingAndPagination() throws Exception {
        List<TaskDto> tasks = Collections.singletonList(sampleTaskDto);

        when(taskService.getAllTasksWithSortingAndPagination(any())).thenReturn(tasks);

        mockMvc.perform(get(TASKS_ENDPOINT + "/all")
                        .param("sortBy", "priority")
                        .param("sortOrder", "ASC")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(tasks)));

        verify(taskService, times(1)).getAllTasksWithSortingAndPagination(any());
    }

    @Test
    void getTaskById() throws Exception {
        UUID taskId = sampleTaskDto.getId();

        when(taskService.getTaskById(taskId)).thenReturn(Optional.of(sampleTaskDto));

        mockMvc.perform(get(TASKS_ENDPOINT + "/{id}", taskId))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(sampleTaskDto)));

        verify(taskService, times(1)).getTaskById(taskId);
    }

    @Test
    void getTaskById_NotFound() throws Exception {
        UUID taskId = UUID.randomUUID();

        when(taskService.getTaskById(taskId)).thenReturn(Optional.empty());

        mockMvc.perform(get(TASKS_ENDPOINT + "/{id}", taskId))
                .andExpect(status().isNotFound());

        verify(taskService, times(1)).getTaskById(taskId);
    }

    @Test
    void createTask() throws Exception {
        when(taskService.createTask(any())).thenReturn(sampleTaskDto);

        mockMvc.perform(post(TASKS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleTaskDto)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(sampleTaskDto)));

        verify(taskService, times(1)).createTask(any());
    }

    @Test
    void updateTask() throws Exception {
        UUID taskId = sampleTaskDto.getId();

        when(taskService.updateTask(eq(taskId), any())).thenReturn(Optional.of(sampleTaskDto));

        mockMvc.perform(put(TASKS_ENDPOINT + "/{id}", taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleTaskDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(sampleTaskDto)));

        verify(taskService, times(1)).updateTask(eq(taskId), any());
    }


    @Test
    void updateTask_NotFound() throws Exception {
        UUID taskId = UUID.randomUUID();

        when(taskService.updateTask(eq(taskId), any())).thenReturn(Optional.empty());

        mockMvc.perform(put(TASKS_ENDPOINT + "/{id}", taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleTaskDto)))
                .andExpect(status().isNotFound());

        verify(taskService, times(1)).updateTask(eq(taskId), any());
    }

    @Test
    void deleteTask() throws Exception {
        UUID taskId = sampleTaskDto.getId();

        when(taskService.deleteTask(taskId)).thenReturn(true);

        mockMvc.perform(delete(TASKS_ENDPOINT + "/{id}", taskId))
                .andExpect(status().isNoContent());

        verify(taskService, times(1)).deleteTask(taskId);
    }

    @Test
    void deleteTask_NotFound() throws Exception {
        UUID taskId = UUID.randomUUID();

        when(taskService.deleteTask(taskId)).thenReturn(false);

        mockMvc.perform(delete(TASKS_ENDPOINT + "/{id}", taskId))
                .andExpect(status().isNotFound());

        verify(taskService, times(1)).deleteTask(taskId);
    }

    @Test
    void batchUpdateTaskStatus() throws Exception {
        List<UUID> taskIds = Collections.singletonList(sampleTaskDto.getId());
        String newStatus = "COMPLETED";

        mockMvc.perform(put(TASKS_ENDPOINT + "/batchUpdate")
                        .param("taskIds", taskIds.get(0).toString())
                        .param("newStatus", newStatus))
                .andExpect(status().isOk());

        verify(taskService, times(1)).batchUpdateTaskStatus(taskIds, newStatus);
    }
}