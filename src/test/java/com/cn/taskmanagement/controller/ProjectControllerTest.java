package com.cn.taskmanagement.controller;

import com.cn.taskmanagement.model.Project;
import com.cn.taskmanagement.model.Task;
import com.cn.taskmanagement.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProjectControllerTest {

    @Mock
    private ProjectService projectService;

    @InjectMocks
    private ProjectController projectController;

    @Test
    void getAllProjects_ReturnsListOfProjects() {
        // TODO : Add code
        /*
        // Create tasks
        List<Task> tasksList = createListOfTasks();
        List<Project> projects = List.of(new Project("Test", tasksList));
        when(projectService.getAllProjects()).thenReturn(projects);

        // Act
        List<Project> result = projectController.getAllProjects();

        // Assert
        assertEquals(2, result.size());*/
    }

    @Test
    void getProjectById_WithValidId_ReturnsProject() {

        // Create tasks
        List<Task> tasksList = createListOfTasks();
        Project expectedProject = new Project("test1", tasksList);
        Long projectId = 1L;
        when(projectService.getProjectById(projectId)).thenReturn(Optional.of(expectedProject));

        // Act
        ResponseEntity<Project> responseEntity = projectController.getProjectById(projectId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedProject, responseEntity.getBody());
    }

    @Test
    void getProjectById_WithInvalidId_ReturnsNotFound() {
        // Arrange
        Long projectId = 1L;
        when(projectService.getProjectById(projectId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Project> responseEntity = projectController.getProjectById(projectId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void createProject_WithValidProject_ReturnsCreated() {

        // Arrange
        Project projectToCreate = new Project((String) null, createListOfTasks());
        Project createdProject = new Project("test2", createListOfTasks());
        when(projectService.createProject(projectToCreate)).thenReturn(createdProject);

        // Act
        ResponseEntity<Project> responseEntity = projectController.createProject(projectToCreate);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(createdProject, responseEntity.getBody());
    }

    @Test
    void updateProject_WithValidIdAndProject_ReturnsUpdatedProject() {
        // Arrange
        Long projectId = 2L;
        Project updatedProject = new Project("Test1", createListOfTasks());
        when(projectService.updateProject(projectId, updatedProject)).thenReturn(Optional.of(updatedProject));

        // Act
        ResponseEntity<Project> responseEntity = projectController.updateProject(projectId, updatedProject);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedProject, responseEntity.getBody());
    }

    @Test
    void updateProject_WithInvalidId_ReturnsNotFound() {

        // Create tasks
        Task task1 = new Task("Task 1", "Description 1", "To Do", "High", LocalDateTime.now().plusDays(1));
        Task task2 = new Task("Task 2", "Description 2", "In Progress", "Medium", LocalDateTime.now().plusDays(3));

        // Create a list of tasks
        List<Task> tasksList = new ArrayList<>();
        tasksList.add(task1);
        tasksList.add(task2);

        Project updatedProject = new Project("test", tasksList);
        Long projectId = 1L;
        when(projectService.updateProject(projectId, updatedProject)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Project> responseEntity = projectController.updateProject(projectId, updatedProject);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void deleteProject_WithValidId_ReturnsNoContent() {
        // Arrange
        Long projectId = 1L;
        when(projectService.deleteProject(projectId)).thenReturn(true);

        // Act
        ResponseEntity<Void> responseEntity = projectController.deleteProject(projectId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void deleteProject_WithInvalidId_ReturnsNotFound() {
        // Arrange
        Long projectId = 1L;
        when(projectService.deleteProject(projectId)).thenReturn(false);

        // Act
        ResponseEntity<Void> responseEntity = projectController.deleteProject(projectId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    Task createTask() {
        return new Task("Task 1", "Description 1", "To Do", "High", LocalDateTime.now());
    }

    List<Task> createListOfTasks() {
        // Create tasks
        Task task1 = new Task("Task 1", "Description 1", "To Do", "High", LocalDateTime.now());
        Task task2 = new Task("Task 2", "Description 2", "In Progress", "Medium", LocalDateTime.now().plusDays(3));

        // Create a list of tasks
        List<Task> tasksList = new ArrayList<>();
        tasksList.add(task1);
        tasksList.add(task2);

        return tasksList;
    }
    // TODO: Additional test methods for create, update, and delete can be added

}
