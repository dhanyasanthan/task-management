package com.cn.taskmanagement.dto;

import java.util.UUID;

public class ProjectDto {
    private UUID id;
    private String name;

    // Constructors
    public ProjectDto() {
        // Default constructor
    }

    public ProjectDto(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters and Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
