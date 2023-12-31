package com.cn.taskmanagement.service.usecase;

import com.cn.taskmanagement.model.Project;
import com.cn.taskmanagement.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link ProjectServiceImpl} class.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
class ProjectServiceImplTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectServiceImpl projectService;

    /**
     * Test for retrieving all projects.
     */
    void getAllProjects() {
        // Arrange
        Project project1 = new Project();
        project1.setProjectName("Project 1");

        Project project2 = new Project();
        project2.setProjectName("Project 2");

        List<Project> projects = Arrays.asList(project1, project2);

        when(projectRepository.findAll()).thenReturn(projects);

        // Act
        List<Project> result = projectService.getAllProjects();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Project 1", result.get(0).getProjectName());
        assertEquals("Project 2", result.get(1).getProjectName());

        // Verify interactions
        verify(projectRepository, times(1)).findAll();
        verifyNoMoreInteractions(projectRepository);
    }

    /**
     * Test for retrieving a project by its ID when the project exists.
     */
    @Test
    void getProjectById_ProjectExists() {
        // Arrange
        UUID projectId = UUID.randomUUID();
        Project project = new Project();
        project.setProjectName("Existing Project");

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        // Act
        Optional<Project> result = projectService.getProjectById(projectId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Existing Project", result.get().getProjectName());

        // Verify interactions
        verify(projectRepository, times(1)).findById(projectId);
        verifyNoMoreInteractions(projectRepository);
    }

    /**
     * Test for retrieving a project by its ID when the project does not exist.
     */
    @Test
    void getProjectById_ProjectNotExists() {
        // Arrange
        UUID projectId = UUID.randomUUID();

        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        // Act
        Optional<Project> result = projectService.getProjectById(projectId);

        // Assert
        assertTrue(result.isEmpty());

        // Verify interactions
        verify(projectRepository, times(1)).findById(projectId);
        verifyNoMoreInteractions(projectRepository);
    }

    /**
     * Test for creating a new project.
     */
    @Test
    void createProject() {
        // Arrange
        Project project = new Project();
        project.setProjectName("New Project");

        when(projectRepository.save(project)).thenReturn(project);

        // Act
        Project result = projectService.createProject(project);

        // Assert
        assertEquals("New Project", result.getProjectName());

        // Verify interactions
        verify(projectRepository, times(1)).save(project);
        verifyNoMoreInteractions(projectRepository);
    }

    /**
     * Test for updating an existing project when the project exists.
     */
    @Test
    void updateProject_ProjectExists() {
        // Arrange
        UUID projectId = UUID.randomUUID();
        Project existingProject = new Project();
        existingProject.setProjectName("Updated Project");
        Project updatedProject = new Project();
        updatedProject.setProjectName("Existing Project");

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(existingProject));
        when(projectRepository.save(existingProject)).thenReturn(existingProject);

        // Act
        Optional<Project> result = projectService.updateProject(projectId, updatedProject);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Updated Project", result.get().getProjectName());

        // Verify interactions
        verify(projectRepository, times(1)).findById(projectId);
        verify(projectRepository, times(1)).save(existingProject);
        verifyNoMoreInteractions(projectRepository);
    }

    /**
     * Test for updating an existing project when the project does not exist.
     */
    @Test
    void updateProject_ProjectNotExists() {
        // Arrange
        UUID projectId = UUID.randomUUID();

        Project updatedProject = new Project();
        updatedProject.setProjectName("Updated Project");

        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        // Act
        Optional<Project> result = projectService.updateProject(projectId, updatedProject);

        // Assert
        assertTrue(result.isEmpty());

        // Verify interactions
        verify(projectRepository, times(1)).findById(projectId);
        verifyNoMoreInteractions(projectRepository);
    }

    /**
     * Test for deleting an existing project when the project exists.
     */
    @Test
    void deleteProject_ProjectExists() {
        // Arrange
        UUID projectId = UUID.randomUUID();
        Project existingProject = new Project();
        existingProject.setProjectName("Existing Project");

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(existingProject));

        // Act
        boolean result = projectService.deleteProject(projectId);

        // Assert
        assertTrue(result);

        // Verify interactions
        verify(projectRepository, times(1)).findById(projectId);
        verify(projectRepository, times(1)).delete(existingProject);
        verifyNoMoreInteractions(projectRepository);
    }

    /**
     * Test for deleting a project when the project does not exist.
     */
    @Test
    void deleteProject_ProjectNotExists() {
        // Arrange
        UUID projectId = UUID.randomUUID();

        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        // Act
        boolean result = projectService.deleteProject(projectId);

        // Assert
        assertFalse(result);

        // Verify interactions
        verify(projectRepository, times(1)).findById(projectId);
        verifyNoMoreInteractions(projectRepository);
    }

}
