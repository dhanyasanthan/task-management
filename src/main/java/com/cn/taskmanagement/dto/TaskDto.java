package com.cn.taskmanagement.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class TaskDto {
    private UUID id;
    private String title;
    private String description;
    private String status;
    private String priority;
    private LocalDateTime deadline;

    // Default constructor (needed for Jackson deserialization)
    public TaskDto() {
    }

    // Custom constructor
    public TaskDto(String title, String description, String status, String priority, LocalDateTime deadline) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.deadline = deadline;
    }

    // Getters and setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    private ProjectDto project;

    // Getters and setters for the project field

    public ProjectDto getProject() {
        return project;
    }

    public void setProject(ProjectDto project) {
        this.project = project;
    }
}
