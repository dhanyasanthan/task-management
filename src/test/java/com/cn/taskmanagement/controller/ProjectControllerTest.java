package com.cn.taskmanagement.controller;

import com.cn.taskmanagement.model.Project;
import com.cn.taskmanagement.service.usecase.ProjectServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the {@link ProjectController} class.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
class ProjectControllerTest {

    @Mock
    private ProjectServiceImpl projectService;

    @InjectMocks
    private ProjectController projectController;

    private Project sampleProject;
    private UUID sampleProjectId;

    /**
     * Set up method to initialize test data before each test.
     */
    @BeforeEach
    void setUp() {
        sampleProjectId = UUID.randomUUID();
        sampleProject = new Project();
    }

    /**
     * Test case for the {@link ProjectController#getAllProjects()} method.
     */
    @Test
    void getAllProjects() {
        // Mocking the behavior of the projectService to return a list containing the sample project
        when(projectService.getAllProjects()).thenReturn(Collections.singletonList(sampleProject));

        // Calling the method under test
        List<Project> result = projectController.getAllProjects();

        // Assertions
        assertEquals(1, result.size());
        assertEquals(sampleProject, result.get(0));
    }

    /**
     * Test case for the {@link ProjectController#getProjectById(UUID)} method with an existing project.
     */
    @Test
    void getProjectById_ExistingProject() {
        // Mocking the behavior of the projectService to return the sample project wrapped in an Optional
        when(projectService.getProjectById(sampleProjectId)).thenReturn(Optional.of(sampleProject));

        // Calling the method under test
        ResponseEntity<Project> result = projectController.getProjectById(sampleProjectId);

        // Assertions
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(sampleProject, result.getBody());
    }

    /**
     * Test case for the {@link ProjectController#getProjectById(UUID)} method with a non-existent project.
     */
    @Test
    void getProjectById_NonexistentProject() {
        // Mocking the behavior of the projectService to return an empty Optional
        when(projectService.getProjectById(sampleProjectId)).thenReturn(Optional.empty());

        // Calling the method under test
        ResponseEntity<Project> result = projectController.getProjectById(sampleProjectId);

        // Assertions
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    /**
     * Test case for the {@link ProjectController#createProject(Project)} method.
     */
    @Test
    void createProject() {
        // Mocking the behavior of the projectService to return the sample project
        when(projectService.createProject(sampleProject)).thenReturn(sampleProject);

        // Calling the method under test
        ResponseEntity<Project> result = projectController.createProject(sampleProject);

        // Assertions
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(sampleProject, result.getBody());
    }

    /**
     * Test case for the {@link ProjectController#updateProject(UUID, Project)} method with an existing project.
     */
    @Test
    void updateProject_ExistingProject() {
        // Mocking the behavior of the projectService to return the sample project wrapped in an Optional
        when(projectService.updateProject(sampleProjectId, sampleProject)).thenReturn(Optional.of(sampleProject));

        // Calling the method under test
        ResponseEntity<Project> result = projectController.updateProject(sampleProjectId, sampleProject);

        // Assertions
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(sampleProject, result.getBody());
    }

    /**
     * Test case for the {@link ProjectController#updateProject(UUID, Project)} method with a non-existent project.
     */
    @Test
    void updateProject_NonexistentProject() {
        // Mocking the behavior of the projectService to return an empty Optional
        when(projectService.updateProject(sampleProjectId, sampleProject)).thenReturn(Optional.empty());

        // Calling the method under test
        ResponseEntity<Project> result = projectController.updateProject(sampleProjectId, sampleProject);

        // Assertions
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    /**
     * Test case for the {@link ProjectController#deleteProject(UUID)} method with an existing project.
     */
    @Test
    void deleteProject_ExistingProject() {
        // Mocking the behavior of the projectService to return true for successful deletion
        when(projectService.deleteProject(sampleProjectId)).thenReturn(true);

        // Calling the method under test
        ResponseEntity<Void> result = projectController.deleteProject(sampleProjectId);

        // Assertions
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNull(result.getBody());
    }

    /**
     * Test case for the {@link ProjectController#deleteProject(UUID)} method with a non-existent project.
     */
    @Test
    void deleteProject_NonexistentProject() {
        // Mocking the behavior of the projectService to return false for unsuccessful deletion
        when(projectService.deleteProject(sampleProjectId)).thenReturn(false);

        // Calling the method under test
        ResponseEntity<Void> result = projectController.deleteProject(sampleProjectId);

        // Assertions
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

}