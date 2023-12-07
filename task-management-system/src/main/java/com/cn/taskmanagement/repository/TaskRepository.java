package com.cn.taskmanagement.repository;

import com.cn.taskmanagement.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    // additional query methods if needed
}