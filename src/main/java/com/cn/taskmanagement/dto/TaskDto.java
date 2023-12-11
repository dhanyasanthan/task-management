package com.cn.taskmanagement.dto;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Data Transfer Object (DTO) representing Task information.
 */
public class TaskDto {
    private UUID id;
    private String title;
    private String description;
    private String status;
    private String priority;
    private LocalDateTime deadline;

    private UUID projectId;

    // Default constructor
    public TaskDto() {
        // Initialize any default values if needed
    }

    // Custom constructor
    public TaskDto(UUID projectId) {
        this.projectId = projectId;
    }

    // Custom constructor
    public TaskDto(String title, String description, String status, String priority, LocalDateTime deadline) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.deadline = deadline;
    }

    // Custom constructor
    public TaskDto(String title, String description, String status, String priority, LocalDateTime deadline, UUID projectId) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.deadline = deadline;
        this.projectId = projectId;
    }

    // Getters and setters

    /**
     * Gets the unique identifier of the task.
     *
     * @return The UUID of the task.
     */
    public UUID getId() {
        return id;
    }


    /**
     * Sets the unique identifier of the task.
     *
     * @param id The UUID to set.
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Gets the title of the task.
     *
     * @return The title of the task.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the task.
     *
     * @param title The title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the description of the task.
     *
     * @return The description of the task.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the task.
     *
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the status of the task.
     *
     * @return The status of the task.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the task.
     *
     * @param status The status to set.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the priority of the task.
     *
     * @return The priority of the task.
     */
    public String getPriority() {
        return priority;
    }

    /**
     * Sets the priority of the task.
     *
     * @param priority The priority to set.
     */
    public void setPriority(String priority) {
        this.priority = priority;
    }

    /**
     * Gets the deadline of the task.
     *
     * @return The deadline of the task.
     */
    public LocalDateTime getDeadline() {
        return deadline;
    }

    /**
     * Sets the deadline of the task.
     *
     * @param deadline The deadline to set.
     */
    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    private ProjectDto project;


    /**
     * Gets the associated project DTO.
     *
     * @return The ProjectDto associated with the task.
     */
    public ProjectDto getProject() {
        return project;
    }

    /**
     * Sets the associated project DTO.
     *
     * @param project The ProjectDto to set.
     */
    public void setProject(ProjectDto project) {
        this.project = project;
    }

    /**
     * Gets the unique identifier of the associated project.
     *
     * @return The UUID of the associated project.
     */
    public UUID getProjectId() {
        return projectId;
    }

    /**
     * Compares two TaskDto objects for equality based on their unique identifiers.
     *
     * @param o The object to compare with.
     * @return True if the TaskDto objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskDto taskDto = (TaskDto) o;
        return Objects.equals(id, taskDto.id);
    }

    /**
     * Generates a hash code value for the TaskDto object.
     *
     * @return The hash code value.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
