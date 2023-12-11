package com.cn.taskmanagement.service.usecase;

import com.cn.taskmanagement.dto.SortingAndPaginationParamsDto;
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

/**
 * Service implementation for managing Task entities.
 */
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    /**
     * Get a list of all tasks.
     *
     * @return List of all tasks.
     */
    @Override
    public List<TaskDto> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(TaskMapper.INSTANCE::taskToTaskDto)
                .collect(Collectors.toList());
    }

    /**
     * Get a task by its unique identifier.
     *
     * @param taskId The unique identifier of the task.
     * @return Optional containing the task if found, otherwise empty.
     */
    @Override
    public Optional<TaskDto> getTaskById(UUID taskId) {
        return taskRepository.findById(taskId).map(TaskMapper.INSTANCE::taskToTaskDto);
    }

    /**
     * Get tasks by title.
     *
     * @param title The title to search for.
     * @return List of tasks with the specified title.
     */
    @Override
    public List<TaskDto> getTasksByTitle(String title) {
        List<Task> tasks = taskRepository.findByTitle(title);
        return tasks.stream()
                .map(TaskMapper.INSTANCE::taskToTaskDto)
                .collect(Collectors.toList());
    }

    /**
     * Create a new task.
     *
     * @param taskDto The task DTO to create.
     * @return The created task DTO.
     */
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

    /**
     * Update an existing task.
     *
     * @param taskId         The unique identifier of the task to update.
     * @param updatedTaskDto The updated task DTO.
     * @return Optional containing the updated task if found, otherwise empty.
     */
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

    /**
     * Update task properties.
     *
     * @param existingTask    The existing task entity.
     * @param updatedTaskDto  The updated task DTO.
     * @return The updated task entity.
     */
    @Override
    public Task updateTaskProperties(Task existingTask, TaskDto updatedTaskDto) {
        // Use a mapper to update the task properties
        return TaskMapper.INSTANCE.updateTaskFromDto(existingTask, updatedTaskDto);
    }

    /**
     * Get all tasks with sorting and pagination.
     *
     * @param params Sorting and pagination parameters.
     * @return List of tasks with sorting and pagination applied.
     */
    @Override
    public List<TaskDto> getAllTasksWithSortingAndPagination(SortingAndPaginationParamsDto params) {
        List<Task> sortedAndPaginatedTasks = getSortedAndPaginatedTasks(params);
        return mapTasksToDtos(sortedAndPaginatedTasks);
    }

    private List<Task> getSortedAndPaginatedTasks(SortingAndPaginationParamsDto params) {
        Sort sort = buildSort(params.getSortBy(), params.getSortOrder());
        Pageable pageable = buildPageable(params.getPage(), params.getSize(), sort);

        return taskRepository.findAll(pageable).getContent();
    }

    private Sort buildSort(String sortBy, String sortOrder) {
        return Sort.by(sortOrder.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
    }

    private Pageable buildPageable(int page, int size, Sort sort) {
        return PageRequest.of(page, size, sort);
    }

    private List<TaskDto> mapTasksToDtos(List<Task> tasks) {
        return tasks.stream()
                .map(TaskMapper.INSTANCE::taskToTaskDto)
                .collect(Collectors.toList());
    }

    /**
     * Delete a task by its unique identifier.
     *
     * @param taskId The unique identifier of the task to delete.
     * @return True if the task was deleted, false otherwise.
     */
    @Override
    public boolean deleteTask(UUID taskId) {
        Optional<Task> existingTaskOptional = taskRepository.findById(taskId);
        return existingTaskOptional.map(this::isValidForUpdateOrDelete).orElse(false);
    }

    private boolean isValidForUpdateOrDelete(Task task) {
        // Business logic for validation (e.g., pending or in-progress state only then update task)
        return "pending".equals(task.getStatus()) || "in-progress".equals(task.getStatus());
    }

    /**
     * Batch update the status of tasks.
     *
     * @param taskIds   List of task IDs to update.
     * @param newStatus The new status to set for the tasks.
     */
    @Transactional
    public void batchUpdateTaskStatus(List<UUID> taskIds, String newStatus) {
        taskRepository.batchUpdateStatus(taskIds, newStatus);
    }

}
