
package com.cn.taskmanagement.repository;

import com.cn.taskmanagement.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    // Additional custom queries can be added if needed
}