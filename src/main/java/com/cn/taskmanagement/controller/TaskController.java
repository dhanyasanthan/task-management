package com.cn.taskmanagement.controller;

import com.cn.taskmanagement.dto.SortingAndPaginationParamsDto;
import com.cn.taskmanagement.dto.TaskDto;
import com.cn.taskmanagement.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Controller class for handling Task-related HTTP requests.
 */
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Retrieves a list of all tasks.
     *
     * @return List of TaskDto objects.
     */
    @GetMapping
    public List<TaskDto> getAllTasks() {
        return taskService.getAllTasks();
    }

    /**
     * Retrieves a paginated and sorted list of tasks based on provided parameters.
     *
     * @param sortBy    The field to sort by.
     * @param sortOrder The sort order (ASC or DESC).
     * @param page      The page number.
     * @param size      The number of items per page.
     * @return ResponseEntity containing the paginated and sorted list of TaskDto objects.
     */
    @GetMapping("/all")
    public ResponseEntity<List<TaskDto>> getAllTasksWithSortingAndPagination(
            @RequestParam(defaultValue = "priority") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortOrder,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        SortingAndPaginationParamsDto params = new SortingAndPaginationParamsDto(sortBy, sortOrder, page, size);
        List<TaskDto> tasks = taskService.getAllTasksWithSortingAndPagination(params);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    /**
     * Retrieves a task by its ID.
     *
     * @param id The ID of the task to retrieve.
     * @return ResponseEntity containing the TaskDto if found, or NOT_FOUND status if not.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable UUID id) {
        Optional<TaskDto> taskOptional = taskService.getTaskById(id);
        return taskOptional.map(taskDto -> new ResponseEntity<>(taskDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Creates a new task.
     *
     * @param taskDto The TaskDto to be created.
     * @return ResponseEntity containing the created TaskDto and CREATED status.
     */
    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto) {
        TaskDto createdTask = taskService.createTask(taskDto);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    /**
     * Updates an existing task.
     *
     * @param id             The ID of the task to update.
     * @param updatedTaskDto The updated TaskDto data.
     * @return ResponseEntity containing the updated TaskDto if successful, or NOT_FOUND status if the task doesn't exist.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable UUID id, @RequestBody TaskDto updatedTaskDto) {
        Optional<TaskDto> updatedTask = taskService.updateTask(id, updatedTaskDto);

        return updatedTask.map(taskDto ->
                        new ResponseEntity<>(taskDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Deletes a task by its ID.
     *
     * @param id The ID of the task to delete.
     * @return ResponseEntity with NO_CONTENT status if the deletion is successful, or NOT_FOUND status if the task doesn't exist.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID id) {
        boolean deleted = taskService.deleteTask(id);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Batch updates the status of multiple tasks.
     *
     * @param taskIds   The list of task IDs to be updated.
     * @param newStatus The new status to set for the tasks.
     * @return ResponseEntity with OK status upon successful batch update.
     */
    @PutMapping("/batchUpdate")
    public ResponseEntity<String> batchUpdateTaskStatus(@RequestParam List<UUID> taskIds,
                                                        @RequestParam String newStatus) {
        taskService.batchUpdateTaskStatus(taskIds, newStatus);
        return ResponseEntity.ok("Batch Status Update Successful");
    }
}
