package com.cn.taskmanagement.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Unit tests for the {@link Task} class.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TaskTest {

    /**
     * Test equals and hashCode methods of Task.
     */
    @Test
    public void testEqualsAndHashCode() {
        // Arrange
        Task task1 = new Task();
        Task task2 = new Task();
        UUID id = UUID.randomUUID();
        task1.setId(id);
        task2.setId(id);

        // Assert
        assertEquals(task1, task2);
        assertEquals(task1.hashCode(), task2.hashCode());
    }

    /**
     * Test getters and setters of Task.
     */
    @Test
    public void testGettersAndSetters() {
        // Arrange
        UUID id = UUID.randomUUID();
        String title = "Task Title";
        String description = "Task Description";
        String status = "InProgress";
        String priority = "High";
        LocalDateTime deadline = LocalDateTime.of(2023, 12, 31, 23, 59);

        Task task = new Task();
        task.setId(id);
        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(status);
        task.setPriority(priority);
        task.setDeadline(deadline);

        // Assert
        assertEquals(id, task.getId());
        assertEquals(title, task.getTitle());
        assertEquals(description, task.getDescription());
        assertEquals(status, task.getStatus());
        assertEquals(priority, task.getPriority());
        assertEquals(deadline, task.getDeadline());
    }

    /**
     * Test equals with different IDs.
     */
    @Test
    public void testEqualsWithDifferentId() {
        // Arrange
        Task task1 = new Task();
        Task task2 = new Task();
        task1.setId(UUID.randomUUID());
        task2.setId(UUID.randomUUID());

        // Assert
        assertNotEquals(task1, task2);
    }

    /**
     * Test equals with null IDs.
     */
    @Test
    public void testEqualsWithNullId() {
        // Arrange
        Task task1 = new Task();
        Task task2 = new Task();

        // Assert
        assertNotEquals(task1, task2);
    }

}
