package com.spring.TaskManagementBackend.service;

import com.spring.TaskManagementBackend.pojo.Task;
import com.spring.TaskManagementBackend.pojo.User;
import com.spring.TaskManagementBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.TaskManagementBackend.repository.TaskRepository;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(Task task) {
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }


    public Task createTask(Task task)
    {
        User assigneeUser = userRepository.findByName(task.getAssignee())
                .orElseThrow(() -> new RuntimeException("Assignee not found: " + task.getAssignee()));
        User creatorUser = userRepository.findByName(task.getCreator())
                .orElseThrow(() -> new RuntimeException("Creator not found: " + task.getCreator()));

        // Assign the fetched names from User table
        task.setAssignee(assigneeUser.getName());
        task.setCreator(creatorUser.getName());

        return taskRepository.save(task);
    }
    public List<String> getStatuses() {
        // Example: return a static list of statuses
        return List.of("TO-DO", "INPROGRESS", "READY FOR QA", "DONE");
    }

    public List<String> getAssignees() {
        System.out.println("Assignees" + userRepository.findAll().stream()
                .filter(user -> user.getStatus() == User.Status.ACTIVE)
                .map(User::getName).toList());
        return userRepository.findAll().stream().filter(user -> user.getStatus() == User.Status.ACTIVE)
                .map(User::getName).toList();
    }

    public List<String> getCreators() {
        return userRepository.findAll().stream()
                .filter(user -> user.getStatus() == User.Status.ACTIVE)
                .map(User::getName).toList();
    }






}
