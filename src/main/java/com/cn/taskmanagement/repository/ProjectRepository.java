
package com.cn.taskmanagement.repository;

import com.cn.taskmanagement.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository interface for managing Project entities in the database.
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {
    // Add additional custom queries as needed
}