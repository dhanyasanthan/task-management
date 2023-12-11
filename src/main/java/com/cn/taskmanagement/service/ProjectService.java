package com.cn.taskmanagement.service;

import com.cn.taskmanagement.model.Project;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectService {
    List<Project> getAllProjects();

    Optional<Project> getProjectById(UUID projectId);

    Project createProject(Project project);

    Optional<Project> updateProject(UUID projectId, Project updatedProject);

    boolean deleteProject(UUID projectId);
}

