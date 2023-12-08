package com.cn.taskmanagement.service.usecase;

import com.cn.taskmanagement.dto.SortingAndPaginationParams;
import com.cn.taskmanagement.dto.TaskDto;
import com.cn.taskmanagement.mapper.TaskMapper;
import com.cn.taskmanagement.model.Task;
import com.cn.taskmanagement.repository.TaskRepository;
import com.cn.taskmanagement.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private final TaskRepository taskRepository;


    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<TaskDto> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(TaskMapper.INSTANCE::taskToTaskDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TaskDto> getTaskById(UUID taskId) {
        return taskRepository.findById(taskId).map(TaskMapper.INSTANCE::taskToTaskDto);
    }

    @Override
    public List<TaskDto> getTasksByTitle(String title) {
        List<Task> tasks = taskRepository.findByTitle(title);
        return tasks.stream()
                .map(TaskMapper.INSTANCE::taskToTaskDto)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDto createTask(TaskDto taskDto) {
        Task task = TaskMapper.INSTANCE.taskDtoToTask(taskDto);
        Task createdTask = taskRepository.save(task);
        return TaskMapper.INSTANCE.taskToTaskDto(createdTask);
    }

    @Override
    public Optional<TaskDto> updateTask(UUID taskId, TaskDto updatedTaskDto) {
        Optional<Task> existingTaskOptional = taskRepository.findById(taskId);
        if (!existingTaskOptional.isPresent()) {
            // Handle non-existent task case
            return Optional.empty();
        }
        Task existingTask = existingTaskOptional.get();

        if (isValidForUpdateOrDelete(existingTask)) {

            // Update task properties
            updateTaskProperties(existingTask, updatedTaskDto);

            // Save the updated task
            Task savedTask = saveUpdatedTask(existingTask);
            return Optional.ofNullable(TaskMapper.INSTANCE.taskToTaskDto(savedTask));
        } else {
            return Optional.empty(); // Indicate that the update is not valid
        }
    }

    private Task saveUpdatedTask(Task task) {
        return taskRepository.save(task);
    }


    @Override
    public void updateTaskProperties(Task existingTask, TaskDto updatedTaskDto) {
        existingTask.setTitle(updatedTaskDto.getTitle());
        existingTask.setDescription(updatedTaskDto.getDescription());
        existingTask.setStatus(updatedTaskDto.getStatus());
        existingTask.setPriority(updatedTaskDto.getPriority());
        existingTask.setDeadline(updatedTaskDto.getDeadline());
        // Update other properties as needed
    }

    @Override
    public List<TaskDto> getAllTasksWithSortingAndPagination(SortingAndPaginationParams params) {
        List<Task> sortedAndPaginatedTasks = getSortedAndPaginatedTasks(params);
        return mapTasksToDtos(sortedAndPaginatedTasks);
    }

    private List<Task> getSortedAndPaginatedTasks(SortingAndPaginationParams params) {
        String sortBy = params.getSortBy();
        String sortOrder = params.getSortOrder();
        int page = params.getPage();
        int size = params.getSize();

        Sort sort = Sort.by(sortOrder.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        return taskRepository.findAll(pageable).getContent();
    }

    private List<TaskDto> mapTasksToDtos(List<Task> tasks) {
        return tasks.stream()
                .map(TaskMapper.INSTANCE::taskToTaskDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteTask(UUID taskId) {
        Optional<Task> existingTaskOptional = taskRepository.findById(taskId);
        return existingTaskOptional.map(this::isValidForUpdateOrDelete).orElse(false);
    }

    private boolean isValidForUpdateOrDelete(Task task) {
        // Business logic for validation (e.g., pending or in-progress state only then update task)
        return "pending".equals(task.getStatus()) || "in-progress".equals(task.getStatus());
    }

    @Transactional
    public void batchUpdateTaskStatus(List<UUID> taskIds, String newStatus) {
        taskRepository.batchUpdateStatus(taskIds, newStatus);
    }
}
