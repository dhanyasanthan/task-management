package com.cn.taskmanagement.repository;

import com.cn.taskmanagement.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByTitle(String title);
    // Additional custom queries can be added if needed
}
