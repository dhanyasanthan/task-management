package com.cn.taskmanagement.repository;

import com.cn.taskmanagement.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for managing Task entities in the database.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    /**
     * Batch update the status of tasks with the given IDs.
     *
     * @param taskIds   The list of task IDs to be updated.
     * @param newStatus The new status to set for the tasks.
     */
    //Update batch tasks
    @Modifying
    @Query("UPDATE Task t SET t.status = :newStatus WHERE t.id IN :taskIds")
    void batchUpdateStatus(@Param("taskIds") List<UUID> taskIds, @Param("newStatus") String newStatus);

    /**
     * Find tasks by title.
     *
     * @param title The title to search for.
     * @return List of tasks with the specified title.
     */
    List<Task> findByTitle(String title);
}
