package com.cn.taskmanagement.service.usecase;

import com.cn.taskmanagement.model.Project;
import com.cn.taskmanagement.repository.ProjectRepository;
import com.cn.taskmanagement.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service implementation for managing Project entities.
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository projectRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    /**
     * Get a list of all projects.
     *
     * @return List of all projects.
     */
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    /**
     * Get a project by its unique identifier.
     *
     * @param projectId The unique identifier of the project.
     * @return Optional containing the project if found, otherwise empty.
     */
    public Optional<Project> getProjectById(UUID projectId) {
        return projectRepository.findById(projectId);
    }

    /**
     * Create a new project.
     *
     * @param project The project entity to create.
     * @return The created project.
     */
    public Project createProject(Project project) {
        Project createdProject = createProjectInstance(project.getProjectName());
        return projectRepository.save(createdProject);
    }

    /**
     * Helper method to create a new project instance with a generated UUID.
     *
     * @param projectName The name of the project.
     * @return The new project instance.
     */
    private Project createProjectInstance(String projectName) {
        Project project = new Project();
        project.setId(UUID.randomUUID());
        project.setProjectName(projectName);
        return project;
    }

    /**
     * Update an existing project.
     *
     * @param projectId      The unique identifier of the project to update.
     * @param updatedProject The updated project entity.
     * @return Optional containing the updated project if found, otherwise empty.
     */
    public Optional<Project> updateProject(UUID projectId, Project updatedProject) {
        return projectRepository.findById(projectId)
                .map(existingProject -> updateProjectProperties(existingProject, updatedProject))
                .map(projectRepository::save);
    }

    /**
     * Helper method to update project properties.
     *
     * @param existingProject The existing project entity.
     * @param updatedProject  The updated project entity.
     * @return The updated project entity.
     */
    private Project updateProjectProperties(Project existingProject, Project updatedProject) {
        existingProject.setProjectName(updatedProject.getProjectName());
        // You can add more property updates here if needed
        return existingProject;
    }

    /**
     * Delete a project by its unique identifier.
     *
     * @param projectId The unique identifier of the project to delete.
     * @return True if the project was deleted, false otherwise.
     */
    public boolean deleteProject(UUID projectId) {
        return projectRepository.findById(projectId).map(this::deleteProjectEntity).orElse(false);
    }

    /**
     * Helper method to delete a project entity.
     *
     * @param project The project entity to delete.
     * @return True if the project was deleted, false otherwise.
     */
    private boolean deleteProjectEntity(Project project) {
        projectRepository.delete(project);
        return true;
    }
}