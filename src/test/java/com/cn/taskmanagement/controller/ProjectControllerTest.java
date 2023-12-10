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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ProjectControllerTest {

    @Mock
    private ProjectServiceImpl projectService;

    @InjectMocks
    private ProjectController projectController;

    private Project sampleProject;
    private UUID sampleProjectId;

    @BeforeEach
    void setUp() {
        sampleProjectId = UUID.randomUUID();
        sampleProject = new Project();
    }

    @Test
    void getAllProjects() {
        when(projectService.getAllProjects()).thenReturn(Collections.singletonList(sampleProject));

        List<Project> result = projectController.getAllProjects();

        assertEquals(1, result.size());
        assertEquals(sampleProject, result.get(0));
    }

    @Test
    void getProjectById_ExistingProject() {
        when(projectService.getProjectById(sampleProjectId)).thenReturn(Optional.of(sampleProject));

        ResponseEntity<Project> result = projectController.getProjectById(sampleProjectId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(sampleProject, result.getBody());
    }

    @Test
    void getProjectById_NonexistentProject() {
        when(projectService.getProjectById(sampleProjectId)).thenReturn(Optional.empty());

        ResponseEntity<Project> result = projectController.getProjectById(sampleProjectId);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void createProject() {
        when(projectService.createProject(sampleProject)).thenReturn(sampleProject);

        ResponseEntity<Project> result = projectController.createProject(sampleProject);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(sampleProject, result.getBody());
    }

    @Test
    void updateProject_ExistingProject() {
        when(projectService.updateProject(sampleProjectId, sampleProject)).thenReturn(Optional.of(sampleProject));

        ResponseEntity<Project> result = projectController.updateProject(sampleProjectId, sampleProject);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(sampleProject, result.getBody());
    }

    @Test
    void updateProject_NonexistentProject() {
        when(projectService.updateProject(sampleProjectId, sampleProject)).thenReturn(Optional.empty());

        ResponseEntity<Project> result = projectController.updateProject(sampleProjectId, sampleProject);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void deleteProject_ExistingProject() {
        when(projectService.deleteProject(sampleProjectId)).thenReturn(true);

        ResponseEntity<Void> result = projectController.deleteProject(sampleProjectId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertTrue(result.getBody() == null || result.getBody().toString().isEmpty());
    }

    @Test
    void deleteProject_NonexistentProject() {
        when(projectService.deleteProject(sampleProjectId)).thenReturn(false);

        ResponseEntity<Void> result = projectController.deleteProject(sampleProjectId);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }
}