package com.cn.taskmanagement.service;

import com.cn.taskmanagement.model.Project;
import com.cn.taskmanagement.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Optional<Project> getProjectById(Long projectId) {
        return projectRepository.findById(projectId);
    }

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    public Optional<Project> updateProject(Long projectId, Project updatedProject) {
        return projectRepository.findById(projectId).map(existingProject -> {
            existingProject.setProjectName(updatedProject.getProjectName());
            // TODO: Update other fields as needed
            return projectRepository.save(existingProject);
        });
    }

    public boolean deleteProject(Long projectId) {
        return projectRepository.findById(projectId).map(project -> {
            projectRepository.delete(project);
            return true;
        }).orElse(false);
    }
}