package com.cn.taskmanagement.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String status;
    private String priority;
    private LocalDate deadline;

    public Task() {
        // Default constructor
    }

    public Task(String title, String description, String status, String priority, LocalDate deadline) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.deadline = deadline;
    }

}
