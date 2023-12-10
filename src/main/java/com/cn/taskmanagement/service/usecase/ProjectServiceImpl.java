package com.cn.taskmanagement.service.usecase;

import com.cn.taskmanagement.model.Project;
import com.cn.taskmanagement.repository.ProjectRepository;
import com.cn.taskmanagement.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository projectRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Optional<Project> getProjectById(UUID projectId) {
        return projectRepository.findById(projectId);
    }



    public Project createProject(Project project) {
        Project createdProject = createProjectInstance(project.getProjectName());
        return projectRepository.save(createdProject);
    }

    private Project createProjectInstance(String projectName) {
        Project project = new Project();
        project.setId(UUID.randomUUID());
        project.setProjectName(projectName);
        return project;
    }

    public Optional<Project> updateProject(UUID projectId, Project updatedProject) {
        return projectRepository.findById(projectId)
                .map(existingProject -> updateProjectProperties(existingProject, updatedProject))
                .map(projectRepository::save);
    }

    private Project updateProjectProperties(Project existingProject, Project updatedProject) {
        existingProject.setProjectName(updatedProject.getProjectName());
        // You can add more property updates here if needed
        return existingProject;
    }

    public boolean deleteProject(UUID projectId) {
        return projectRepository.findById(projectId).map(this::deleteProjectEntity).orElse(false);
    }

    private boolean deleteProjectEntity(Project project) {
        projectRepository.delete(project);
        return true;
    }
}