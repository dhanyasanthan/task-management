package com.cn.taskmanagement.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity class representing a task in the system.
 */
@Entity
@Table(name = "task", schema = "public")
public class Task {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;
    private String title;
    private String description;
    private String status;
    private String priority;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime deadline;

    // Constructors

    /**
     * Default constructor for Task.
     */
    public Task() {
        // Default constructor
    }

    /**
     * Constructor for Task with specified properties.
     *
     * @param title       The title of the task.
     * @param description The description of the task.
     * @param status      The status of the task.
     * @param priority    The priority of the task.
     * @param deadline    The deadline of the task.
     */
    public Task(String title, String description, String status, String priority, LocalDateTime deadline) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.deadline = deadline;
    }

    // Getters and Setters

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

    /**
     * Represents the project associated with the task.
     * Many tasks can belong to one project.
     */
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "project_id")
    private Project project;

    /**
     * Gets the project associated with the task.
     *
     * @return The associated project.
     */
    public Project getProject() {
        return project;
    }

    /**
     * Sets the project associated with the task.
     * This setter is needed for Hibernate to set the associated project when loading from the database.
     *
     * @param project The project to set.
     */
    public void setProject(Project project) {
        this.project = project;
    }

    @SuppressWarnings("unused")
    @Column(name = "project_id", insertable = false, updatable = false)
    private UUID projectId;
}