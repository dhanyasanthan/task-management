package com.cn.taskmanagement.controller;

import com.cn.taskmanagement.model.Project;
import com.cn.taskmanagement.service.usecase.ProjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controller class for handling Project-related HTTP requests.
 */
@RestController
@RequestMapping("/api/projects")
public class ProjectController {

  private final ProjectServiceImpl projectService;

  @Autowired
  public ProjectController(ProjectServiceImpl projectService) {
    this.projectService = projectService;
  }

  /**
   * Retrieves a list of all projects.
   *
   * @return List of projects.
   */
  @GetMapping
  public List<Project> getAllProjects() {
    return projectService.getAllProjects();
  }

  /**
   * Retrieves a project by its ID.
   *
   * @param id The ID of the project to retrieve.
   * @return ResponseEntity containing the project if found, or NOT_FOUND status if not.
   */
  @GetMapping("/{id}")
  public ResponseEntity<Project> getProjectById(@PathVariable UUID id) {
    return projectService.getProjectById(id)
        .map(project -> new ResponseEntity<>(project, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  /**
   * Creates a new project.
   *
   * @param project The project to be created.
   * @return ResponseEntity containing the created project and CREATED status.
   */
  @PostMapping
  public ResponseEntity<Project> createProject(@RequestBody Project project) {
    Project createdProject = projectService.createProject(project);
    return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
  }

  /**
   * Updates an existing project.
   *
   * @param id             The ID of the project to update.
   * @param updatedProject The updated project data.
   * @return ResponseEntity containing the updated project if successful, or NOT_FOUND status if the
   * project doesn't exist.
   */
  @PutMapping("/{id}")
  public ResponseEntity<Project> updateProject(@PathVariable UUID id,
      @RequestBody Project updatedProject) {
    return projectService.updateProject(id, updatedProject)
        .map(project -> new ResponseEntity<>(project, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  /**
   * Deletes a project by its ID.
   *
   * @param id The ID of the project to delete.
   * @return ResponseEntity with OK status if the deletion is successful, or NOT_FOUND status if the
   * project doesn't exist.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProject(@PathVariable UUID id) {
    boolean deleted = projectService.deleteProject(id);
    return deleted ? new ResponseEntity<>(HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
}