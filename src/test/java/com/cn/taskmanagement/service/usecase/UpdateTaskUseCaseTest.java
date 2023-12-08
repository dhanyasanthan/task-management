package com.cn.taskmanagement.service.usecase;
import com.cn.taskmanagement.dto.TaskDto;
import com.cn.taskmanagement.model.Task;
import com.cn.taskmanagement.repository.TaskRepository;
import com.cn.taskmanagement.service.usecase.UpdateTaskUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UpdateTaskUseCaseTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private UpdateTaskUseCase updateTaskUseCase;

    @Test
    void updateTaskProperties_TaskExists() {
        // Arrange
        UUID taskId = UUID.randomUUID();
        TaskDto updatedTaskDto = new TaskDto("Updated Task", "Updated Description", "In Progress", "Medium", null);
        Task existingTask = new Task("Task 1", "Description 1", "TODO", "High", null);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(Mockito.any(Task.class))).thenReturn(existingTask);

        // Act
        Optional<Task> result = updateTaskUseCase.updateTaskProperties(taskId, updatedTaskDto);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Updated Task", result.get().getTitle());

        // Verify interactions
        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, times(1)).save(Mockito.any(Task.class));
        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    void updateTaskProperties_TaskNotExists() {
        // Arrange
        UUID taskId = UUID.randomUUID();
        TaskDto updatedTaskDto = new TaskDto("Updated Task", "Updated Description", "In Progress", "Medium", null);

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // Act
        Optional<Task> result = updateTaskUseCase.updateTaskProperties(taskId, updatedTaskDto);

        // Assert
        assertTrue(result.isEmpty());

        // Verify interactions
        verify(taskRepository, times(1)).findById(taskId);
        verifyNoMoreInteractions(taskRepository);
    }

    // Add more test methods based on additional scenarios or business logic...
}
