package com.cn.taskmanagement.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String status;
    private String priority;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime deadline;

    //TODO: add additional columns as required
    // private Long createdBy;
    // @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    // private LocalDateTime createdTime;

    // @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    // private LocalDateTime startTime;

    // private Long modifiedBy;

    // @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    // private LocalDateTime modifiedTime;

    // Constructors

    public Task() {
        // Default constructor
    }

    public Task(String title, String description, String status, String priority, LocalDateTime deadline) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.deadline = deadline;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Many tasks can belong to one project
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}