package com.cn.taskmanagement.service.usecase;

import com.cn.taskmanagement.dto.SortingAndPaginationParams;
import com.cn.taskmanagement.dto.TaskDto;
import com.cn.taskmanagement.mapper.TaskMapper;
import com.cn.taskmanagement.model.Task;
import com.cn.taskmanagement.repository.ProjectRepository;
import com.cn.taskmanagement.repository.TaskRepository;
import com.cn.taskmanagement.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
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

    public TaskDto createTask(TaskDto taskDto) {
        // Validate if the project with the given projectId exists
        validateProjectExistence(taskDto.getProjectId());

        Task newTask = TaskMapper.INSTANCE.taskDtoToTask(taskDto);
        Task savedTask = taskRepository.save(newTask);
        return TaskMapper.INSTANCE.taskToTaskDto(savedTask);
    }

    private void validateProjectExistence(UUID projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new EntityNotFoundException("Project not found with id: " + projectId);
        }
    }

    @Override
    public Optional<TaskDto> updateTask(UUID taskId, TaskDto updatedTaskDto) {
        Optional<Task> existingTaskOptional = getExistingTask(taskId);
        if (existingTaskOptional.isEmpty() || !isValidForUpdateOrDelete(existingTaskOptional.get())) {
            return Optional.empty();
        }

        try {
            Task updatedTask = updateTaskProperties(existingTaskOptional.get(), updatedTaskDto);
            if (updateTaskInRepository(updatedTask)) {
                return Optional.ofNullable(TaskMapper.INSTANCE.taskToTaskDto(updatedTask));
            } else {
                return Optional.empty(); // Indicate that the update is not valid
            }
        } catch (Exception e) {
            return Optional.empty(); // Indicate that the update is not valid
        }
    }

    private Optional<Task> getExistingTask(UUID taskId) {
        return taskRepository.findById(taskId);
    }


    private boolean updateTaskInRepository(Task task) {
        // Additional validation or business logic before updating in the repository
        Task savedTask = taskRepository.save(task);
        return true; // Return true if the update was successful
    }

    @Override
    public Task updateTaskProperties(Task existingTask, TaskDto updatedTaskDto) {
        // Use a mapper to update the task properties
        return TaskMapper.INSTANCE.updateTaskFromDto(existingTask, updatedTaskDto);
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
