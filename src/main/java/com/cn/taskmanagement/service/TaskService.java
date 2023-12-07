package com.cn.taskmanagement.service;

import com.cn.taskmanagement.model.Task;
import com.cn.taskmanagement.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        try {
            return taskRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching all tasks", e);
        }
    }

    public Optional<Task> getTaskById(Long taskId) {
        return taskRepository.findById(taskId);
    }

    public List<Task> getTasksByTitle(String title) {
        return taskRepository.findByTitle(title);
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Optional<Task> updateTask(Long taskId, Task updatedTask) {
        return taskRepository.findById(taskId).map(existingTask -> {
            existingTask.setTitle(updatedTask.getTitle());
            existingTask.setDescription(updatedTask.getDescription());
            existingTask.setStatus(updatedTask.getStatus());
            existingTask.setPriority(updatedTask.getPriority());
            existingTask.setDeadline(updatedTask.getDeadline());
            return taskRepository.save(existingTask);
        });
    }

    public boolean deleteTask(Long taskId) {
        return taskRepository.findById(taskId).map(task -> {
            taskRepository.delete(task);
            return true;
        }).orElse(false);
    }

    public Optional<Task> updateTaskSelective(Long taskId, Map<String, Object> updates) {
        return taskRepository.findById(taskId).map(existingTask -> {
            for (Map.Entry<String, Object> entry : updates.entrySet()) {
                String fieldName = entry.getKey();
                Object newValue = entry.getValue();

                // Update only if the field is not null
                if (newValue != null) {
                    switch (fieldName) {
                        case "title":
                            existingTask.setTitle((String) newValue);
                            break;
                        case "description":
                            existingTask.setDescription((String) newValue);
                            break;
                        case "status":
                            existingTask.setStatus((String) newValue);
                            break;
                        case "priority":
                            existingTask.setPriority((String) newValue);
                            break;
                        case "deadline":
                            existingTask.setDeadline((LocalDateTime) newValue);
                            break;
                    }
                }
            }

            return taskRepository.save(existingTask);
        });
    }

    public List<Task> getOverdueTasks() {
        LocalDateTime now = LocalDateTime.now();
        return getAllTasks().stream()
                .filter(task -> task.getDeadline() != null && task.getDeadline().isBefore(now))
                .collect(Collectors.toList());
    }

}
