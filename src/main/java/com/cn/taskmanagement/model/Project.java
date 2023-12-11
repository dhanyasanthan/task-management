package com.cn.taskmanagement.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

/**
 * Entity class representing a project in the system.
 */
@Entity
@Table(name = "project", schema = "public")
public class Project {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    private String projectName;

    // Constructors

    /**
     * Default constructor for Project.
     */
    public Project() {
        // Default constructor
    }

    /**
     * Parameterized constructor for Project.
     *
     * @param id           The unique identifier of the project.
     * @param projectName  The name of the project.
     */
    public Project(UUID id, String projectName) {
        this.id = id;
        this.projectName = projectName;
    }

    /**
     * Constructor for Project with a list of associated tasks.
     *
     * @param projectName  The name of the project.
     * @param tasks        The list of associated tasks.
     */
    public Project(String projectName, List<Task> tasks) {
        this.projectName = projectName;
        this.tasks = tasks;
    }

    // Getters and Setters

    /**
     * Gets the unique identifier of the project.
     *
     * @return The UUID of the project.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the project.
     *
     * @param id The UUID to set.
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Gets the name of the project.
     *
     * @return The name of the project.
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Sets the name of the project.
     *
     * @param projectName The name to set.
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * Gets the list of tasks associated with the project.
     *
     * @return The list of tasks associated with the project.
     */
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * Sets the list of tasks associated with the project.
     *
     * @param tasks The list of tasks to set.
     */
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    // One project can have multiple tasks

    /**
     * Represents the list of tasks associated with the project.
     * One project can have multiple tasks.
     */
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Task> tasks;

}