package com.spring.TaskManagementBackend.repository;

import com.spring.TaskManagementBackend.pojo.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
