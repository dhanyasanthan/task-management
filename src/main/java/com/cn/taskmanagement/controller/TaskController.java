package com.cn.taskmanagement.controller;

import com.cn.taskmanagement.dto.SortingAndPaginationParams;
import com.cn.taskmanagement.dto.TaskDto;
import com.cn.taskmanagement.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskDto> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/all")
    public ResponseEntity<List<TaskDto>> getAllTasksWithSortingAndPagination(
            @RequestParam(defaultValue = "priority") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortOrder,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        SortingAndPaginationParams params = new SortingAndPaginationParams(sortBy, sortOrder, page, size);
        List<TaskDto> tasks = taskService.getAllTasksWithSortingAndPagination(params);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable UUID id) {
        Optional<TaskDto> taskOptional = taskService.getTaskById(id);
        return taskOptional.map(taskDto -> new ResponseEntity<>(taskDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto) {
        TaskDto createdTask = taskService.createTask(taskDto);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable UUID id, @RequestBody TaskDto updatedTaskDto) {
        Optional<TaskDto> updatedTask = taskService.updateTask(id, updatedTaskDto);

        return updatedTask.map(taskDto ->
                        new ResponseEntity<>(taskDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID id) {
        boolean deleted = taskService.deleteTask(id);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/batchUpdate")
    public ResponseEntity<String> batchUpdateTaskStatus(@RequestParam List<UUID> taskIds,
                                                        @RequestParam String newStatus) {
        taskService.batchUpdateTaskStatus(taskIds, newStatus);
        return ResponseEntity.ok("Batch Status Update Successful");
    }
}
