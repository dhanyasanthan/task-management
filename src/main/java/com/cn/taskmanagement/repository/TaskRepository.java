package com.cn.taskmanagement.repository;

import com.cn.taskmanagement.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    //Update batch tasks
    @Modifying
    @Query("UPDATE Task t SET t.status = :newStatus WHERE t.id IN :taskIds")
    void batchUpdateStatus(@Param("taskIds") List<UUID> taskIds, @Param("newStatus") String newStatus);


    List<Task> findByTitle(String title);
}
