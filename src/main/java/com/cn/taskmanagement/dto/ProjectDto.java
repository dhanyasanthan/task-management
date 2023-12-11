package com.cn.taskmanagement.dto;

import java.util.Objects;
import java.util.UUID;

/**
 * Data Transfer Object (DTO) for representing Project information.
 */
public class ProjectDto {
    private UUID id;
    private String name;

    // Constructors

    /**
     * Default constructor for ProjectDto.
     */
    public ProjectDto() {
        // Default constructor
    }

    /**
     * Parameterized constructor for ProjectDto.
     *
     * @param id   The unique identifier of the project.
     * @param name The name of the project.
     */
    public ProjectDto(UUID id, String name) {
        this.id = id;
        this.name = name;
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
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the project.
     *
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Compares two ProjectDto objects for equality based on their unique identifiers.
     *
     * @param o The object to compare with.
     * @return True if the ProjectDto objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProjectDto projectDto = (ProjectDto) o;

        // Check if the id is equal
        return Objects.equals(id, projectDto.id);
    }

    /**
     * Generates a hash code value for the ProjectDto object.
     *
     * @return The hash code value.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
