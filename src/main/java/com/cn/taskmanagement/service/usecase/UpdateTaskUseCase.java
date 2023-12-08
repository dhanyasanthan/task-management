package com.cn.taskmanagement.service.usecase;

import com.cn.taskmanagement.dto.TaskDto;
import com.cn.taskmanagement.model.Task;
import com.cn.taskmanagement.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UpdateTaskUseCase {
    private final TaskRepository taskRepository;

    @Autowired
    public UpdateTaskUseCase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Optional<Task> updateTaskProperties(UUID taskId, TaskDto updatedTaskDto) {
        return taskRepository.findById(taskId).map(existingTask -> {
            // Update task properties
            existingTask.setTitle(updatedTaskDto.getTitle());
            existingTask.setDescription(updatedTaskDto.getDescription());
            existingTask.setStatus(updatedTaskDto.getStatus());
            existingTask.setPriority(updatedTaskDto.getPriority());
            existingTask.setDeadline(updatedTaskDto.getDeadline());

            // Additional properties can be updated as needed

            return existingTask;
        });
    }
}