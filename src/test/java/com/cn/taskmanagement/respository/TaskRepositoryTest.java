package com.cn.taskmanagement.respository;

import com.cn.taskmanagement.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the {@link TaskRepository} class.
 */
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
class TaskRepositoryTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private Query query;

    @InjectMocks
    private TaskRepository taskRepository;

    /**
     * Test the batch update of task statuses in the repository.
     */
    @Test
    void batchUpdateStatus_ShouldExecuteUpdateQuery() {
        // Arrange
        List<UUID> taskIds = Arrays.asList(UUID.randomUUID(), UUID.randomUUID());
        String newStatus = "completed";

        when(entityManager.createQuery(anyString())).thenReturn(query);

        // Act
        taskRepository.batchUpdateStatus(taskIds, newStatus);

        // Assert
        verify(entityManager).createQuery(anyString());
        verify(query).executeUpdate();
        verify(query).setParameter(eq("taskIds"), eq(taskIds));
        verify(query).setParameter(eq("newStatus"), eq(newStatus));
    }

}
