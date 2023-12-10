package com.cn.taskmanagement.service;

import com.cn.taskmanagement.dto.SortingAndPaginationParams;
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

    public Task updateTaskProperties(Task existingTask, TaskDto updatedTaskDto);

    List<TaskDto> getAllTasksWithSortingAndPagination(SortingAndPaginationParams params);

}
