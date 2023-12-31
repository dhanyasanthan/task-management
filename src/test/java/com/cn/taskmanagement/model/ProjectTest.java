package com.cn.taskmanagement.model;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testng.annotations.Test;

/**
 * Unit tests for the {@link Project} class.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProjectTest {

    /**
     * Test the default constructor of Project.
     */
    @Test
    public void testDefaultConstructor() {
        // Arrange
        Project project = new Project();

        // Assert
        assertNull(project.getId());
        assertNull(project.getProjectName());
        assertNull(project.getTasks());
    }

    /**
     * Test the parameterized constructor of Project.
     */
    @Test
    public void testParameterizedConstructor() {
        // Arrange
        List<Task> tasks = new ArrayList<>();
        Project project = new Project("ProjectName", tasks);

        // Assert
        assertNull(project.getId()); // As it's generated by JPA
        assertEquals("ProjectName", project.getProjectName());
        assertEquals(tasks, project.getTasks());
    }

    /**
     * Test getters and setters of Project.
     */
    @Test
    public void testGettersAndSetters() {
        // Arrange
        Project project = new Project();
        UUID id = UUID.randomUUID();
        String projectName = "NewProject";
        List<Task> tasks = new ArrayList<>();

        // Act
        project.setId(id);
        project.setProjectName(projectName);
        project.setTasks(tasks);

        // Assert
        assertEquals(id, project.getId());
        assertEquals(projectName, project.getProjectName());
        assertEquals(tasks, project.getTasks());
    }

}
