package com.cn.taskmanagement.respository;
import com.cn.taskmanagement.model.Project;
import com.cn.taskmanagement.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link ProjectRepository} class.
 */
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
class ProjectRepositoryTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectRepository projectRepositoryUnderTest;

    /**
     * Test the retrieval of all projects from the repository.
     */
    @Test
    void testGetAllProjects() {
        // Arrange
        List<Project> expectedProjects = Arrays.asList(
                new Project(UUID.randomUUID(), "Project 1"),
                new Project(UUID.randomUUID(), "Project 2")
        );
        when(projectRepository.findAll()).thenReturn(expectedProjects);

        // Act
        List<Project> actualProjects = projectRepositoryUnderTest.findAll();

        // Assert
        assertEquals(expectedProjects, actualProjects);
        verify(projectRepository).findAll();
    }

    /**
     * Test the retrieval of a project by ID from the repository.
     */
    @Test
    void testGetProjectById() {
        // Arrange
        UUID projectId = UUID.randomUUID();
        Project expectedProject = new Project(projectId, "Project 1");
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(expectedProject));

        // Act
        Optional<Project> actualProject = projectRepositoryUnderTest.findById(projectId);

        // Assert
        assertEquals(Optional.of(expectedProject), actualProject);
        verify(projectRepository).findById(projectId);
    }

}
