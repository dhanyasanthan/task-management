package com.cn.taskmanagement.service;

import com.cn.taskmanagement.dto.SortingAndPaginationParamsDto;
import com.cn.taskmanagement.dto.TaskDto;
import com.cn.taskmanagement.model.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface TaskService {
    List<TaskDto> getAllTasks();

    Optional<TaskDto> getTaskById(UUID taskId);

    List<TaskDto> getTasksByTitle(String title);

    TaskDto createTask(TaskDto taskDto);

    Optional<TaskDto> updateTask(UUID taskId, TaskDto updatedTaskDto);

    boolean deleteTask(UUID taskId);

    void batchUpdateTaskStatus(List<UUID> taskIds, String newStatus);

    Task updateTaskProperties(Task existingTask, TaskDto updatedTaskDto);

    List<TaskDto> getAllTasksWithSortingAndPagination(SortingAndPaginationParamsDto params);

}
